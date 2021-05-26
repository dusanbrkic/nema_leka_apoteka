Vue.component("PacijentLekovi", {
    data: function () {
        return {
            cookie: "",
            nesto: 'waiting for server response',
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
            
            ukupnaCena: 0,
	        losUnos: false,
	        uspeh: false,
	        blocked: false,
	        ocena: 0,
	        prethodna_ocena: 0,
        }
    },

    mounted() {
        this.cookie = localStorage.getItem("cookie")
		this.loadLekovi();
		this.getPenali();
    },
    template:
        `
          <div>

            <b-container>
            
        <b-alert style="text-align: center;" v-model="this.losUnos" variant="danger"> Los unos podataka! </b-alert>
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Uspesna rezervacija, pogledajte svoj email.</b-alert>
    	<b-alert style="text-align: center;" v-model="blocked" variant="danger"> Nalog je blokiran! </b-alert>

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
		  
		  
		      <!-- PRIKAZ LEKOVA -->
		        
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
                      v-if="!lek.alergija"
                  >
                  
                    <b-card-text>
                      Tip: {{ lek.tip }} <br>
                      <div class="card-footer bg-transparent border-secondary"/>
					  Opis: {{ lek.sastav }}
             		</b-card-text>
<br>

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
<div class="d-flex justify-content-center">
				<b-button style="margin: 10px auto;" v-if="!blocked" v-on:click="prikaziLek(lek)" variant="primary">Rezervisi</b-button>
				<b-button style="margin: 10px auto;" v-if="!blocked && lek.pravoOcene" v-on:click="pokaziOceniModal(lek)" variant="success">Oceni</b-button>
				<b-button style="margin: 10px auto;" v-if="!blocked && !lek.pravoOcene" v-on:click="pokaziOceniModal(lek)" variant="success" disabled>Oceni</b-button>
                <b-button style="margin: 10px auto;" v-if="!blocked" v-on:click="alergican(lek)" variant="danger">Alergija</b-button>
</div>

                    <p class="card-text text-left"><small class="text-muted">
                      Na stanju: {{lek.kolicina }}
                    </small></p> 
                    
                  </b-card>

                  <b-card
                      style="margin: 10px auto; max-width: 300px"
                      :header="lek.naziv"
                      :footer="lek.footer"
                      class="text-left text-light"
                      bg-variant="danger"
                      v-if="lek.alergija"
                  >
                  
                    <b-card-text>
                      Tip: {{ lek.tip }} <br>
                      <div class="card-footer bg-transparent border-light"/>
					  Opis: {{ lek.sastav }}
             		</b-card-text>
                    
                  </b-card>


                </b-col>
              </b-row>
            </b-container>
            
            
            <!-- Page Index -->           
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
          
          
          
          
           <!-- Modal Rezervisanja Leka-->

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





           <!-- Modal Ocenjivanja Leka-->
     
     <b-modal ref="oceni-modal" hide-footer :title="this.izabranLek.naziv">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="pokaziOceniModal">

       <div class="d-flex justify-content-center">
    <div class="text-center">
        <div class="ratings" style="max-height: 190px"> 
        	<div style="margin: -55px auto;">
	        	<span class="product-rating">{{ this.izabranLek.prosecnaOcena }}</span><span>/5</span>
		            <div class="stars">

			            <div v-if="this.izabranLek.star == 5">
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i> 
			            	 <five class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="this.izabranLek.star == 4">
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="this.izabranLek.star == 3">
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="this.izabranLek.star == 2">
			            	 <two class="fa fa-star"></i>
			            	 <two class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="this.izabranLek.star == 1">
			            	 <one class="fa fa-star"></i>
			           	</div>
		           	
		           	</div>
	            <div class="rating-text"> <small class="text-muted">{{this.izabranLek.brojOcena}}</small> </div>
            </div>
        </div>
    </div>
</div>
       <br>
       
       
        <div class="d-flex justify-content-center">      
       	  <b-button type="submit" v-if="this.prethodna_ocena == 0 || this.prethodna_ocena != 1" variant="light" v-on:click="setJedan">1</b-button>
       	  <b-button type="submit" v-if="this.prethodna_ocena == 1" variant="danger" v-on:click="setJedan">1</b-button>
       	  
       	  <b-button type="submit" v-if="this.prethodna_ocena == 0 || this.prethodna_ocena != 2" variant="light" v-on:click="setDva">2</b-button>
       	  <b-button type="submit" v-if="this.prethodna_ocena == 2" variant="danger" v-on:click="setDva">2</b-button>
       	  
       	  <b-button type="submit" v-if="this.prethodna_ocena == 0 || this.prethodna_ocena != 3" variant="light" v-on:click="setTri">3</b-button>
       	  <b-button type="submit" v-if="this.prethodna_ocena == 3" variant="danger" v-on:click="setTri">3</b-button>
       	  
       	  <b-button type="submit" v-if="this.prethodna_ocena == 0 || this.prethodna_ocena != 4" variant="light" v-on:click="setCetiri">4</b-button>
       	  <b-button type="submit" v-if="this.prethodna_ocena == 4" variant="danger" v-on:click="setCetiri">4</b-button>
       	  
          <b-button type="submit" v-if="this.prethodna_ocena == 0 || this.prethodna_ocena != 5" variant="light" v-on:click="setPet">5</b-button>
          <b-button type="submit" v-if="this.prethodna_ocena == 5" variant="danger" v-on:click="setPet">5</b-button>
          
        </div>
           </b-form>
      	</b-card>
    	</b-modal>	
          
          
          
          </div>

        `
    ,
    methods: {
    	kolicinaChange() {
	      this.ukupnaCena = "Ukupna cena: " + this.izabranLek.cena*this.izabranLek.kolicina;
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
	    rezervacijaLeka() {
	
		  this.losUnos = false;
		  this.uspeh = false;
	        
	      axios.get("rezervacije/rezervacija-leka/", 
			    { headers: { "Content-Type": "application/json; charset=UTF-8" },
			      params: 
			       { 		
			       			sifra: this.izabranLek.sifra,
			      			kolicina: this.izabranLek.kolicina,
			      			istekRezervacije: this.izabranLek.istekRezervacije.toString() + "T02:00:00",
			      			cookie: this.cookie
			       },
			    }).then((response) => {
        			this.$refs['my-modal'].hide();
        			this.loadLekovi();
	          		this.uspeh = true;
		        })
		        .catch((e) => {
		        
		        	this.$refs['my-modal'].hide();
		        	this.losUnos = true;
		        	console.log(e);
		        });
		        
		   this.loadLekovi();

	    },
        loadLekovi: function () {
            axios
                .post("/lekovi/getAllLekoviAlergican", {
                    'page': this.pageLekova - 1,
                    'pageSize': this.pageSizeLekova,
                    'pretraga': this.pretragaLekova,
                    'cookie'  : this.cookie,
                })
                .then(response => {
                    this.lekovi = []
                    this.totalLekovi = response.data['totalElements']

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
        prikaziLek(lek) {
		  	this.reverseLek = JSON.parse(JSON.stringify(lek));
		  	this.izabranLek = JSON.parse(JSON.stringify(lek));
		  	console.log(this.izabranLek);
		  	this.kolicinaChange();
	        this.$refs['my-modal'].show()
        },
        
        alergican(lek) {
	  		console.log(lek.sifra);
       	 	axios.get("lekovi/setAlergija", 
			 {
			 	params: {
			                'cookie': this.cookie,
			                'id': lek.sifra
			            }
			 })
			.then((response) => {
			this.loadLekovi();

			})
			.catch((e) => {
			  console.log(e);
			});
		},
		pokaziOceniModal(lek) {
		  	this.izabranLek = JSON.parse(JSON.stringify(lek));
		  	this.prethodna_ocena = 0;
		    this.getOcena();
	        this.$refs['oceni-modal'].show()
		},
		
		oceni() {
       	 	axios.get("lekovi/oceni", 
			 {
			 	params: {
			                'cookie': this.cookie,
			                'id': this.izabranLek.sifra.toString(),
			                'ocena': this.ocena
			            }
			 })
			.then((response) => {
			this.$refs['oceni-modal'].hide();
			this.loadLekovi();

			})
			.catch((e) => {
			  console.log(e);
			});
		},
		getOcena() {
		
			if(this.izabranLek.sifra != null) {
	       	 	axios.get("lekovi/getOcena", 
				 {
				 	params: {
				                'cookie': this.cookie,
				                'id': this.izabranLek.sifra,
				            }
				 })
				.then((response) => {
				this.prethodna_ocena = response.data.ocena;
				})
				.catch((e) => {
				  console.log(e);
				});
			}
		},
		setJedan() { this.ocena = 1; this.oceni(); },
		setDva() { this.ocena = 2; this.oceni();},
		setTri() { this.ocena = 3; this.oceni();},
		setCetiri() { this.ocena = 4; this.oceni();},
		setPet() { this.ocena = 5; this.oceni(); },
		
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
