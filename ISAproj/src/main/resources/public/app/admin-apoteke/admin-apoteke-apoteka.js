Vue.component("AdminApoteka", {

    data() {
    	
        return {
         apoteka: {
         	id: "",
         	naziv: "",
         	adresa: "",
         	prosecnaOcena: "",
         	opis: "",
         	dermatolozi: "",
         	farmaceuti: "",
         	lekovi: ""
         	
         },
         lekovi : [],
         dermatolozi: [],
         farmaceuti: [],
         cookie : "",
         
         } 

    },
    mounted () {
        this.cookie = localStorage.getItem("cookie");
        this.loadApoteka();
        this.loadDermatologe();
		this.loadFarmaceute();
    },
    template: `
		<div>
	<link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
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
  
      <b-container id="page_content">
      <b-card style="max-width: 500px; margin: 30px auto;">
        <b-form @submit.prevent="onSubmit">
	        <h2>Izmena podataka apoteke</h2>
	        <form @submit.prevent="saveApoteka">
	
	          <div class="form-group">
	            <label for="username">Naziv:</label>
	            <input type="text" class="form-control" id="naziv" v-model="apoteka.naziv">
	          </div>
	
	          <div class="form-group">
	            <label for="ime">Adresa:</label>
	            <input type="text" class="form-control" id="adresa" v-model="apoteka.adresa">
	          </div>
	
	          <div class="form-group">
	            <label>Opis:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.opis">
	          </div>
	          <div class="form-group">
	            <label for="datumRodjenja">Lekovi:</label>
	        <b-list-group flush style="max-height: 200px; 
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
            <b-list-group-item v-for="lek in lekovi" class="list-group-item px-0">
            
            <b-row align-v="centar" >
                <b-col md="auto">
                    <b>{{lek.naziv}}</b>
                    
                      <p class="text-sm mb-0"> Trenutno na stanju: {{lek.kolicina}}  </p>
                      <p class="text-sm mb-0"> Cena: {{lek.cena}}  </p>
                        </b-col>
                </b-col>
                <b-col md="auto" class="float-right"> 
                </b-col>
            </b-row>
        </b-list-group-item>
        </b-list-group>
        <label>Dermatolozi:</label>
        <b-list-group flush style="max-height: 200px; 
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
    	<b-list-group-item v-for="dermatolog in dermatolozi" class="list-group-item px-0">
    		<b>{{dermatolog.ime}} {{dermatolog.prezime}}</b>
    	</b-list-group-item>	
    	</b-list-group>
    	<label>Farmaceuti:</label>
		</b-list-group-item>
        </b-list-group>
        <b-list-group flush style="max-height: 200px; 
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
    	<b-list-group-item v-for="farmaceut in farmaceuti" class="list-group-item px-0">
    		<b>{{farmaceut.ime}} {{farmaceut.prezime}}</b>
    	</b-list-group-item>	
    	</b-list-group>
	    </div>
			<b-button type="button" size="sm" v-on:click="redirectToHome()" variant="primary">Povratak na glavnu stranu</b-button>
			<b-button type="button" size="sm" v-on:click="saveApoteka()" variant="primary">Sacuvaj podatke</b-button>
        <div>

      </div>

	        </form>
		</b-card>
		</b-containter>
      </div>
    `
    ,
    methods: {
    	loadLekoveApoteka: function(){
    		let info = {
                params: {
                    "cookie": this.cookie
                }
            }
    		axios.get("lekovi/basic/",info).then(response => this.lekovi = response.data)
    	},
    	saveApoteka: function(){
    	
    	},
    	loadApoteka(){
            let info = {
                params: {
                    "cookie": this.cookie
                }
            }
    		axios.get("apoteke/getByAdmin/",info)
      		.then(response => {
      		(this.apoteka = response.data)
      		console.log(this.apoteka.id);
      		this.loadLekoveApoteka();
      		console.log(this.lekovi);
      		})
      		
		},
		loadDermatologe(){
			let info = {
                params: {
                    "cookie": this.cookie
                }
            }
            axios.get("zdravstveniradnik/getAllDermatologApotekaList/",info)
      		.then(response => {
      		(this.dermatolozi = response.data)
  			console.log(this.dermatolozi);
      		})
		},
		loadFarmaceute(){
			let info = {
                params: {
                    "cookie": this.cookie
                }
            }
            axios.get("zdravstveniradnik/getAllFarmaceutsApoteka/",info)
      		.then(response => {
      		(this.farmaceuti = response.data)
  			console.log(this.farmaceuti);
      		})

		},
		redirectToHome: function () {
			console.log(this.lekovi);
            app.$router.push("/home-admin_apoteke")
        },
            logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
	}

});