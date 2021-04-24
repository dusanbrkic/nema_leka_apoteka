Vue.component("PacijentLekovi", {
    data: function () {
        return {
           cookie: "",
           nesto: 'waiting for server response',
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
				istekRezervacije: "",
				cookie: ""
		   },

	
	       page: 1,
	       count: 0,
	       pageSize: 6,
	
	       pageSizes: [3, 6, 9, 15, 30],
	       
	       
	       ukupnaCena: 0,
	       losUnos: false,
	       uspeh: false,
        
        }
    },
      mounted () {
      	this.cookie = localStorage.getItem("cookie")
      	this.retrieveLekovi()
  	},
    template: `
    	<div>
    	<div class="container">
    	
    	<b-alert style="text-align: center;" v-model="this.losUnos" variant="danger"> Los unos podataka! </b-alert>
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Uspesna rezervacija, pogledajte svoj email.</b-alert>
    	

      <!-- BIRANJE VELICINE STRANE -->
        Lekova po strani:
        <select v-model="pageSize" @change="handlePageSizeChange($event)">
          <option v-for="size in pageSizes" :key="size" :value="size">
            {{ size }}
          </option>
      </select>

      
      <!-- NAVIGACIJA PO STRANAMA -->
      <b-pagination
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        @change="handlePageChange"
      ></b-pagination>




		<!-- PRIKAZ LEKOVA -->
		

        <b-row>
        <b-card-group deck v-for="lek in lekovi">
            <b-card>
              <b-card-text>
              <h5> {{lek.naziv}} </h5>
              <p class="card-text">
                 Sastav: {{lek.sastav}} <br>
				 Cena: {{lek.cena}}		<br>
				 Kolicina: {{lek.kolicina}}
			  </p>
              </b-card-text>
              
              <b-button v-on:click="prikaziLek(lek)" variant="primary">Rezervisi</b-button>

            </b-card>
        </b-card-group>
        </b-row>
        
        
        <!-- Modal -->


	<b-modal ref="my-modal" hide-footer title="Rezervacija leka">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="prikaziLek">
        <h3>{{this.izabranLek.naziv}}</h3>
        Sastav: {{this.izabranLek.sastav}} <br>
        Cena po komadu: {{this.izabranLek.cena}} <br>
       <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
       
        <b-form-input
                id="input-3"
                type="date"
                type="number"
                @input="kolicinaChange();"
                v-model="izabranLek.kolicina"
        ></b-form-input>
            
        <b-form-group id="input-group-2" label="Preuzeti do:" label-for="input-2">
            <b-form-input
                id="input-2"
                type="date"
                v-model="izabranLek.istekRezervacije"
            ></b-form-input>
            
        <br>
        
		<div v-html="ukupnaCena"> </div>

          <b-button type="submit" variant="primary" v-on:click="rezervacijaLeka">Rezervisi</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
  	</div>	


      </div>
</div>
    `
    ,
    methods: {

		kolicinaChange() {
	      this.ukupnaCena = "Ukupna cena: " + this.izabranLek.cena*this.izabranLek.kolicina;
	    },

        redirectToHome: function () {
            app.$router.push("/")
        },
        
	    getRequestParams(searchTitle, page, pageSize) {
	      let params = {};
	
	      if (searchTitle) {
	        params["title"] = searchTitle;
	      }
	
	      if (page) {
	        params["page"] = page - 1;
	      }
	
	      if (pageSize) {
	        params["size"] = pageSize;
	      }
	
	      return params;
	    },
    
	    rezervacijaLeka() {
	      const params = this.getRequestParams(this.izabranLek.sifra, this.izabranLek.kolicina, this.izabranLek.istekRezervacije);
	
		  this.losUnos = false;
		  this.uspeh = false;
	        
	      axios.get("lekovi/rezervacija-leka/", 
			    { headers: { "Content-Type": "application/json; charset=UTF-8" },
			      params: 
			       { 		
			       			sifra: this.izabranLek.sifra,
			      			kolicina: this.izabranLek.kolicina,
			      			istekRezervacije: this.izabranLek.istekRezervacije,
			      			cookie: this.cookie
			       },
			    }).then((response) => {
        			this.$refs['my-modal'].hide();
        			this.retrieveLekovi();
	          		this.uspeh = true;
		        })
		        .catch((e) => {
		        
		        	this.$refs['my-modal'].hide();
		        
		        	this.losUnos = true;
		        });
		        
		   this.retrieveLekovi();

	    },

	    handlePageChange(value) {
	      this.page = value;
	      this.retrieveLekovi();
	    },
	
	    handlePageSizeChange(event) {
	      this.pageSize = event.target.value;
	      this.page = 1;
	      this.retrieveLekovi();
	    },
        
        
	prikaziLek(lek) {
	  	this.reverseLek = JSON.parse(JSON.stringify(lek));
	  	this.izabranLek = JSON.parse(JSON.stringify(lek));
	  	console.log(this.izabranLek);
	  	this.kolicinaChange();
        this.$refs['my-modal'].show()
        },
        
        getNumbers:function(start,stop){
            return new Array(stop-start).fill(start).map((n,i)=>n+i);
        },
        
        
    retrieveLekovi() {
	      const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize);
	
	
		  axios.get("lekovi", {params})
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
    }
});

