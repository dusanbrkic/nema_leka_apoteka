Vue.component("AdminSlobodniTermini", {

  data: function () {
    return {
    	cookie: "",
      page: 1,
      count: 0,
      pageSize: 6,
      apotekaID: "",
      dermatolozi :[],
      searchTitle :"",
      izabranDermatolog:[],
      pageSizes: [3, 6, 9, 15, 30],
      cena: "",
      datumPregleda: "",
      pocetakPregleda: "",
      krajPregleda: "",
      "pregledDTO": {
   			   		"start": "",
   		"end": "",
   		"dijagnoza" : "",
   		"pregledObavljen": false,
   		"pregledZakazan" :false,
   		"apotekaId" : "",
   		"username" : "",
   						
   		},
      }
   },
   mounted(){
   		this.cookie = localStorage.getItem("cookie");
   		this.apotekaID = localStorage.getItem("apotekaID");
   		this.retrieveDermatologe();
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
            <b-nav-item >Izmeni podatke o apoteci</b-nav-item>
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
	          @input="page = 1; retrieveDermatologe();"
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
   <!-- PRIKAZ LEKOVA -->
		<b-row>
		 <b-card-group deck v-for="dermatolog in dermatolozi">
            <b-card
            >
            
            <p class="card-text">
                Ime  i prezime: {{dermatolog.ime}} {{dermatolog.prezime}}
            </p>
           <p class="card-text">
               Korisnicko ime: {{dermatolog.username}}
            </p>
                       
                       <p class="card-text">
               Cena: {{dermatolog.cena}}
            </p>
                       <p class="card-text">
              Pocetak radnog vremena: {{fixDate(dermatolog.radnoVremePocetak)}}
 			</p>
 		   <p class="card-text">
              Kraj radnog vremena: {{fixDate(dermatolog.radnoVremeKraj)}}
 			</p>
 		 		   <p class="card-text">
              Prosecna ocena: {{dermatolog.prosecnaOcena}}
 			</p>
              <b-button  v-on:click="dodajTermin(dermatolog)" variant="primary">Dodaj slobodan termin</b-button>
            </b-card>
           </b-card-group>
           </b-row>
		</b-container>
		
			<!-- forma za narucivanje leka -->
	<b-modal ref="my-modal" hide-footer title="Dodavanje slobodnog termina">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="onDodajTermin">
        <h3>{{this.izabranDermatolog.ime}}</h3>
          <b-form-group id="input-group-3" label="Datum slobodnog termina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="date"
				v-model="datumPregleda"
            ></b-form-input>
                   <b-form-group id="input-group-3" label="Pocetak termina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="time"
				v-model="pocetakPregleda"
            ></b-form-input>
                             <b-form-group id="input-group-3" label="Kraj termina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="time"
				v-model="krajPregleda"
            ></b-form-input>
		<br>
          <b-button type="submit" variant="primary">Dodaj termin</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
   </div>
   `,
   
   
   methods:{
   		fixDate(date){
   			
   			return moment(date).format('HH:SS')
   		
   		},
   		onDodajTermin(){
 
   			console.log(this.datumPregleda);
   			console.log(this.pocetakPregleda);
   			const dateStart = this.datumPregleda + "T" + this.pocetakPregleda;
   			const dateEnd = this.datumPregleda + "T" +  this.krajPregleda;
   			this.pregledDTO.start = dateStart;
   			this.pregledDTO.end = dateEnd;
   			this.pregledDTO.dijagnoza = "";
   			this.pregledDTO.pregledObavljen = false;
   			this.pregledDTO.pregledZakazan = false;
   			this.pregledDTO.apotekaId = this.apotekaID;
   			this.pregledDTO.username = this.izabranDermatolog.username;
   			console.log(this.pregledDTO);
   			axios.post("pregledi/addSlobodanTermin",this.pregledDTO).then(response => console.log(response.data));
   			this.$refs["my-modal"].hide();
   		},
   		dodajTermin(dermatolog){
   			this.$refs["my-modal"].show();
   			this.izabranDermatolog = dermatolog;
   			console.log(this.izabranDermatolog);
   		},
   		logout(){
   		
   		},
	    getRequestParams(
	      searchTitle,
	      page,
	      pageSize,
	      cookie)
	      {
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
		      params["cookie"] = cookie;
	      return params;
	      },
   		retrieveDermatologe(){
   			const params = this.getRequestParams(this.searchTitle, this.page,
        this.pageSize,
        this.cookie);
        	axios.get("zdravstveniradnik/getAllDermatologApoteka/",{params}).then((response) => {const {dermatolozi, totalItems} = response.data;
        	this.dermatolozi = dermatolozi;
        	this.count = totalItems;
        	console.log(response.data)}).catch((e) => {
          	console.log(e);
        	});
   		},
   			    handleSortChange(value) {
	      this.sortirajPo = event.target.value;
	      this.page = 1;
	      this.retrieveDermatologe();
	    },
	    
		handleSortOrderChange(value) {
	      this.redosled = event.target.value;
	      this.page = 1;
	      this.retrieveDermatologe();
	    },

	    handlePageChange(value) {
	      this.page = value;
	      this.retrieveDermatologe();
	    },
	
	    handlePageSizeChange(event) {
	      this.pageSize = event.target.value;
	      this.page = 1;
	      this.retrieveDermatologe();
	    },
   }
});