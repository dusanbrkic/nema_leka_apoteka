Vue.component("PacijentNarudzbenice", {
    data: function () {
        return {
            cookie: '',
            rezervacije: [],
            fields: [
                {
                    key: 'idRezervacije',
                    sortable: false
                },
                {
                    key: 'lekovi',
                    sortable: false
                },
                {
                    key: 'apoteka',
                    sortable: false
                },
                {
                    key: 'datumIstekaRezervacije',
                    sortable: false
                },
                {
                    key: 'status',
                    sortable: false
                },
            ],

            table_is_busy: false,
            
            selected: {
		   		id: "",
		   		lekovi: "",
		   		apoteka: "",
		   		datumIstekaRezervacije: "",
		   		status: "",
		   		},
            
            
           greska: false,
	       uspeh: false,
		   
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-container>

		<b-alert style="text-align: center;" v-model="this.greska" variant="danger"> Greska prilikom otkazivanja! </b-alert>
      	<b-alert style="text-align: center;" v-model="this.uspeh" variant="success"> Uspesno otkazivanje</b-alert>


          <b-row>
            <b-table
                ref="table"
                id="table-id"
                hover
                :items="itemProvider"
                :fields="fields"
                :busy.sync="table_is_busy"
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
		        <h5 class="modal-title">Informacije Rezervacije</h5>
		      </div>
		      <div class="modal-body">

				<b-row>
					<b-col>Apoteka: </b-col>
					<b-col>{{selected.apoteka}}</b-col>
				</b-row>
				<b-row>
					<b-col>Lekovi:</b-col>
					<b-col>{{selected.lekovi}}</b-col>
				</b-row>
				<b-row>
					<b-col>Datum isteka:</b-col>
					<b-col>{{selected.datumIstekaRezervacije}}</b-col>
				</b-row>
				<b-row>
					<b-col>Status:</b-col>
					<b-col>{{selected.status}}</b-col>
				</b-row>
		      </div>
		      <div class="modal-footer">
					<b-button type="button" class="btn btn-secondary" data-dismiss="modal">Zatvori</b-button>
		  		    <b-button v-if="selected.rowVariant == 'info' " @click="otkazi()" class="mr-1">
			          Otkazi rezervaciju
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
    	
    	nextDay.setDate(nextDay.getDate() + 1);
    	
   		if(r.preuzeto) {
   			r.rowVariant = 'success';
   			r.status = 'Preuzeto';
   		}
    	else {
	    	if(nextDay.toISOString().slice(0, 10) > r.datumRezervacije.slice(0, 10)) {
	    	
	    		console.log(today.toISOString().slice(0, 10) + " - " + r.datumRezervacije.slice(0, 10));
	    		if(today.toISOString().slice(0, 10) == r.datumRezervacije.slice(0, 10)){
	    			r.rowVariant = 'warning';
	    			r.status = 'Potrebno preuzeti';
	    		} else {
	    			r.rowVariant = 'danger';
	    			r.status = 'Nije preuzeto';
	    		}
	    	}
	    	else {
	    		r.rowVariant = 'info';
	    		r.status = 'Potrebno preuzeti';
	    	}
    	}

    },
    
    otkazi() {
            $('#modal').modal('hide');  
        	this.greska = false;
		    this.uspeh = false;
		    
		    
		     axios.get("rezervacije/otkazi-rezervaciju", 
			    {		
			    params: {
			       'id_rezervacije': this.selected.id
			       }
			    }).then((response) => {
			    		     this.$root.$emit("bv::refresh::table", "table-id");
		        })
		        .catch((e) => {
		        		if (e.request.status == 409) {
			        		this.$root.$emit("bv::refresh::table", "table-id");
						} else {
			        		this.greska = true;
			        	}
		        });
      },
    
        loadPregledi: async function () {
            this.table_is_busy = true
            let items = []
            this.rezervacije = [];
            await axios
                .get("rezervacije/moje_rezervacije", {
                    params:
                        {
                            'cookie': this.cookie,
                        }
                })
                .then(response => {
                    let rez = response.data;
                    for (const p of rez) {
        
                    	this.checkDate(p);	
                    	

                            p.idRezervacije = p.id,
                            p.lekovi = p.sifraLeka,
                            p.apoteka = p.apotekaId,
                            p.datumIstekaRezervacije = p.datumRezervacije.slice(0, 10),
                          	p._rowVariant = p.rowVariant

                        
                        items.push(p);
                        this.rezervacije.push(p);
                    }
                })
       	        .catch((e) => {
		        	console.log(e);
		        });
            this.table_is_busy = false
            return items
        },
        itemProvider: function (ctx) {
            return this.loadPregledi()
        },
        myRowClickHandler(record, index) {
        	if(this.rezervacije[index].rowVariant == 'info') {
	      		this.selected = this.rezervacije[index];
	      		$("#modal").modal();
      		}
		},
    },
});
