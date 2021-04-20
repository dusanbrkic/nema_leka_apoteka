Vue.component("Apoteke", {
    data: function () {
        return {
           nesto: 'waiting for server response',
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
        
        }
    },
      mounted () {
      	this.retrieveApoteke()
  	},
    template: `
    	<div class="container">
    	
    	
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
              
              <!-- <a href="#/apoteke" class="stretched-link"></a> -->

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
    }
});

