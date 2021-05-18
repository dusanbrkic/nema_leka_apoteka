Vue.component("PacijentPoseteDermatologu", {
    data: function () {
        return {
            cookie: '',
            pregledi: [],
            fields: [
                {
                    key: 'idPregleda',
                    sortable: false
                },
                {
                    key: 'apoteka',
                    sortable: false
                },
                {
                	key: 'dermatolog',
                	sortable: false
                },
                {
                    key: 'datum',
                    sortable: false
                },
                {
                    key: 'pocetak',
                    sortable: false
                },
                                {
                    key: 'kraj',
                    sortable: false
                },
                {
                    key: 'cena',
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


          <b-row>
            <b-table
                ref="table"
                hover
                :items="itemProvider"
                :fields="fields"
                :busy.sync="table_is_busy"
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
                .get("pregledi/posete_dermatologu", {
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
                    	
                        items.push({
                            idPregleda: p.id,
                            apoteka: p.apoteka.naziv,
                            dermatolog: p.username,
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
            return items
        },
        itemProvider: function (ctx) {
            return this.loadPregledi()
        },
    },
});
