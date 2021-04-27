Vue.component("NaruciAdmin", {
  data: function () {
    return {
      cookie: "",
      izabranLek: {
        sifra: "",
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
        datumNarudzbine: "",
      },
      kolicina: 0,
      datumNarudzbine: "",
      page: 1,
      count: 0,
      pageSize: 6,
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
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.retrieveLekove();
  },
  template: `
    <div>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/home_dermatolog.css" type="text/css">
      <b-navbar toggleable="lg" href="#/home-admin_apoteke" type="dark" variant="dark">
        <img src="../../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-admin_apoteke">Home</b-nav-item>
            <b-nav-item href="#/dodaj-lek-admin">Dodaj lek</b-nav-item>
            <b-nav-item href="#/pretraga-lek-admin">Pretrazi, obrisi i uredi lekove</b-nav-item>
            <b-nav-item href="#/admin-apoteke-apoteka">Izmeni podatke o apoteci</b-nav-item>
            <b-nav-item href="#/admin-apoteke-narudzbina">Naruci lekove</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
      

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
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
				<b>{{lek.naziv}}</b>
				<div v-if="lek.kolicina == null">
				<p class="text-sm mb-0"> Nije dodat  </p>
				<small></small> 
				</div>
				<div v-else>
				<p class="text-sm mb-0"> Trenutno na stanju: {{lek.kolicina}}  </p>
				<small></small> 
				</div>
                  
		            </b-col>
		    </b-col>
		    <b-col md="auto" class="float-right">
		    	<div v-if="lek.kolicina == null">
		    		<b-button type="button" size="sm" v-on:click="dodajLek(lek)" variant="primary">Dodaj u ponudu</b-button>
		    	</div>
		    	<div v-else>
		    		<b-button type="button" size="sm" v-on:click="naruciLek(lek)" variant="primary">Dodaj u narudzbenicu</b-button>
		    	</div>  
            </b-col>
        </b-row>
    </b-list-group-item>
	</b-list-group>
     </b-card>
  
     <b-card style="max-width: 500px; margin: 30px auto;" title="Narudzbenica">
     		<b-list-group v-for="lek in listaNarudzbina" title="Narudzbenica">	
	 <b-card style="min-width: 450px; max-margin: 5px auto;">{{lek.naziv}} Kolicina: {{lek.kolicina}}

		<b-button v-on:click="obrisiLek(lek)" variant="primary">Obrisi</b-button>
        	

	</b-list-group>
	<b-col md="auto" class="float-right">
		<div v-if="listaNarudzbina.length != 0">
		<b-button v-on:click="potvrdiNarudzbenicu()" :disabled="listaNarudzbina.length == 0" variant="primary">Potvrdi Narudzbinu</b-button>
		</div>
		
		</b-col>
     </b-card> 
  </b-row>


	<!-- forma za narucivanje leka -->
	<b-modal ref="my-modal" hide-footer title="Narucivanje leka">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="onNaruciLek">
        <h3>{{this.izabranLek.naziv}}</h3>
       <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
				min = "1"
				v-model="kolicina"
            ></b-form-input>
		<br>
          <b-button type="submit" variant="primary">Sacuvaj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
    <!-- forma za narudzbenicu -->
    		<b-modal ref="my-modal1" hide-footer title="Potvrdi Narudzbinu">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="posaljiNarudzbinu">
        <h3>Narudzbenica</h3>
       <b-form-group id="input-group-3" label="Rok ponude:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="date"
				min = "1"
				v-model="datumNarudzbine"
            ></b-form-input>
		<br>
          <b-button type="submit" variant="primary">Naruci</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
  	</div>	
  	 </b-container id="page_content">
      </div>
	`,
  methods: {
    dodajLek(lek) {
      axios
        .get("/narudzbine/lekNarudzbina", {
          params: {
            sifra: lek.sifra,
            cookie: this.cookie,
          },
        })
        .then((response) => this.retrieveLekove());
      this.retrieveLekove();
    },
    potvrdiNarudzbenicu() {
      this.$refs["my-modal1"].show();
    },
    posaljiNarudzbinu() {
      for (i = 0; i < this.listaNarudzbina.length; i++) {
        this.listaNarudzbina[i].datumNarudzbine = this.datumNarudzbine;
      }
      console.log(JSON.parse(JSON.stringify(this.listaNarudzbina)));
      axios
        .post(
          "/narudzbine/admin",
          JSON.parse(JSON.stringify(this.listaNarudzbina))
        )
        .then((response) => console.log(response.data));
      this.listaNarudzbina = [];
      this.$refs["my-modal1"].hide();
    },
    obrisiLek(lek) {
      for (i = 0; i < this.listaNarudzbina.length; i++) {
        if (this.listaNarudzbina[i].sifra === lek.sifra) {
          this.listaNarudzbina.splice(i, 1);
        }
      }
    },
    naruciLek(lek) {
      this.izabranLek = lek;

      this.$refs["my-modal"].show();
    },
    onNaruciLek() {
      tempLek = {
        naziv: this.izabranLek.naziv,
        sifra: this.izabranLek.sifra,
        datumNarudzbine: this.datumNarudzbine,
        kolicina: this.kolicina,
        apotekaId: this.apotekaID,
      };
      this.listaNarudzbina.push(tempLek);
      this.datumNarudzbine = "";
      this.kolicina = "";
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
        .get("lekovi/narucivanje_lek/", { params })
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