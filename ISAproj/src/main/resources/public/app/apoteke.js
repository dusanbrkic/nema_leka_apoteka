Vue.component("Apoteke", {
    data: function () {
        return {
           cookie: "",
           apoteke: [],
		   currentTutorial: null,
		   currentIndex: -1,
	       searchNaziv: "",
	       searchAdresa: "",
	
	       pageApoteka: 1,
	       count: 0,
	       pageSize: 6,
	       pageSizeApoteka: 6,
	       pageSizes: [3, 6, 9, 15, 30],
	       totalApoteke: '',
	       
	       redosledi: ["opadajuce", "rastuce"],
	       redosled: "opadajuce",
	       polja: ["naziv", "prosecnaOcena", "adresa", "opis"], 
	       sortirajPo: "naziv",
        
           fromGrade: 0,
	       toGrade: 5,
	       
	       gradeValues: [0,1,2,3,4,5],
        
           ocena: 0,
	       prethodna_ocena: 0,
        }
    },
      mounted () {
      	this.retrieveApoteke()
  	},
    template: `
    	<div class="container">
 
    	
    	<!-- PRETRAGA NAZIV-->
 <div class="form-row">    	  	
    	 <div class="form-group col-md-6">
            <input
	          type="text"
	          class="form-control"
	          placeholder="Pretrazi naziv"
	          v-model="searchNaziv"
	          @input="page = 1; retrieveApoteke();"
	        />
          </div>
          
          
         <!-- PRETRAGA ADRESA-->
    	 <div class="form-group col-md-6">
            <input
	          type="text"
	          class="form-control"
	          placeholder="Pretrazi adresu"
	          v-model="searchAdresa"
	          @input="page = 1; retrieveApoteke();"
	        />
          </div>  
</div>
          
     <!-- FILTRIRANJE PO OCENI-->       
          
         <form>
		  <div class="form-group row " style="margin: 2px auto;" >
		    <h6 for="ocenaOD" class="col-sm-2 col-form-label">Raspon ocena:</h6>
		    <div class="col-sm-1">
			    <b-select id="ocenaOD" class="form-control" v-model="fromGrade" @change="handleGradeMinChange($event)">
			      <option v-for="g in gradeValues">
			        {{ g }}
			      </option>
				</b-select>
			</div>
			<div class="col-sm-1">		
				<b-select id="ocenaDO" class="form-control" v-model="toGrade" @change="handleGradeMaxChange($event)">
		          <option v-for="g2 in gradeValues">
		            {{ g2 }}
		          </option>
				</b-select>
				
		    </div>
		  </div>
		</form>
          
          
          
          
       <!-- REDOSLED SORTIRANJA -->
      
  		 <form>
		  <div class="form-group row " style="margin: 2px auto;" >
		    <h6 for="red" class="col-sm-2 col-form-label">Redosled:</h6>
		    <div class="col-sm-2">
			    <b-select id="red" class="form-control" v-model="redosled" @change="handleSortOrderChange($event)">
			      <option v-for="s in redosledi" :key="s" :value="s">
			        {{ s }}
			      </option>
				</b-select>
		    </div>
		  </div>
		</form>
      
			
				      <!-- BIRANJE VELICINE STRANE -->  
		 <form>
			  <div class="form-group row " style="margin: 2px auto;">
			    <h6 for="size" class="col-sm-2 col-form-label">Apoteka po strani:</h6>
			    <div class="col-sm-1">
				    <b-select id="size" class="form-control" v-model="pageSizeApoteka" @change="handlePageSizeChange">
				      <option v-for="size in pageSizes" :key="size" :value="size">
				        {{ size }}
				      </option>
					</b-select>
			    </div>
			  </div>
		</form>
          
       
	

		<!-- PRIKAZ APOTEKA -->

              <b-row v-for="chungus in apoteke">
                <b-col v-for="apoteka in chungus">

                  <b-card
                      style="margin: 10px auto; max-width: 300px"
                      :header="apoteka.naziv"
                      class="text-left"
                      bg-variant="light"
                  >
                  
                    <b-card-text>
						Adresa: {{apoteka.adresa}} <br>
                      	<div class="card-footer bg-transparent border-secondary"/>
				 		Opis: {{apoteka.opis}}
             		</b-card-text>
<br>

<div class="d-flex justify-content-center">
    <div class="text-center">
        <div class="ratings" style="max-height: 190px"> 
        	<div style="margin: -55px auto;">
	        	<span class="product-rating">{{ apoteka.prosecnaOcena }}</span><span>/5</span>
		            <div class="stars">

			            <div v-if="apoteka.star == 5">
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i> 
			            	 <five class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="apoteka.star == 4">
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="apoteka.star == 3">
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="apoteka.star == 2">
			            	 <two class="fa fa-star"></i>
			            	 <two class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="apoteka.star == 1">
			            	 <one class="fa fa-star"></i>
			           	</div>
		           	
		           	</div>
	            <div class="rating-text"> <small class="text-muted">{{apoteka.brojOcena}}</small> </div>
            </div>
        </div>
    </div>
</div>           
      </b-card>
    </b-col>
  </b-row>

            <!-- SELECT PAGE -->           
            <b-pagination
                v-if="totalApoteke>0"
                pills
                align="center"
                style="margin: 10px auto;"
                v-model="pageApoteka"
                :total-rows="totalApoteke"
                :per-page="pageSizeApoteka"
                @change="handlePageChange"
            ></b-pagination>
            <h6 align="center" style="margin: 10px;" v-if="totalApoteke==0">Nema apoteka za prikaz</h6>
          
     


      </div>
    `
    ,
    methods: {
        
        redirectToHome: function () {
            app.$router.push("/")
        },
    
	    retrieveApoteke() {
        
        	console.log(this.fromGrade);
        	console.log(this.toGrade);
        
	       axios.post("/apoteke/getall", {
                    'pretragaNaziv': this.searchNaziv,
                    'pretragaAdresa': this.searchAdresa,
                    'cookie': this.cookie,
                    'page': this.pageApoteka - 1,
                    'pageSize': this.pageSizeApoteka,
                    'smer': (() => {
                    	if(this.redosled == "opadajuce") {
                    		return true
                    	}
                            return false
                    })(),
                    'ocenaOD': this.fromGrade + ".0",
                    'ocenaDO': this.toGrade + ".0",
                })
                .then(response => {
                
	          	this.apoteke = [];
				this.totalApoteke = response.data['totalElements']

		  		for (let i = 0; i < this.totalApoteke/3; i = i + 1) {
                    this.apoteke.push([])
                    for (let j = 0; j < 3; j = j + 1) {
                        let item = response.data.content[j + i * 3]
                        if (typeof item === 'undefined')
                            return
                        item.prosecnaOcena = Math.round(item.prosecnaOcena * 10) / 10
                        
                        if(item.prosecnaOcena > 4.5) {
                        	item.star = 5;
                        }
                        else if(item.prosecnaOcena > 3.5) {
                        	item.star = 4;
                        }
                        else if(item.prosecnaOcena > 2.5) {
                        	item.star = 3;
                        }
                        else if(item.prosecnaOcena > 1.5) {
                        	item.star = 2;
                        }
                        else {
                        	item.star = 1;
                        }
                        
                        if(item.brojOcena % 10 > 1 && item.brojOcena % 10 < 5) {
                        	item.brojOcena = item.brojOcena + " ocene";
                        }
                        else {
                        	item.brojOcena = item.brojOcena + " ocena";
                        }
                        this.fixAdresu(item);
                        this.apoteke[i].push(item)
                    }
                }
                })
		        .catch((e) => {
		          console.log(e);
		        });
	        
	    },
	    handleSortChange(value) {
	      this.sortirajPo = value;
	      this.pageApoteka = 1;
	      this.retrieveApoteke();
	    },
	    
		handleSortOrderChange(value) {
	      this.redosled = value;
	      this.pageApoteka = 1;
	      this.retrieveApoteke();
	    },
	    
	   	handleGradeMinChange(value) {
	      this.fromGrade = value;
	      this.pageApoteka = 1;
	      this.retrieveApoteke();
	    },
	    
	    handleGradeMaxChange(value) {
	      this.toGrade = value;
	      this.pageApoteka = 1;
	      this.retrieveApoteke();
	    },

	    handlePageChange(value) {
	      this.pageApoteka = value;
	      this.retrieveApoteke();
	    },
	
	    handlePageSizeChange(value) {
	      this.pageSizeApoteka = value;
	      this.pageApoteka = 1;
	      this.retrieveApoteke();
	    },

	    fixAdresu(ap) {
	    
	      const words = ap.adresa.split("|");
	      
	      if(words.length != 0){
	      	ap.adresa = words[0];
	      }
	    },

    }
});

