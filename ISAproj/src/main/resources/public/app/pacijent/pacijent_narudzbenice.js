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
                hover
                :items="itemProvider"
                :fields="fields"
                :busy.sync="table_is_busy"
            >
            
	            <template #cell(status)="row">
	            
			        <div size="sm" v-if="rezervacije[row.index].rowVariant == 'info' || rezervacije[row.index].rowVariant == 'warning'" class="mr-1">
			          Potrebno preuzeti
			        </div>
	            	<b-button size="sm" v-if="rezervacije[row.index].rowVariant == 'info' " @click="otkazi(row.index)" class="mr-1">
			          Otkazi
			        </b-button>
			       
			        <div size="sm" v-if="rezervacije[row.index].rowVariant == 'success' " class="mr-1">
			          Preuzeto
			        </div>

			    </template>
            </b-table>
          </b-row>




        </b-container>
      </b-card>
      </div>
    `,
    methods: {
    
    checkDate(r) {
    
    	
        var today = new Date();
    	
    	var nextDay = new Date();
    	
    	nextDay.setDate(nextDay.getDate() + 1);
    	
   		if(r.preuzeto) {
   			r.rowVariant = 'success';
   		}
    	else {
	    	if(nextDay.toISOString().slice(0, 10) > r.datumRezervacije.slice(0, 10)) {
	    	
	    		console.log(today.toISOString().slice(0, 10) + " - " + r.datumRezervacije.slice(0, 10));
	    		if(today.toISOString().slice(0, 10) == r.datumRezervacije.slice(0, 10)){
	    			r.rowVariant = 'warning';
	    		} else {
	    			r.rowVariant = 'danger';
	    		}
	    	}
	    	else {
	    		r.rowVariant = 'info';
	    	}
    	}

    },
    
    otkazi(index) {
    
        	this.greska = false;
		    this.uspeh = false;
		    
		    
		     axios.get("rezervacije/otkazi-rezervaciju", 
			    {		
			    params: {
			       'id_rezervacije': this.rezervacije[index].id
			       }
			    }).then((response) => {
	          		//this.uspeh = true;
	          		//this.rezervacije.splice(index, 1);
   		   			//this.$refs.table.refresh();
   		   			//localStorage.setItem("uspeh", true);
   		   			location.reload();
		        })
		        .catch((e) => {
		        	this.greska = true;
		        });
      },
    
        loadPregledi: async function () {
            this.table_is_busy = true
            let items = []
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
                    	this.rezervacije.push(p);
                    	console.log(p);
                    	
                        items.push({
                            idRezervacije: p.id,
                            lekovi: p.sifraLeka,
                            apoteka: p.apotekaId,
                            datumIstekaRezervacije: p.datumRezervacije.slice(0, 10),
                          	_rowVariant: p.rowVariant
                            
                        })
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
    },
});
