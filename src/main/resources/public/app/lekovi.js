Vue.component("Lekovi", {
    data: function () {
        return {
           
            today: new Date(),
           
            pretragaLekova: '',
            totalLekovi: '',
            pageLekova: 1,
            pageSizeLekova: 6,
            totalpreporuceniLekovi: 0,
            preporuceniLekoviPrikaz: [],
            selectedTerm: null,
            pageSizes: [3, 6, 9, 15, 30],
           
            lekovi: [],
           
            showPraznaPoljaAlert: false,
            lekNijeDostupan: false,
            odabirLekaTitle: 'Preporuci lek pacijentu:',
            nedostupanLek: '',
            kolicinaNedostupnogLeka: '-1'
        }
    },

    mounted() {
		this.loadLekovi();
    },
    template:
        `
          <div>

            <b-container>


		      <!-- BIRANJE VELICINE STRANE -->  
		 <form>
			  <div class="form-group row ">
			    <h6 for="size" class="col-sm-2 col-form-label">Lekova po strani:</h6>
			    <div class="col-sm-2">
				    <b-select id="size" class="form-control" v-model="pageSizeLekova" @change="handlePageSizeChange">
				      <option v-for="size in pageSizes" :key="size" :value="size">
				        {{ size }}
				      </option>
					</b-select>
			    </div>
			  </div>
		</form>
		  
		  
		  

              <b-row>
                <b-col>
                  <b-form-input
                      placeholder="Pretrazi lekove..."
                      v-on:input="handleSearch"
                      v-model="pretragaLekova"
                      type="search"/>
                </b-col>
              </b-row>
            </b-container>
            <b-container>
              <b-row v-for="chungus in lekovi">
                <b-col v-for="lek in chungus">
                  <b-card
                      style="margin: 10px auto; max-width: 300px"
                      :header="lek.naziv"
                      :footer="lek.footer"
                      class="text-left"
                      bg-variant="light"
                  >
                  
                    <b-card-text>
                      Tip: {{ lek.tip }} <br>
                      <div class="card-footer bg-transparent border-secondary"/>
					  Opis: {{ lek.sastav }}
             		</b-card-text>

<div class="d-flex justify-content-center">
    <div class="text-center">
        <div class="ratings" style="max-height: 190px"> 
        	<div style="margin: -55px auto;">
	        	<span class="product-rating">{{ lek.prosecnaOcena }}</span><span>/5</span>
		            <div class="stars">
		            
		            
		            <div v-if="lek.star == 5">
		            	 <five class="fa fa-star"></i>
		            	 <five class="fa fa-star"></i>
		            	 <five class="fa fa-star"></i>
		            	 <five class="fa fa-star"></i> 
		            	 <five class="fa fa-star"></i> 
		           	</div>
		           	
		           	<div v-if="lek.star == 4">
		            	 <four class="fa fa-star"></i>
		            	 <four class="fa fa-star"></i>
		            	 <four class="fa fa-star"></i>
		            	 <four class="fa fa-star"></i> 
		           	</div>
		           	
		           	<div v-if="lek.star == 3">
		            	 <three class="fa fa-star"></i>
		            	 <three class="fa fa-star"></i>
		            	 <three class="fa fa-star"></i>
		           	</div>
		           	
		           	<div v-if="lek.star == 2">
		            	 <two class="fa fa-star"></i>
		            	 <two class="fa fa-star"></i>
		           	</div>
		           	
		           	<div v-if="lek.star == 1">
		            	 <one class="fa fa-star"></i>
		           	</div>
		           	
		           	</div>
	            <div class="rating-text"> <small class="text-muted">{{lek.brojOcena}}</small> </div>
            </div>
        </div>
    </div>
</div>

<br>

                    <p class="card-text text-left"><small class="text-muted">
                      Na stanju: {{lek.kolicina }}
                    </small></p> 
                    
                  </b-card>
                </b-col>
              </b-row>
            </b-container>
            
            <b-pagination
                v-if="totalLekovi>0"
                pills
                align="center"
                style="margin: 10px auto;"
                v-model="pageLekova"
                :total-rows="totalLekovi"
                :per-page="pageSizeLekova"
                @change="handlePageChange"
            ></b-pagination>
            <h6 align="center" style="margin: 10px;" v-if="totalLekovi==0">Nema lekova za prikaz</h6>
          
          </div>

        `
    ,
    methods: {

        zakazivanje_forma: function () {
            this.$bvModal.show('zakazivanjeModal')
        },

        handlePageChangePreporucenihLekova: function (value) {
            this.pagepreporucenihLekova = value
            this.dobavi_preporucene_lekove(this.pagepreporucenihLekova)
        },
        handlePageChange(value) {
            this.pageLekova = value
            this.loadLekovi()
        },
        handleSearch: function () {
            this.pageLekova = 1
            this.loadLekovi()
        },
        handlePageSizeChange(value) {
	      this.pageSizeLekova = value;
	      this.pageLekova = 1;
	      this.loadLekovi();
	    },
        loadLekovi: function () {
            axios
                .post("/lekovi/getAllLekovi", {
                    'page': this.pageLekova - 1,
                    'pageSize': this.pageSizeLekova,
                    'pretraga': this.pretragaLekova,
                })
                .then(response => {
                    this.lekovi = []
                    this.totalLekovi = response.data['totalElements']
                    //console.log(this.totalLekovi);
                    for (let i = 0; i < this.totalLekovi/3; i = i + 1) {
                        this.lekovi.push([])
                        for (let j = 0; j < 3; j = j + 1) {
                            let item = response.data.content[j + i * 3]
                            if (typeof item === 'undefined')
                                return
                            item.footer = "Cena: " + item.cena + " RSD";
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
                            
                            this.lekovi[i].push(item)
                        }
                    }

                })
        },

    }
});
