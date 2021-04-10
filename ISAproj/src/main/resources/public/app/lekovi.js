Vue.component("Lekovi", {
    data: function () {
        return {
           nesto: 'waiting for server response',
           lekovi: [],
		   currentTutorial: null,
		   currentIndex: -1,
	       searchTitle: "",
	
	       page: 1,
	       count: 0,
	       pageSize: 6,
	
	       pageSizes: [6, 9, 15, 30],
        
        }
    },
      mounted () {
      	this.retrieveLekovi()
  	},
    template: `
    	<div>
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
        Lekova po strani:
        <select v-model="pageSize" @change="handlePageSizeChange($event)">
          <option v-for="size in pageSizes" :key="size" :value="size">
            {{ size }}
          </option>
      </select>

<br>
<br>


		<!-- PRIKAZ LEKOVA -->
   
		  <div class="row">
				<div class="col-sm-4" v-for="lek in lekovi">
				  <div class="panel panel-default">
					<div class="panel-heading">{{lek.naziv}}</div>
					  Sastav: {{lek.sastav}} <br>
					  Uputstvo: {{lek.uputstvo}}
				</div>
		  </div>


      </div>
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

	    handlePageChange(value) {
	      this.page = value;
	      this.retrieveLekovi();
	    },
	
	    handlePageSizeChange(event) {
	      this.pageSize = event.target.value;
	      this.page = 1;
	      this.retrieveLekovi();
	    },
        
    }
});

