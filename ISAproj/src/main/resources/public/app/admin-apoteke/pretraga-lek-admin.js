Vue.component("PretragaLekAdmin", {
    data: function () {
        return {
           nesto: 'waiting for server response',
           apotekaID: "",
           lekovi: [],
		   currentTutorial: null,
		   currentIndex: -1,
	       searchTitle: "",
	       reverseLek : "",
		   izabranLek : {
				sifra: "",
				naziv: "",
				uputstvo: "",
				tip : "",
				oblikLeka: "",
				dodatneNapomene: "",
				sastav: "",
				kolicina: "",
				cena: "",
				istekVazenjaCene: "",
				staraCena: "",
				cookie: ""
		   },
	       page: 1,
	       count: 0,
	       pageSize: 6,
			
	       pageSizes: [3, 6, 9, 15, 30],
	       
	       redosledi: ["opadajuce", "rastuce"],
	       
	       redosled: "opadajuce",
	       
	       polja: ["naziv"],
	       
	       sortirajPo: "naziv"
        
        }
    },
      mounted () {
      	this.cookie = localStorage.getItem("cookie");
        this.apotekaID = localStorage.getItem("apotekaID");
      	this.retrieveLekove()
  	},
    template: `
    <div>
    <!-- nav bar -->
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
            <b-nav-item v-on:click="redirectToApotekaIzmeni">Izmeni podatke o apoteci</b-nav-item>
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
		<!-- PRIKAZ LEKOVA -->
		<b-row>
		 <b-card-group deck v-for="lek in lekovi">
            <b-card
            >
            
            <p class="card-text">
                Naziv : {{lek.naziv}}
            </p>
            <p class="card-text">
                Sifra : {{lek.sifra}}
            </p>
            <p class="card-text">
                Sastav : {{lek.sastav}}
            </p>
           <p class="card-text">
                Uputstvo : {{lek.uputstvo}}
            </p>
            <p class="card-text">
                Tip : {{lek.tip}}
            </p>
            <p class="card-text">
                Oblik : {{lek.oblikLeka}}
            </p>
            <p class="card-text">
                Cena : {{lek.cena}}
            </p>
            <p class="card-text">
                Kolicina : {{lek.kolicina}}
            </p>
            <p class="card-text">
                Stara cena : {{lek.staraCena}}
            </p>
            <p class="card-text">
               	Datum isteka vazenja cene : {{lek.istekVazenjaCene}}
            </p>
              <b-button v-on:click="deleteLek(lek.sifra)" variant="primary">Obrisi</b-button>
              <b-button v-on:click="prikaziPromeniLek(lek)" variant="primary">Izmeni</b-button>
            </b-card>
           </b-card-group>
           </b-row>
	
	<!-- forma za promenu podataka -->
	<b-modal ref="my-modal" hide-footer title="Izmena podataka lekova">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="onIzmeniLek">
        <h3>{{this.izabranLek.naziv}}</h3>
        <b-form-group id="input-group-1" label="Cena:" label-for="input-1">
            <b-form-input
                id="input-1"
                type="number"
                v-model="izabranLek.cena"
                min = "0"
            ></b-form-input>
       <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
                v-model="izabranLek.kolicina"
                min = "0"
            ></b-form-input>
        <b-form-group id="input-group-2" label="Datum isteka vazenja cene:" label-for="input-2">
            <b-form-input
                id="input-2"
                type="date"
                v-model="izabranLek.istekVazenjaCene"
            ></b-form-input>

       <b-form-group id="input-group-3" label="Stara cena:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
                v-model="this.izabranLek.staraCena"
                min = "0"
            ></b-form-input>
          <b-button type="submit" variant="primary">Sacuvaj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
  	</div>	

<br><br>
			<div class="mt-2">
        	<b-button variant="primary" type="button" v-on:click="redirectToHome" class="ml-2">Return to home</b-button>
        </div>

      </div>
    `
    ,
    methods: {
		onIzmeniLek: function(){
			this.izabranLek.cookie = this.cookie;
			axios.put("lekovi",this.izabranLek).then(response => this.retrieveLekove())
			this.retrieveLekove()
			this.$refs['my-modal'].hide()
		},
        onReset: function (event) {
            event.preventDefault()
            this.izabranLek = this.reverseLek
        },

	  prikaziPromeniLek(lek) {
	  	this.reverseLek = JSON.parse(JSON.stringify(lek));
	  	this.izabranLek = JSON.parse(JSON.stringify(lek));
	  	console.log(this.izabranLek);
        this.$refs['my-modal'].show()
      },
        redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        },
        
	    getRequestParams(searchTitle, page, pageSize, sortirajPo, redosled, apotekaID) {
	      let params = {};
	
	      if (searchTitle) {
	        params["title"] = searchTitle;
	      }
		  else{
			params["title"] = "";
		  }
	
	      if (page) {
	        params["page"] = page - 1;
	      }
	
	      if (pageSize) {
	        params["size"] = pageSize;
	      }
	      
	      if(sortirajPo) {
	      	params["sort"] = sortirajPo;
	      }
	
		  if(sortirajPo) {
	      	params["smer"] = redosled;
	      }
		  params["apotekaID"] = apotekaID;
	      return params;
	    },
    	logout: function () {
    		localStorage.clear()
    		app.$router.push("/");
        },
        	redirectToApotekaIzmeni: function(){
				app.$router.push("/apoteka/" + this.apoteka.id);
		},
		deleteLek: function(sifra){
        	let info = {
                params: {
                	"sifraLeka" : sifra,
                    "cookie": this.cookie
                }
            }
            axios.delete("/lekovi/deleteLek",info).then(response => this.retrieveLekove())
        	this.retrieveLekove()
        },
	    retrieveLekove() {
			const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize, this.sortirajPo, this.redosled, this.apotekaID);
	
	
		  axios.get("lekovi/aa/", {params})
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
    }
});