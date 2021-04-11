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
        
        }
    },
      mounted () {
      	this.retrieveApoteke()
  	},
    template: `
    	<div class="container">
		
		 <!-- NAVIGACIJA PO STRANAMA -->
	      <b-pagination
	        v-model="page"
	        :total-rows="count"
	        :per-page="pageSize"
	        size="lg"
	        @change="handlePageChange"
	      ></b-pagination>
	
			<br>
	
	      <!-- BIRANJE VELICINE STRANE -->
	        Apoteka po strani:
	        <select v-model="pageSize" @change="handlePageSizeChange($event)">
	          <option v-for="size in pageSizes" :key="size" :value="size">
	            {{ size }}
	          </option>
	      </select>
	
		<br>
		<br>

		<!-- PRIKAZ APOTEKA -->
		<div class="container">    
		  <div class="row">
			<div class="col-sm-4" v-for="apoteka in apoteke" :key="apoteka.id">
			  <div class="panel panel-default">
				<div class="panel-heading">{{apoteka.naziv}}</div>
				  Adresa: {{apoteka.adresa}} <br>
				  Ocena: {{apoteka.prosecnaOcena}} <br>
				  Opis: {{apoteka.opis}}
			</div>
		  </div>
		</div><br><br>


      </div>
    `
    ,
    methods: {
        
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
    
	    retrieveApoteke() {
	      const params = this.getRequestParams(this.searchTitle,this.page,this.pageSize);
	
	
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

