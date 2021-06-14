Vue.component("DodajLekAdmin", {
    data: function () {
        return {

			lek: {
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
        lekovi: [],
          dodatLek: false,
          postojiLek: false,
		  cenaGreska: false,
		  kolicinaGreska: false, 
		  tipGreska: false,
		  oblikGreska : false,
		  currentTutorial: null,
		  currentIndex: -1,
	      searchTitle: "",
	      page: 1,
	      count: 0,
	      pageSize: 6,
		  apotekaID : "",
	      pageSizes: [3, 6, 9, 15, 30],
	      
	      redosledi: ["opadajuce", "rastuce"],
	       
	      redosled: "opadajuce",
	       
	      polja: ["naziv"],
	       
	      sortirajPo: "naziv"
        }
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
            <b-nav-item href="#/admin-apoteke-apoteka"">Izmeni podatke o apoteci</b-nav-item>
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
    <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
      <b-container id="page_content">
      
      
           <b-card style="max-width: 450px; max-margin: 5px auto;" title="Lekovi">
      	<b-list-group flush>
	    <b-list-group-item v-for="lek in lekovi"
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
				<b>{{lek.naziv}}</b>
				<div>
				</div>
                  
		            </b-col>
		    </b-col>
		    <b-col md="auto" class="float-right">
		    	<div>
		    		<b-button type="button" size="sm" v-on:click="dodajLek(lek)" variant="primary">Dodaj u ponudu</b-button>
		    	</div>
            </b-col>
    </b-list-group-item>
	</b-list-group>
     </b-card>
		</b-container>
      </div>
      
      
    `
    ,
    methods: {
		dodajLek(lek){
			axios.get("/narudzbine/lekNarudzbina",{params:
                            {
                                'sifra': lek.sifra,
                                'cookie': this.cookie
                            }}).then(response => this.retrieveLekove());
           this.retrieveLekove();
		},
    	redirectToApotekaIzmeni: function(){
    	
    	},
    	logout: function () {
    		localStorage.clear()
    		app.$router.push("/");
        },
        redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        },
        saveLek: function(){
        	this.postojiLek = false;
        	this.dodatLek = false;
			this.cenaGreska = false;
			this.kolicinaGreska = false;
			this.tipGreska = false;
			this.oblikGreska = false;
			if(this.lek.tip == ""){
				this.tipGreska = true;
				return;
			}
			if(this.lek.oblikLeka == ""){
				this.oblikGreska = true;
				return;
			}
        	axios.post('/lekovi',this.lek).then(response => {this.dodatLek = true})
        	.catch(error => {
                if (error.request.status==404) {
					this.postojiLek = true;
                } else if (error.request.status==400) {
                    this.postojiLek = true;
                }
            })
        },
	    getRequestParams(searchTitle, page, pageSize, sortirajPo, redosled, apotekaID,cookie) {
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
		  params["cookie"] = cookie;
	      return params;
	    },
    	logout: function () {
    		localStorage.clear()
    		app.$router.push("/");
        },
        	redirectToApotekaIzmeni: function(){
				app.$router.push("/apoteka/" + this.apoteka.id);
		},
	    retrieveLekove() {
			const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize, this.sortirajPo, this.redosled, this.apotekaID,this.cookie);
	
	
		  axios.get("lekovi/ostali", {params})
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
    },
});