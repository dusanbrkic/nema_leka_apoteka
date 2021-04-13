Vue.component("DodajLekAdmin", {
    data: function () {
        return {

			lek: {
        sifra: "",
				naziv: "",
				uputstvo: "",
				tip : "",
				oblikLeka: "",
				dodatneNapomene: "",
		    sastav: "",
		    kolicina: "",
		    cena: "",
		    istekVazenjaCene: "",
				staraCena: "",
        cookie: ""
        },
        lekovi: [],
		    currentTutorial: null,
		    currentIndex: -1,
	      searchTitle: "",
	      page: 1,
	      count: 0,
	      pageSize: 6,
		  apotekaID : "",
	      pageSizes: [3, 6, 9, 15, 30],
	       
	      redosledi: ["opadajuce", "rastuce"],
	       
	      redosled: "opadajuce",
	       
	      polja: ["naziv"],
	       
	      sortirajPo: "naziv"
        }
    },
    mounted() {
      this.lek.cookie = localStorage.getItem("cookie");
      this.apotekaID = localStorage.getItem("apotekaID");
      this.retrieveLekove();
    },
    template: `
     <div>
      <div class="container">
        <h2>Dodavanje Leka</h2>
        <form @submit.prevent="saveLek">

          <div class="form-group">
            <label for="naziv">Naziv leka:</label>
            <input type="text" class="form-control" id="naziv" name="naziv" v-model="lek.naziv"/>
          </div>
          <div class="form-group">
            <label for="sifra">Sifra leka:</label>
            <input type="text" class="form-control" id="sifra" name="sifra" v-model="lek.sifra"/>
          </div>
          <div class="form-group">
            <label for="uputstvo">Uputstvo leka:</label>
            <input type="text" class="form-control" id="uputstvo" name="uputstvo" v-model="lek.uputstvo"/>
          </div>
		  <div class="form-group">
            <label for="tip">Tip leka:</label>
			<select id = "tip" v-model="lek.tip">
			  <option disabled value="">Tip leka</option>
			  <option>ANTIBIOTIK</option>
			  <option>ANALGETIK</option>
			  <option>HISTAMINIK</option>
			</select>
          </div>
          <div class="form-group">
            <label for="oblikLeka">Oblik leka:</label>
           	<select id = "oblikLeka" v-model="lek.oblikLeka">
			  <option disabled value="">Oblik leka</option>
			  <option>PRASAK</option>
			  <option>KAPSULA</option>
			  <option>TABLETA</option><option>MAST</option><option>PASTA</option><option>GEL</option>
			  <option>SIRUP</option>
			  <option>RASTVOR</option>
			</select>
      </div>
          <div class="form-group">
            <label for="sastavLeka">Sastav leka:</label>
            <input type="text" class="form-control" id="sastavLeka" name="sastavLeka" v-model="lek.sastav"/>
          </div>
          <div class="form-group">
          <label for="cena">Cena:</label>
          <input v-model="lek.cena" id="cena" name="cena" type="number" placeholder="Cena" />
          </div>
          <div class="form-group">
          <label for="kolicina">Kolicina:</label>
          <input v-model="lek.kolicina" id="kolicina" name="kolicina" type="number" placeholder="Kolicina" />
          </div>
          <div class="form-group">
            <label for="dodatneNapomene">Dodatne napomene:</label>
            <textarea v-model="lek.dodatneNapomene" id="dodatneNapomene" name="dodatneNapomene" placeholder="Dodatne napomene"/>
          </div>
          
         <div class="mt-2">
         	<b-button variant="primary" type="button" v-on:click="redirectToHome" class="ml-2">Return to home</b-button>
         	<b-button variant="primary" type="button" v-on:click="saveLek" class="ml-2">Dodaj lek</b-button>
         </div>
        </form>
      </div>

		
      </div>
      
      
    `
    ,
    methods: {
        redirectToHome: function () {
            app.$router.push("/home-admin_apoteke")
        },
        saveLek: function(){
        	console.log(this.lek);
        	axios.post('/lekovi',this.lek).then(response => console.log(response));
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
	    retrieveLekove() {
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
    },
});