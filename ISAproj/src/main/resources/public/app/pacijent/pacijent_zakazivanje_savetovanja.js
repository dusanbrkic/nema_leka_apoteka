Vue.component("PacijentZakazivanjeSavetovanja", {
    data: function () {
        return {

            datumPregleda: '',
            datumTermina: '',
            today: new Date(),
            pregledStart: '',
            pregledEnd: '',
 

            
            cookie: '',
            rezervacije: [],
            fields: [
                {
                    key: 'id',
                    sortable: false
                },
                {
                    key: 'apoteka',
                    sortable: false
                },
                {
                	key: 'adresa',
                	sortable: false
                },
                {
                    key: 'cenaSavetovanja',
                    sortable: true
                },
                {
                    key: 'ocenaApoteke',
                    sortable: true
                },
            ],

            table_is_busy: false,
            apoteke: [],
            infoFilled: false,
            sortBy: 'ocenaApoteke',
        	sortDesc: false,
        	items: [],
        	farmaceuti: [],
        	sortByfarmaceuti: 'ocenaFarmaceuta',
        	sortDescfarmaceuti: false,
        	selectedApoteka: -1,
        	selectedFarmaceut: -1,
        	farmaceuti_fields: [
                {
                    key: 'id',
                    sortable: false
                },
                {
                    key: 'ime',
                    sortable: false
                },
                {
                    key: 'prezime',
                    sortable: false
                },
                {
                    key: 'ocenaFarmaceuta',
                    sortable: true
                },
            ],
            uspeh: false,
            blocked: false,
        }
    },

    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
        this.pregled = JSON.parse(localStorage.getItem("pregled"))
        this.getPenali();
    },
    template:
        `
          <div class="container">
        
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Uspesna rezervacija, pogledajte svoj email.</b-alert>
      	<b-alert style="text-align: center;" v-model="blocked" variant="danger"> Nalog je blokiran! </b-alert>
      	
          <!--modal zakazivanja-->

            <template>
                          <b-container>
              <label for="pregled-datepicker">Datum termina:</label>
              <b-form-datepicker
                  :min="today"
                  required="true"
                  placeholder="Izaberite datum"
                  locale="sr-latn"
                  id="pregled-datepicker"
                  v-model="datumPregleda"
                  class="mb-2"/>
                <b-row>
                  <b-col>
                    <label for="timepicker-start">Pocetak termina:</label>
                    <b-form-timepicker
                        v-model="pregledStart"
                        id="timepicker-start"
                        placeholder="Izaberite vreme"
                        locale="sr-latn"
                        start="08:00"
                        end="20:00"
                    />
                  </b-col>
                  <b-col>
                    <label for="timepicker-end">Kraj termina:</label>
                    <b-form-timepicker
                        v-model="pregledEnd"
                        id="timepicker-end"
                        placeholder="Izaberite vreme"
                        locale="sr-latn"
                        start="08:00"
                        end="20:00"
                    />
                  </b-col>
                </b-row>
              <b-button v-if="!blocked" v-on:click="setInfoFilled" variant="success">
                Pretrazi
              </b-button>
              </b-container>
            </template>

          
         <br>
         
         <!-- FARMACEUTI MODAL -->

        <div id="modal" class="modal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Zakazi savetovanje kod farmaceuta</h5>
		      </div>
		      <div class="modal-body">
		        	   <b-row>
				        <b-table
				            ref="farmaceuti_table"
				            hover
				            :items="farmaceuti"
				            :fields="farmaceuti_fields"
				            :busy.sync="table_is_busy"
				            :sort-by.sync="sortByfarmaceuti"
				  			:sort-desc.sync="sortDescfarmaceuti"
				            @row-clicked="myRowFarmaceuti"
				        >
				        </b-table>
				      </b-row>
		      </div>
		      <div class="modal-footer">
		        	<b-button variant="secondary" v-on:click="zatvori">Otkazi</b-button>
		        	<b-button variant="success" v-on:click="zakazi_savetovanje">Zakazi</b-button>
		      </div>
		    </div>
		  </div>
		</div>
         
         
         
          <!--table-->
<div v-if="!blocked">
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-container>
            
          <b-row v-if="infoFilled">
            <b-table
                ref="table"
                hover
                :items="items"
                :fields="fields"
                :busy.sync="table_is_busy"
                :sort-by.sync="sortBy"
      			:sort-desc.sync="sortDesc"
                @row-clicked="myRowClickHandler"
            >
            </b-table>
          </b-row>
          
	          <b-row v-if="!infoFilled">
	          		<h5 class="text-center"> Odaberite datum i vreme termina </h5>  
	          </b-row>  
            
        </b-container>
      </b-card>
</div>
          </div>

        `
    ,
    methods: {

        zatvori: function (){
              		$('#modal').modal('hide');
        },
        zakazi_savetovanje: function () {
        		this.zatvori();
                axios
                    .get("pregledi/createSavetovanje", {
                        params: {
                            'start': new Date(this.datumPregleda + " " + this.pregledStart),
                            'end': new Date(this.datumPregleda + " " + this.pregledEnd),
                            'cookie': this.cookie,
                            'idFarmaceuta': this.farmaceuti[this.selectedFarmaceut].id,
                            'idApoteke': this.selectedApoteka
                        }
                    })
            		.then(response => {
						this.uspeh = true;
						this.loadApoteke();
                })
       	        .catch((e) => {
       	        this.uspeh = false;
		        	console.log(e);
		        });
        },
        
      	loadApoteke: async function () {
            this.table_is_busy = true
            this.items = []
            
            if(this.datumPregleda == null || this.pregledStart == null || this.pregledEnd == null){
            	return null;
            }
            
            await axios.get("apoteke/sveApotekeSaSlobodnimFarmaceutom", {
                params: {
                    'start': new Date(this.datumPregleda + " " + this.pregledStart),
                    'end': new Date(this.datumPregleda + " " + this.pregledEnd),
                }
            })
            .then(response => {
                    let rez = response.data;
                    for (const a of rez) {
                    	this.apoteke.push(a);
                    	a.prosecnaOcena = Math.round(a.prosecnaOcena * 10) / 10
                        this.items.push({
                            id: a.id,
                            apoteka: a.naziv,
                            adresa: a.adresa,
                            cenaSavetovanja: a.cenaSavetovanja,
                            ocenaApoteke: a.prosecnaOcena,
                          	_rowVariant: "info"
                            
                        })
                    }
                })
       	        .catch((e) => {
		        	console.log(e);
		        });
            this.table_is_busy = false
        },
        itemProvider: function (ctx) {
            //return this.loadApoteke()
            this.loadApoteke();
            return this.items;
        },
      	myRowClickHandler(record, index) {
      		this.selectedApoteka = this.items[index].id;
      		this.loadFarmaceute();
      		$("#modal").modal();
		},
		myRowFarmaceuti(record, index){
		
			this.selectedFarmaceut = index;
			
			for(let i = 0; i < this.farmaceuti.length; i++){
				this.farmaceuti[i]._rowVariant = 'light';
			}
			
			this.farmaceuti[index]._rowVariant = 'success';

		},
		setInfoFilled() {
           this.infoFilled = true;
           this.loadApoteke();
        },
        
        loadFarmaceute: async function () {
            this.table_is_busy = true
            this.farmaceuti = []
            
            if(this.datumPregleda == null || this.pregledStart == null || this.pregledEnd == null){
            	return null;
            }
            
            await axios.get("korisnici/slobodni_farmaceuti_apoteke", {
                params: {
                    'start': new Date(this.datumPregleda + " " + this.pregledStart),
                    'end': new Date(this.datumPregleda + " " + this.pregledEnd),
                    'idApoteke': this.selectedApoteka,
                }
            })
            .then(response => {
                    let rez = response.data;
                    for (const a of rez) {
                    	a.prosecnaOcena = Math.round(a.prosecnaOcena * 10) / 10
                        this.farmaceuti.push({
                        	id: a.username,
                            ime: a.ime,
                            prezime: a.prezime,
                            ocenaFarmaceuta: a.prosecnaOcena,
                          	_rowVariant: "none"
                            
                        })
                    }
                    selectedFarmaceut = -1;
                    //this.farmaceuti[0]._rowVariant = "success";
                })
       	        .catch((e) => {
		        	console.log(e);
		        });
            this.table_is_busy = false
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
