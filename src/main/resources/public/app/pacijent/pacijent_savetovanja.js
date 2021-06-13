Vue.component("PacijentSavetovanja", {
    data: function () {
        return {
            cookie: '',
            pregledi: [],
            fields: [
                {
                    key: 'idSavetovanja',
                    sortable: false
                },
                {
                    key: 'apoteka',
                    sortable: false
                },
                {
                	key: 'farmaceut',
                	sortable: false
                },
                {
                    key: 'datum',
                    sortable: true
                },
                {
                    key: 'pocetak',
                    sortable: true
                },
                                {
                    key: 'kraj',
                    sortable: true
                },
                {
                    key: 'cena',
                    sortable: true
                },
                {
                    key: 'status',
                    sortable: false
                },
            ],

            table_is_busy: false,
            
           greska: false,
	       uspeh: false,
	       
	       sortBy: '',
           sortDesc: false,
		 
		 
		 	items: [],  
		    selectedPregled: {
		   		apoteka: "",
		   		dermatolog: "",
		   		datum: "",
		   		pocetak: "",
		   		kraj: "",
		   		cena: "",
		   		dijagnoza: "",
		   		preporuceniLekovi: [],
		   		status: "",
		   		},
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-container>


          <b-row>
            <b-table
                ref="table"
                id="table-id"
                hover
                :items="itemProvider"
                :fields="fields"
                :busy.sync="table_is_busy"
				:sort-by.sync="sortBy"
				:sort-desc.sync="sortDesc"
				sort-icon-left
				@row-clicked="myRowClickHandler"
            >
            </b-table>
          </b-row>
        </b-container>
      </b-card>
      
      
           <!-- PREGLED MODAL -->
      
      <div id="modal" class="modal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Informacije Pregleda</h5>
		      </div>
		      <div class="modal-body">

				<b-row>
					<b-col>Apoteka: </b-col>
					<b-col>{{selectedPregled.apoteka}}</b-col>
				</b-row>
				<b-row>
					<b-col>Farmaceut:</b-col>
					<b-col>{{selectedPregled.farmaceut}}</b-col>
				</b-row>
				<b-row>
					<b-col>Datum:</b-col>
					<b-col>{{selectedPregled.datum}}</b-col>
				</b-row>
				<b-row>
					<b-col>Početak:</b-col>
					<b-col>{{selectedPregled.pocetak}}</b-col>
				</b-row>
				<b-row>
					<b-col>Kraj:</b-col>
					<b-col>{{selectedPregled.kraj}}</b-col>
				</b-row>
				<b-row>
					<b-col>Cena: </b-col>
					<b-col>{{selectedPregled.cena}}</b-col>
				</b-row>
				<br>
				<b-row>
					<b-col>Dijagnoza:</b-col>
					<b-col>{{selectedPregled.dijagnoza}}</b-col>
				</b-row>
				<br>
				<b-row>
					<b-col>Preporučeni lekovi:</b-col>
					<b-col>
						<b-row v-for="pregledLek in selectedPregled.preporuceniLekovi">
                    		<b-col> - {{ pregledLek.lek.naziv }} - {{ pregledLek.trajanjeTerapije }} dana </b-col>
                  		</b-row>
                  		<b-row v-if="selectedPregled.preporuceniLekovi.length==0"><b-col>Nema preporucenih lekova</b-col></b-row>
					</b-col>
				</b-row>
				<br>
				<b-row>
					<b-col>Status:</b-col>
					<b-col>{{selectedPregled.status}}</b-col>
				</b-row>
		      </div>
		      <div class="modal-footer">
					<b-button type="button" class="btn btn-secondary" data-dismiss="modal">Zatvori</b-button>
		  		    <b-button v-if="selectedPregled.rowVariant == 'info' " @click="otkazi()" class="mr-1">
			          Otkazi pregled
			        </b-button>      	
		      </div>
		    </div>
		  </div>
		</div>
      
      
      
      </div>
    `,
    methods: {
    
    checkDate(r) {
    
    	
        var today = new Date();
        
        var nextDay = new Date();
    	
    	nextDay.setDate(today.getDate() + 1);
    	
   		if(r.pregledObavljen) {
   			r.rowVariant = 'success';
   			r.status = 'Obavljen'
   		}
    	else {
    		console.log(nextDay.toISOString().slice(0, 10) + " - " + r.start.slice(0, 10));
		   	if(nextDay.toISOString().slice(0, 10) > r.start.slice(0, 10)) {
		   		if(today.toISOString().slice(0, 10) == r.start.slice(0, 10)){
    				r.rowVariant = 'warning';
    				r.status = 'Potrebno obaviti'
    			}
    			else {	
					r.rowVariant = 'danger';
					r.status = 'Nije obavljen'
				}
	    	}
			else {	
					r.rowVariant = 'info';
					r.status = 'Potrebno obaviti'
			}
    	}

    },
    otkazi(index) {
            $('#modal').modal('hide');    
        	this.greska = false;
		    this.uspeh = false;
		    
		    
		     axios.get("pregledi/otkazi-pregled", 
			    {		
			    params: {
			       'id_pregleda': this.selectedPregled.id
			       }
			    }).then((response) => {
   		   			this.$root.$emit("bv::refresh::table", "table-id");
		        })
		        .catch((e) => {
		        	this.greska = true;
		        });
      },
    
        loadPregledi: async function () {
            this.table_is_busy = true
            items = []
            this.pregledi = [];
            await axios
                .get("pregledi/savetovanja_farmaceuta", {
                    params:
                        {
                            'cookie': this.cookie,
                            'sortBy': this.sortBy,
                            'sortDesc': this.sortDesc,
                        }
                })
                .then(response => {
                    let rez = response.data;
                    console.log(rez);
                    for (const p of rez) {
        
                    	this.checkDate(p);	

                        p.idSavetovanja = p.id,
                        p.apoteka = p.apoteka.naziv,
                        p.farmaceut = p.username,
                        p.pocetak = p.start.slice(11, 20),
                        p.datum = p.start.slice(0, 10),
                        p.kraj = p.end.slice(11, 20),
                        p.cena = p.cena + ' RSD',
                      	p._rowVariant = p.rowVariant
                    	
                        items.push(p)
                        
                        this.pregledi.push(p);
                    }
                })
       	        .catch((e) => {
		        	console.log(e);
		        });
            this.table_is_busy = false
            return items;
        },
        itemProvider: function (ctx) {
           return this.loadPregledi();
        },
        myRowClickHandler(record, index) {
      		this.selectedPregled = this.pregledi[index];
      		$("#modal").modal();
		},
    },
});
