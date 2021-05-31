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
	       
	       sortBy: 'cena',
           sortDesc: false,
		 
		 
		 	items: [],  
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
        this.loadPregledi();
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
                :items="items"
                :fields="fields"
                :busy.sync="table_is_busy"
                :sort-by.sync="sortBy"
				:sort-desc.sync="sortDesc"
				sort-icon-left
            >
            
	            <template #cell(status)="row">

 					<div size="sm" v-if="pregledi[row.index].rowVariant == 'info' || pregledi[row.index].rowVariant == 'warning'" class="mr-1">
			          Potrebno obaviti
			        </div>
	            	<b-button size="sm" v-if="pregledi[row.index].rowVariant == 'info' " @click="otkazi(row.index)" class="mr-1">
			          Otkazi
			        </b-button>
			        <div size="sm" v-if="pregledi[row.index].rowVariant == 'success' " class="mr-1">
			          Obavljen
			        </div>
			        <div size="sm" v-if="pregledi[row.index].rowVariant == 'danger' " class="mr-1">
			          Nije obavljen
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
    	
    	nextDay.setDate(today.getDate() + 1);
    	
   		if(r.pregledObavljen) {
   			r.rowVariant = 'success';
   		}
    	else {
    		console.log(nextDay.toISOString().slice(0, 10) + " - " + r.start.slice(0, 10));
		   	if(nextDay.toISOString().slice(0, 10) > r.start.slice(0, 10)) {
		   		if(today.toISOString().slice(0, 10) == r.start.slice(0, 10)){
    				r.rowVariant = 'warning';
    			}
    			else {	
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
		    
		    
		     axios.get("pregledi/otkazi-pregled", 
			    {		
			    params: {
			       'id_pregleda': this.pregledi[index].id
			       }
			    }).then((response) => {
   		   			this.loadPregledi();
   		   			this.$root.$emit("bv::refresh::table", "table-id");
		        })
		        .catch((e) => {
		        	this.greska = true;
		        });
      },
    
        loadPregledi: async function () {
            this.table_is_busy = true
            this.items = []
            await axios
                .get("pregledi/savetovanja_farmaceuta", {
                    params:
                        {
                            'cookie': this.cookie,
                        }
                })
                .then(response => {
                    let rez = response.data;
                    for (const p of rez) {
        
                    	this.checkDate(p);	
                    	this.pregledi.push(p);
                    	console.log(p);
                    	
                        this.items.push({
                            idSavetovanja: p.id,
                            apoteka: p.apoteka.naziv,
                            farmaceut: p.username,
                            pocetak: p.start.slice(11, 20),
                            datum: p.start.slice(0, 10),
                            kraj: p.end.slice(11, 20),
                            cena: p.cena + ' din.',
                          	_rowVariant: p.rowVariant
                            
                        })
                    }
                })
       	        .catch((e) => {
		        	console.log(e);
		        });
            this.table_is_busy = false
        },
        itemProvider: function (ctx) {
           // return this.loadPregledi()
        },
    },
});
