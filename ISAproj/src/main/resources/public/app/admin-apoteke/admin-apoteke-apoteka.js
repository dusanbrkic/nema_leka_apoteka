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
         cookie : "",
         
         } 

    },
    mounted () {
        this.cookie = localStorage.getItem("cookie");
        this.loadApoteka();
    },
    template: `
		<div>
	<link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
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
	            <label for="prezime">Prosecna ocena:</label>
	            <input type="text" class="form-control" id="prosecnaOcena" v-model="apoteka.prosecnaOcena">
	          </div>
	
	          <div class="form-group">
	            <label for="datumRodjenja">Opis:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.opis">
	          </div>
	          <div class="form-group">
	            <label for="datumRodjenja">Lekovi:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.opis">
	          </div>
				<b-button type="button" size="sm" v-on:click="redirectToHome()" variant="primary">Povratak na glavnu stranu</b-button>
				<b-button type="button" size="sm" v-on:click="saveApoteka()" variant="primary">Sacuvaj podatke</b-button>
            <div>
            <b-list-group flush>
            <b-list-group-item v-for="lek in lekovi"
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
                <!--   <div v-if="lek.kolicina == null">
                        <b-button type="button" size="sm" v-on:click="dodajLek(lek)" variant="primary">Dodaj u ponudu</b-button>
                    </div>
                    <div v-else>
                      <b-button type="button" size="sm" v-on:click="naruciLek(lek)" variant="primary">Dodaj u narudzbenicu</b-button>  -->
                    </div>  
                </b-col>
            </b-row>
        </b-list-group-item>
        </b-list-group>
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
		redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        }
	}

});