Vue.component("Apoteka", {
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
         	
         }
         
         
         } 

    },
      mounted () {
      	this.loadApoteka(this.$route.params.id);
  	},
    template: `
		<div>
	      <div class="container">
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
	          <input type="button" v-on:click="redirectToHome" value="Back To Home">
	          <input type="submit" v-on:submit="saveApoteka" value="Save information">
	        </form>
      </div>


      </div>
    `
    ,
    methods: {
    	loadLekoveApoteka: function(){
    		let info = {
                params: {
                    "apotekaID": this.apoteka.id
                }
            }
    		console.log(this.apoteka.id + "asda");
    		axios.get("apoteke/basic/",info).then(response => this.apoteka.lekovi = response.data)
    	},
    	saveApoteka: function(){
    	
    	},
    	loadApoteka(id){
    		axios.get("apoteke/" + id)
      		.then(response => {
      		(this.apoteka = response.data)
      		console.log(this.apoteka.id);
      		this.loadLekoveApoteka();
      		})
      		
		},
		redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        }
	}
});