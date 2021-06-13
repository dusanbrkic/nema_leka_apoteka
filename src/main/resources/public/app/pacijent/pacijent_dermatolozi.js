Vue.component("PacijentDermatolozi", {
  data: function () {
    return {
      message: "",
      apoteka: "",
      cookie: "",
      naziviApoteka: "",
      apoteka: "",
      page: 1,
      count: "",
      pageSize: 6,
      table_is_busy: false,
      sortDesc: false,
      sortBy: "username",
      pretragaIme: "",
      pretragaPrezime: "",
      ocena: 0,
      pocetakRadnog: "00:00",
      krajRadnog: "23:59",
      naziviApoteka: "",
      apoteka: "",
      dermatolozi: [],
      pageSizes: [2, 6, 10, 20, 50],
      fields: [
        {
          key: "username",
          sortable: true,
        },
        {
          key: "prezime",
          sortable: true,
        },
        {
          key: "ime",
          sortable: true,
        },
        {
          key: "prosecnaOcena",
          sortable: true,
        },
        {
          key: "radnoVremePocetak",
          sortable: true,
          label: "Pocetak radnog vremena",
          formatter: (value, key, item) => {
            return moment(value).format("HH:mm");
          },
        },
        {
          key: "radnoVremeKraj",
          sortable: true,
          label: "Kraj radnogVremena",
          formatter: (value, key, item) => {
            return moment(value).format("HH:mm");
          },
        },
      ],
      izabranRadnik: "",
      prethodna_ocena: -1,
      ocenaRadnika: 0,
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekeNazivi();
  },
  template: `<div>
       <b-navbar toggleable="lg" type="dark" variant="dark">
        <img src="../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-pacijent/">Home</b-nav-item>
            <b-nav-item href="#/home-pacijent/apoteke">Apoteke</b-nav-item>
            <b-nav-item href="#/home-pacijent/lekovi">Lekovi</b-nav-item>
            <b-nav-item href="#/home-pacijent/pregledi">Pregledi</b-nav-item>
            <b-nav-item href="#/pacijent_dermatolozi">Dermatolozi</b-nav-item>
            <b-nav-item href="#/pacijent_farmaceuti">Farmaceuti</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
			<b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>

      <router-view/>
          <b-card style="margin: 40px auto; max-width: 2000px">
        <b-container>
          <b-row>
            <b-col>
              <b-form-input v-model="pretragaIme" placeholder="Pretrazite po imenu"></b-form-input>
            </b-col>
            <b-col>
              <b-form-input v-model="pretragaPrezime" placeholder="Pretrazite po prezimenu"></b-form-input>
            </b-col>
            <b-col>
              <b-button v-on:click="pretraga" style="float: right">Pretrazi</b-button>
              <b-button v-on:click="obrisiPretragu" style="float: right" variant="danger">Resetuj</b-button>
            </b-col>
            </b-row>
                        <br>
            <b-row>


                  <b-col sm="2">
        <label for="range-2">Prosecna ocena:
        <b-form-input id="range-2" v-model="ocena" @change="handleOcenaChange($event)" type="range" size = "sm" min="0" max="5" step="0.5"></b-form-input>
        
        <div class="mt-2">Farmaceuti sa prosecnom ocenom vecom od: {{ ocena }}</div>
        </label>
        </b-col>
        <b-col sm="2">
                       <select v-model="apoteka" @change="handleApotekaChange($event)" style="float: right">
                <option v-for="apoteka1 in naziviApoteka" :key="apoteka1" :value="apoteka1">
                  {{ apoteka1 }}
                </option>
              </select>
        </b-col>
        
        </b-row>
        <b-row>
                <b-col><b-form-input type= "time" v-model="pocetakRadnog" @change="handlePocetakChange($event)":max=this.krajRadnog ></b-form-input></b-col>
                <b-col><b-form-input type= "time" v-model="krajRadnog" @change="handleKrajChange($event)" :min=pocetakRadnog></b-form-input></b-col>
      </b-row>
            <br>
		          <b-row>
                    <b-table
                        striped
                        id="dermatolozi-tabela"
                        hover
                        :items="itemProvider"
                        :fields="fields"
                        :per-page="pageSize"
                        :current-page="page"
                        :busy.sync="table_is_busy"
                        :sort-by.sync="sortBy"
                        sort-icon-left
                        responsive="sm"
                        :sort-desc.sync="sortDesc">
                    <template #cell(obrisiDermatologa)="row">
                    	<b-button @click="obrisiDermatologa(row.item)"  variant="primary">Obrisi</b-button>
			    	</template>
			    	
			    	<template #cell(prosecnaOcena)="row">
			    		{{row.item.prosecnaOcena}}/5
                    	<b-button v-if="row.item.pravoOcene" @click="pokaziOceniModal(row.item)"  variant="light">Oceni</b-button>
                    	<b-button v-if="!row.item.pravoOcene" variant="light" disabled>Oceni</b-button>
			    	</template>
                    
                    <template  #cell(izmeniRadnoVreme)="row">
                    <b-button v-on:click="izmeniDermatologa(row.item)" variant="primary">Izmeni</b-button></template> </b-table>
         </b-row>
         <b-row>
          <b-col>
            <b-pagination
                  v-model="page"
                  :total-rows="count"
                  :per-page="pageSize"
                  @change="handlePageChange"
              ></b-pagination>
              </b-col>
           <b-col>
              <select v-model="pageSize" @change="handlePageSizeChange($event)" style="float: right">
                <option v-for="size in pageSizes" :key="size" :value="size">
                  {{ size }}
                </option>
              </select>
          </b-col>
         </b-row>
                 </b-containter>
      </b-card>
      
      
      
                <!-- Modal Ocenjivanja Radnika-->
     
     <b-modal ref="oceni-modal" hide-footer :title="this.izabranRadnik.ime + this.izabranRadnik.prezime">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="pokaziOceniModal">

       <div class="d-flex justify-content-center">
    <div class="text-center">
        <div class="ratings" style="max-height: 190px"> 
        	<div style="margin: -55px auto;">
	        	<span class="product-rating">{{ this.izabranRadnik.prosecnaOcena }}</span><span>/5</span>
		            <div class="stars">

			            <div v-if="this.izabranRadnik.star == 5">
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i>
			            	 <five class="fa fa-star"></i> 
			            	 <five class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="this.izabranRadnik.star == 4">
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i>
			            	 <four class="fa fa-star"></i> 
			           	</div>
			           	
			           	<div v-if="this.izabranRadnik.star == 3">
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			            	 <three class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="this.izabranRadnik.star == 2">
			            	 <two class="fa fa-star"></i>
			            	 <two class="fa fa-star"></i>
			           	</div>
			           	
			           	<div v-if="this.izabranRadnik.star == 1">
			            	 <one class="fa fa-star"></i>
			           	</div>
		           	
		           	</div>
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
      `,
  methods: {
    handleApotekaChange(event) {
      this.page = 1;
      this.apoteka = event.target.value;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    fixTime(date) {
      return moment(date).format("HH:mm");
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    fixDate(date) {
      return moment(date).format("HH:mm");
    },
    itemProvider: function (ctx) {
      console.log(ctx);
      return this.retrieveDermatologe(
        ctx.page,
        ctx.pageSize,
        ctx.sortBy,
        ctx.sortDesc,
        this.pretragaIme,
        this.pretragaPrezime
      );
    },
    retrieveDermatologe: async function (
      page,
      pageSize,
      sortBy,
      sortDesc,
      pretragaIme,
      pretragaPrezime
    ) {
      this.table_is_busy = true;
      let info = {
        params: {
          cookie: this.cookie,
          page: this.page - 1,
          size: this.pageSize,
          sortBy: sortBy,
          sortDesc: sortDesc,
          pretraziIme: pretragaIme,
          pretraziPrezime: pretragaPrezime,
          ocena: this.ocena,
          pocetak: this.pocetakRadnog + ":00.000Z",
          kraj: this.krajRadnog + ":00.000Z",
          apoteka: this.apoteka,
        },
      };
      console.log("123");
      console.log(info);
      await axios
        .get("zdravstveniradnik/getAllDermatologPage/", info)
        .then((response) => {
          this.dermatolozi = response.data["content"];
          
          for(var i = 0; i < this.dermatolozi.length; i++){
          			this.dermatolozi[i].prosecnaOcena = Math.round(this.dermatolozi[i].prosecnaOcena * 10) / 10
                            
                            if(this.dermatolozi[i].prosecnaOcena > 4.5) {
                            	this.dermatolozi[i].star = 5;
                            }
                            else if(this.dermatolozi[i].prosecnaOcena > 3.5) {
                            	this.dermatolozi[i].star = 4;
                            }
                            else if(this.dermatolozi[i].prosecnaOcena > 2.5) {
                            	this.dermatolozi[i].star = 3;
                            }
                            else if(this.dermatolozi[i].prosecnaOcena > 1.5) {
                            	this.dermatolozi[i].star = 2;
                            }
                            else {
                            	this.dermatolozi[i].star = 1;
                            }	
          }
          
          this.count = response.data["totalElements"];
          console.log(this.dermatolozi);
        })
        .catch((e) => {
          console.log(e);
        });
      this.table_is_busy = false;
      return this.dermatolozi;
    },
    pretraga: function () {
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handlePageChange(value) {
      this.page = value;
    },
    handlePageSizeChange(event) {
      this.page = 1;
      this.items_per_page = event.target.value;
    },

    handlePageSizeChange(event) {
      this.page = 1;
      this.pageSize = event.target.value;
    },
    obrisiPretragu: function () {
      this.pretragaIme = "";
      this.pretragaPrezime = "";
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handleOcenaChange(event) {
      this.page = 1;
      this.ocena = event;
      console.log(this.ocena);
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handlePocetakChange(event) {
      this.page = 1;
      this.pocetakRadnog = event;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handleKrajChange(event) {
      this.page = 1;
      this.krajRadnog = event;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    apotekeNazivi: function () {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("/apoteke/allApoteke/", info).then((response) => {
        this.naziviApoteka = JSON.parse(JSON.stringify(response.data));
        console.log(this.naziviApoteka);
      });
    },
    
    
    pokaziOceniModal(lek) {
		  	this.izabranRadnik = JSON.parse(JSON.stringify(lek));
		  	this.prethodna_ocena = 0;
		    this.getOcena();
	        this.$refs['oceni-modal'].show()
		},
		
		oceni() {
       	 	axios.get("zdravstveniradnik/oceni", 
			 {
			 	params: {
			                'cookie': this.cookie,
			                'username': this.izabranRadnik.username,
			                'ocena': this.ocenaRadnika
			            }
			 })
			.then((response) => {
			this.$refs['oceni-modal'].hide();
			this.apotekeNazivi();
			this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");

			})
			.catch((e) => {
			  console.log(e);
			});
		},
		getOcena() {
		
			if(this.izabranRadnik.username != null) {
	       	 	axios.get("zdravstveniradnik/getOcena", 
				 {
				 	params: {
				                'cookie': this.cookie,
				                'username': this.izabranRadnik.username,
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
		setJedan() { this.ocenaRadnika = 1; this.oceni(); },
		setDva() { this.ocenaRadnika = 2; this.oceni();},
		setTri() { this.ocenaRadnika = 3; this.oceni();},
		setCetiri() { this.ocenaRadnika = 4; this.oceni();},
		setPet() { this.ocenaRadnika = 5; this.oceni(); },
  },
});
