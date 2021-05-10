Vue.component("PacijentZakaziPregled", {
    data: function () {
        return {
           cookie: "",
           nesto: 'waiting for server response',
	       apoteka: "",
	       uspeh: false,
	       pregledi: [],
        
           selected: "",
        }
    },
      mounted () {
      	this.cookie = localStorage.getItem("cookie")
      	this.apoteka = localStorage.getItem("apotekaID");
      	this.retrievePregledi()
  	},
    template: `
    	<div>
    	<div class="container">
    	
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Zakazali ste pregled, pogledajte svoj email.</b-alert>
    	


        
      <b-container id="page_content">

        <b-row>
        <b-card-group deck v-for="pregled in pregledi">
            <b-card>
              <b-card-text>
              <h5> {{pregled.username}} </h5>
              <p class="card-text">
              	 Datum: {{pregled.date}} <br>
                 Vreme: {{pregled.time}} <br>
                 Cena: {{pregled.cena}} <br>
			  </p>
              </b-card-text>
              
              <div v-on:click="prikaziPregled(pregled)" variant="primary" class="stretched-link" ></div>

            </b-card>
        </b-card-group>
        </b-row>


      </b-container>
        
        
        
        
        
        
        
        <!-- Modal -->


	<b-modal ref="my-modal" hide-footer title="Da li zelite zakazati pregled?">
        <b-form>
		<div class="col text-center">
          	<b-button type="submit" variant="primary" v-on:click="zakaziPregled">Zakazi</b-button>
          </div>
           </b-form>
    	</b-modal>	
  	</div>	


      </div>
</div>
    `
    ,
    methods: {


        redirectToHome: function () {
            app.$router.push("/")
        },

        
	prikaziPregled(pregled) {
		this.selected = pregled;
        this.$refs['my-modal'].show();
        },
        
            
	zakaziPregled() {
        axios.get("/pregledi/zakaziPregled", {
		  		params:
                        {
                            'id_pregleda': this.selected.id,
                            'cookie': this.cookie
                        }
		  })
		  .then((response) => {
				this.uspeh = true;
	        })
	        .catch((e) => {
	          console.log(e);
	        });
	        
        this.$refs['my-modal'].hide();
        },
        
        
    retrievePregledi() {
	
			//console.log(this.apoteka);
	
		  axios.get("/pregledi/slobodniPregledi", {
		  		params:
                        {
                            'id_apoteke': this.apoteka,
                        }
		  })
	        .then((response) => {
	          this.pregledi = response.data;
	          for (i = 0; i < this.pregledi.length; i++) {
				  this.pregledi[i].date = this.pregledi[i].start.slice(0, 10);
				  this.pregledi[i].time = this.pregledi[i].start.slice(11, 20);
				}
	        })
	        .catch((e) => {
	          console.log(e);
	        });
	       
        },
    }
});

