Vue.component("NaruciPacijent", {
  data: function () {
    return {
      cookie: "",
      izabranLek: {
        sifraLeka: "",
        naziv: "",
        uputstvo: "",
        tip: "",
        oblikLeka: "",
        dodatneNapomene: "",
        sastav: "",
        kolicina: "",
        cena: "",
        istekVazenjaCene: "",
        staraCena: "",
        cookie: "",
        datumRezervacije: "",
      },
      kolicina: 0,
      datumRezervacije: "",
      page: 1,
      count: 0,
      pageSize: 15,
      searchTitle: "",
      pageSizes: [3, 6, 9, 15, 30],
      redosledi: ["opadajuce", "rastuce"],
      redosled: "opadajuce",
      polja: ["naziv"],
      sortirajPo: "naziv",
      lekovi: [],
      cookie: "",
      color: "primary",
      apotekaID: "",
      listaNarudzbina: [],
      uspeh: false,
      losUnos: false,
      ukupnaCena: 0,
      konkurentnost: false,
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.retrieveLekove();
  },
  template: `
    <div>

		<b-alert style="text-align: center;" v-model="this.losUnos" variant="danger"> Los unos podataka! </b-alert>
		<b-alert style="text-align: center;" v-model="this.konkurentnost" variant="danger"> Lekovi su naručeni u medjuvremenu, količina je pri tome bila prevelika, probajte ponovo! </b-alert>
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Uspesna rezervacija, pogledajte svoj email.</b-alert>
      

    <!-- PRETRAGA -->
   <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
      <b-container id="page_content">
    	 <div class="form-group">
            <input
	          type="text"
	          class="form-control"
	          placeholder="Pretraga lekova"
	          v-model="searchTitle"
	          @input="page = 1; retrieveLekove();"
	        />
          </div>
          
	      <!-- BIRANJE VELICINE STRANE -->
	        Lekovi po strani:
		    <select v-model="pageSize" @change="handlePageSizeChange($event)">
		      <option v-for="size in pageSizes" :key="size" :value="size">
		        {{ size }}
		      </option>
			</select>
	
		<br>
		<br>

	   <!-- NAVIGACIJA PO STRANICAMA -->
	   <div class="mt-3">
	    <b-pagination
	      v-model="page"
	      :total-rows="count"
	      :per-page="pageSize"
	      aria-controls="my-table"
	      @change="handlePageChange"
	    	></b-pagination>

	 	 </div> 
	<!-- prikazi lekove -->
    <b-row>
     <b-card style="min-width: 450px; max-margin: 5px auto;" title="Lekovi">
      	<b-list-group flush>
	    <b-list-group-item v-for="lek in lekovi"
        :variant="chooseColor(lek)"
        v-if="!(lek.kolicina == null)"
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
				<b>{{lek.naziv}}</b>

				<p class="text-sm mb-0"> Trenutno na stanju: {{lek.kolicina}}  </p>
				<p class="text-sm mb-0"> Cena: {{lek.cena}}  </p>
                  
		            </b-col>
		    </b-col>
		    <b-col md="auto" class="float-right">
		    	<div >
		    		<b-button type="button" size="sm" v-on:click="naruciLek(lek)" variant="primary">Rezervisi Lek</b-button>
		    	</div>

            </b-col>
        </b-row>
    </b-list-group-item>
	</b-list-group>
     </b-card>
  
     <b-card style="max-width: 500px; margin: 30px auto;" title="Rezervisacija Leka">
     		<b-list-group v-for="lek in listaNarudzbina" title="Narudzbenica">	
	 <b-card style="min-width: 450px; max-margin: 5px auto;">
	 <b>{{lek.naziv}}</b>
	 Cena: {{lek.cenaJedno}} x {{lek.kolicina}} kom. 
	 <p>- Ukupno: {{lek.cenaUkupno}}</p>

		<b-button v-on:click="obrisiLek(lek)" variant="primary">Obrisi</b-button>
        	

	</b-list-group>
	<b-col md="auto" class="float-right">
		<div v-if="listaNarudzbina.length != 0">
		
			<div v-html="ukupnaCena"> </div>
			
			<b-button v-on:click="potvrdiNarudzbenicu()" :disabled="listaNarudzbina.length == 0" variant="primary">Potvrdi Rezervaciju</b-button>
		</div>
		
		</b-col>
     </b-card> 
  </b-row>


	<!-- forma za narucivanje leka -->
	<b-modal ref="my-modal" hide-footer title="Rezervacija Leka">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="onNaruciLek">
        <h3>{{this.izabranLek.naziv}}</h3>
       <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
				min = "1"
				max = {{this.izabranLek.kolicina}}
				v-model="kolicina"
            ></b-form-input>
		<br>
          <b-button type="submit" variant="primary">Sacuvaj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
    <!-- forma za narudzbenicu -->
    		<b-modal ref="my-modal1" hide-footer title="Potvrdi Rezervaciju">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="posaljiNarudzbinu">
        <h3>Potvrdi Rezervaciju</h3>
       <b-form-group id="input-group-3" label="Preuzeti do:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="date"
				min = "1"
				v-model="datumRezervacije"
            ></b-form-input>
		<br>
          <b-button type="submit" variant="primary">Rezervisi</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
  	</div>	
  	 </b-container id="page_content">
      </div>
	`,
	
	
	
	
  methods: {
  
  		kolicinaChange() {
  			
  			 price = 0;
  			
  			 for (i = 0; i < this.listaNarudzbina.length; i++) {
  			 	price += this.listaNarudzbina[i].cenaUkupno;
		     }
  			
	      	 this.ukupnaCena = "Ukupna cena: " + price;
	    },

  
    dodajLek(lek) {
      axios
        .get("/rezervacije/lekNarudzbina", {
          params: {
            sifraLeka: lek.sifraLeka,
            cookie: this.cookie,
          },
        })
        .then((response) => this.retrieveLekove());
      this.retrieveLekove();
      this.kolicinaChange();
    },
    potvrdiNarudzbenicu() {
      this.$refs["my-modal1"].show();
    },
    posaljiNarudzbinu() {
      for (i = 0; i < this.listaNarudzbina.length; i++) {
        this.listaNarudzbina[i].datumRezervacije = this.datumRezervacije + "T02:00:00";
        this.listaNarudzbina[i].pacijent = this.cookie;
      }
      
      this.uspeh = false;
      this.losUnos = false;
            console.log(JSON.parse(JSON.stringify(this.listaNarudzbina)))
      axios.post(
          "/rezervacije/pacijent",
          JSON.parse(JSON.stringify(this.listaNarudzbina))
        )
        .then((response) => { 
         	this.retrieveLekove();
         	this.konkurentnost = false;
        	console.log(response.data);
        	this.uspeh = true; 
        })
       	.catch((e) => {
		    if (e.request.status == 409) {
        		this.konkurentnost = true;
        		this.retrieveLekove();
			} else {
        		this.losUnos = true;
        	}
        });
        
      //this.retrieveLekove();
      this.kolicinaChange();
      this.listaNarudzbina = [];
      this.$refs["my-modal1"].hide();
    },
    obrisiLek(lek) {
      for (i = 0; i < this.listaNarudzbina.length; i++) {
        if (this.listaNarudzbina[i].sifraLeka === lek.sifraLeka) {
          this.listaNarudzbina.splice(i, 1);
        }
      }
    },
    naruciLek(lek) {
      this.izabranLek = lek;

      this.$refs["my-modal"].show();
    },
    onNaruciLek() {
    
    	if(this.kolicina > this.izabranLek.kolicina){
    		this.losUnos = true;
    		this.kolicina = "";
    		this.$refs["my-modal"].hide();
    		return;
    	}
    
      tempLek = {
    //    naziv: this.izabranLek.naziv,
        sifraLeka: this.izabranLek.sifra,
        datumRezervacije: this.datumRezervacije,
        kolicina: this.kolicina,
        apotekaId: this.apotekaID,
        cenaUkupno: this.izabranLek.cena*this.kolicina,
        cenaJedno: this.izabranLek.cena,
      };
      //this.izabranLek.kolicina -= this.kolicina
      this.listaNarudzbina.push(tempLek);
      this.datumNarudzbine = "";
      this.kolicina = "";
      this.kolicinaChange();
      this.$refs["my-modal"].hide();
    },
    redirectUser: function () {
      if (this.new_password === this.korisnik.password) {
        this.samePassword = true;
        return;
      }
      this.changePass();
      let cookie = this.korisnik.username + "-" + this.korisnik.password;
      localStorage.setItem("cookie", cookie);
      if (this.userRole === "PACIJENT") {
        app.$router.push("/home-pacijent");
      } else if (this.userRole === "DERMATOLOG") {
        app.$router.push("/home-dermatolog");
      } else if (this.userRole === "FARMACEUT") {
        app.$router.push("/home-farmaceut");
      } else if (this.userRole === "ADMIN_APOTEKE") {
        app.$router.push("/home-admin_apoteke");
      } else {
        localStorage.clear();
        app.$router.push("/");
      }
    },
    redirectToApotekaIzmeni: function () {
      return "primary";
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    getRequestParams(
      searchTitle,
      page,
      pageSize,
      sortirajPo,
      redosled,
      apotekaID,
      cookie
    ) {
      let params = {};

      if (searchTitle) {
        params["title"] = searchTitle;
      } else {
        params["title"] = "";
      }

      if (page) {
        params["page"] = page - 1;
      }

      if (pageSize) {
        params["size"] = pageSize;
      }

      if (sortirajPo) {
        params["sort"] = sortirajPo;
      }

      if (sortirajPo) {
        params["smer"] = redosled;
      }
      params["apotekaID"] = apotekaID;
      params["cookie"] = cookie;
      return params;
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    redirectToApotekaIzmeni: function () {
      app.$router.push("/apoteka/" + this.apoteka.id);
    },
    retrieveLekove() {
      const params = this.getRequestParams(
        this.searchTitle,
        this.page,
        this.pageSize,
        this.sortirajPo,
        this.redosled,
        this.apotekaID,
        this.cookie
      );

      axios
        .get("lekovi/narucivanje_lek_pacijant/", { params })
        .then((response) => {
          const { lekovi, totalItems } = response.data;
          this.lekovi = lekovi;
          this.count = totalItems;

          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleSortChange(value) {
      this.sortirajPo = event.target.value;
      this.page = 1;
      this.retrieveLekove();
    },

    handleSortOrderChange(value) {
      this.redosled = event.target.value;
      this.page = 1;
      this.retrieveLekove();
    },

    handlePageChange(value) {
      this.page = value;
      this.retrieveLekove();
    },

    handlePageSizeChange(event) {
      this.pageSize = event.target.value;
      this.page = 1;
      this.retrieveLekove();
    },
    chooseColor(lek) {
      if (lek.kolicina === null) {
        return "info";
      }
      if (lek.kolicina === 0) {
        return "danger";
      }
      return "success";
    },
  },
});