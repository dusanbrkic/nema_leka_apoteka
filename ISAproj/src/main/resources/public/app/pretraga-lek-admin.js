Vue.component("PretragaLekAdmin", {
    data: function () {
        return {
           nesto: 'waiting for server response',
           apotekaID: "",
           lekovi: [],
		   currentTutorial: null,
		   currentIndex: -1,
	       searchTitle: "",
		
	       page: 1,
	       count: 0,
	       pageSize: 6,
		
	       pageSizes: [3, 6, 9, 15, 30],
	       
	       redosledi: ["opadajuce", "rastuce"],
	       
	       redosled: "opadajuce",
	       
	       polja: ["naziv"],
	       
	       sortirajPo: "naziv"
        
        }
    },
      mounted () {
        this.apotekaID = localStorage.getItem("apotekaID");
      	this.retrieveLekove()
  	},
    template: `
    	<div class="container">
    	
    	
    	<!-- PRETRAGA -->
    	 <div class="form-group">
            <input
	          type="text"
	          class="form-control"
	          placeholder="Pretraga lekova"
	          v-model="searchTitle"
	          @input="page = 1; retrieveLekove();"
	        />
          </div>
          
          
     <!-- FILTRIRANJE PO OCENI-->     
          
          
          
    
          
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
		<!-- PRIKAZ LEKOVA -->
		<div class="row">
			<div class="col-sm-4" v-for="lek in lekovi">
			<div class="panel panel-default">
				<div class="panel-heading">{{lek.naziv}}</div>
				Sastav: {{lek.sastav}} <br>
				Uputstvo: {{lek.uputstvo}}
		</div>
				
  </div>	

<br><br>
			<div class="mt-2">
        	<b-button variant="primary" type="button" v-on:click="redirectToHome" class="ml-2">Return to home</b-button>
        </div>

      </div>
    `
    ,
    methods: {
        
        redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        },
        
	    getRequestParams(searchTitle, page, pageSize, sortirajPo, redosled, apotekaID) {
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
	      return params;
	    },
    
	    retrieveLekove() {
			const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize, this.sortirajPo, this.redosled, this.apotekaID);
	
	
		  axios.get("lekovi/aa/", {params})
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
    }
});