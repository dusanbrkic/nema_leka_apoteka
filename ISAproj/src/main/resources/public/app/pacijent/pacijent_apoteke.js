Vue.component("PacijentApoteke", {
    data: function () {
        return {
           cookie: "",
           apoteke: [],
		   currentTutorial: null,
		   currentIndex: -1,
	       searchTitle: "",
	
	       page: 1,
	       count: 0,
	       pageSize: 6,
	
	       pageSizes: [3, 6, 9, 15, 30],
	       
	       redosledi: ["opadajuce", "rastuce"],
	       redosled: "opadajuce",
	       polja: ["naziv", "prosecnaOcena", "adresa", "opis"], 
	       sortirajPo: "naziv",
        
           fromGrade: 0,
	       toGrade: 5,
	       
	       gradeValues: [0,1,2,3,4,5],
        
        blocked: false,
        }
    },
      mounted () {
      	this.retrieveApoteke()
        this.cookie = localStorage.getItem("cookie");
        this.getPenali();
  	},
    template: `
    	<div class="container">
    	
    	      <b-alert style="text-align: center;" v-model="blocked" variant="danger"> Nalog je blokiran! </b-alert>
    	
    	<!-- PRETRAGA -->
    	 <div class="form-group">
            <input
	          type="text"
	          class="form-control"
	          placeholder="Pretraga apoteka"
	          v-model="searchTitle"
	          @input="page = 1; retrieveApoteke();"
	        />
          </div>
          
          
     <!-- FILTRIRANJE PO OCENI-->     
     Minimalna ocena:
      <select v-model="fromGrade" @change="handleGradeMinChange($event)">
          <option v-for="g in gradeValues">
            {{ g }}
          </option>
      </select>
     Maksimalna ocena:
      <select v-model="toGrade" @change="handleGradeMaxChange($event)">
          <option v-for="g2 in gradeValues">
            {{ g2 }}
          </option>
      </select>     
          
          
          
     <br>     
     <br>     
          
          
     <!-- POLJE SORTIRANJA -->
    	Sortiraj po:
      <select v-model="sortirajPo" @change="handleSortChange($event)">
          <option v-for="p in polja">
            {{ p }}
          </option>
      </select>
          
       <!-- REDOSLED SORTIRANJA -->
    	Redosled:
      <select v-model="redosled" @change="handleSortOrderChange($event)">
          <option v-for="s in redosledi" :key="s" :value="s">
            {{ s }}
          </option>
      </select>
      
	<br>
	
	
		      <!-- BIRANJE VELICINE STRANE -->
	        Apoteka po strani:
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
	


		<!-- PRIKAZ APOTEKA -->
		
		
	<b-container id="page_content">
        <b-row>
        <b-card-group deck v-for="apoteka in apoteke" :key="apoteka.id">
          <b-col>
            <b-card 
                class="mb-2"
            >
              <b-card-text>
              <h5> {{apoteka.naziv}} </h5> <br>
                 Adresa: {{apoteka.adresa}} <br>
				 Ocena: {{apoteka.prosecnaOcena}} <br>
				 Opis: {{apoteka.opis}}
              </b-card-text>
              
              <b-button v-if="!blocked" v-on:click="prikaziLekove(apoteka)" variant="primary">Rezervisi lekove</b-button>
              <b-button v-if="!blocked" v-on:click="prikaziTemineDermatologa(apoteka)" variant="primary">Zakazi pregled kod dermatologa</b-button>

            </b-card>
          </b-col>

        </b-row>
      </b-container>
		
		  
<br><br>


      </div>
    `
    ,
    methods: {
        
        redirectToHome: function () {
            app.$router.push("/")
        },
        
	    getRequestParams(searchTitle, page, pageSize, sortirajPo, redosled, fromGrade, toGrade) {
	      let params = {};
	
	      if (searchTitle) { params["title"] = searchTitle; }
	      if (page) { params["page"] = page - 1; }
	      if (pageSize) { params["size"] = pageSize; }
	      if(sortirajPo) {	params["sort"] = sortirajPo; }
		  if(redosled) { params["smer"] = redosled; }
		  if(fromGrade) { params["fromGrade"] = fromGrade; }
	 	  if(toGrade) { params["toGrade"] = toGrade; }
	 	  
	      return params;
	    },
    
	    retrieveApoteke() {
	      const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize, this.sortirajPo, this.redosled, this.fromGrade, this.toGrade);
	
	
		  axios.get("apoteke", {params})
	        .then((response) => {
	          const { apoteke, totalItems } = response.data;
	          this.apoteke = apoteke;
	          this.count = totalItems;
	          
	          for(var i = 0; i< this.apoteke.length; i++){
	          	this.fixAdresu(this.apoteke[i]);
	          }
	
	          console.log(response.data);
	        })
	        .catch((e) => {
	          console.log(e);
	        });
	    },
	    handleSortChange(value) {
	      this.sortirajPo = event.target.value;
	      this.page = 1;
	      this.retrieveApoteke();
	    },
	    
		handleSortOrderChange(value) {
	      this.redosled = event.target.value;
	      this.page = 1;
	      this.retrieveApoteke();
	    },
	    
	   	handleGradeMinChange(value) {
	      this.fromGrade = event.target.value;
	      this.page = 1;
	      this.retrieveApoteke();
	    },
	    
	    handleGradeMaxChange(value) {
	      this.toGrade = event.target.value;
	      this.page = 1;
	      this.retrieveApoteke();
	    },

	    handlePageChange(value) {
	      this.page = value;
	      this.retrieveApoteke();
	    },
	
	    handlePageSizeChange(event) {
	      this.pageSize = event.target.value;
	      this.page = 1;
	      this.retrieveApoteke();
	    },
	    prikaziLekove(apoteka){
	    	localStorage.setItem('apotekaID', apoteka.id);
	    	app.$router.push("/home-pacijent/rezervacija");
	    },
	    prikaziTemineDermatologa(apoteka){
	    	localStorage.setItem('apotekaID', apoteka.id);
	    	app.$router.push("/home-pacijent/zakazivanje_kod_dermatologa");
	    },
	    fixAdresu(ap) {
	    
	      const words = ap.adresa.split("|");
	      
	      if(words.length != 0){
	      	ap.adresa = words[0];
	      }
	    },
	    getPenali() {
	    	 axios.get("korisnici/blocked", 
	    	 {
	    	 	params: {
                            'cookie': this.cookie,
                        }
	    	 })
	        .then((response) => {

              		this.blocked = false;
	          
	        })
	        .catch((e) => {
	          if (e.request.status == 403) {
	          		console.log("blokiran");
	                this.blocked = true;
              }
	          console.log(e);
	        });
	    }
    }
});

