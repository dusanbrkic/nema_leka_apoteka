Vue.component("PacijentNarudzbenice", {
    data: function () {
        return {
           cookie: "",
           narudzbenice: [],
        
        }
    },
      mounted () {
      	this.cookie = localStorage.getItem("cookie")
      	this.retrieveNarudzbine()
  	},
    template: `
    	<div>
    	<div class="container">
    	

		<!-- PRIKAZ PORUDZBINA -->
		

        <b-row>
        <b-card-group deck v-for="n in narudzbenice">
            <b-card>
              <b-card-text>
              <h5> {{n.id}} </h5>
              <p class="card-text">
                 Apoteka {{n.ApotekaNaziv}} <br>
			  </p>
              </b-card-text>

            </b-card>
        </b-card-group>
        </b-row>
        



      </div>
</div>
    `
    ,
    methods: {
        redirectToHome: function () {
            app.$router.push("/")
        },

		retrieveNarudzbine() {

	        
	         axios.get("kruac", 
			    { headers: { "Content-Type": "application/json; charset=UTF-8" },
			      params: {},
			    }).then((response) => {
	          		//this.narudzbenice = response.data;
	          		console.log(response.data);
		        })
		        .catch((e) => {
		        	console.log(e);
		        });
	    },

    }
});

