Vue.component("AdminOdsustvo", {
  data() {
    return {
      odsustva: [],
      cookie: "",
      izabranoOdsustvo: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadOdsustva();
  },
  template: `
	
		<div>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/home_dermatolog.css" type="text/css">
      <b-navbar toggleable="lg" href="#/home-admin_apoteke" type="dark" variant="dark">
        <img src="../../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-admin_apoteke">Home</b-nav-item>
            <b-nav-item href="#/dodaj-lek-admin">Dodaj lek</b-nav-item>
            <b-nav-item href="#/pretraga-lek-admin">Pretrazi, obrisi i uredi lekove</b-nav-item>
            <b-nav-item >Izmeni podatke o apoteci</b-nav-item>
            <b-nav-item href="#/admin-apoteke-narudzbina">Naruci lekove</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dodaj-farmaceuta">Dodaj farmaceuta</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dodaj-dermatologa">Dodaj dermatologa</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dermatolozi">Dermatolozi</b-nav-item>
            <b-nav-item href="#/admin-apoteke-farmaceuti">Farmaceuti</b-nav-item>
            <b-nav-item href="#/admin-apoteke-narudzbenice">Narudzbine</b-nav-item>
            <b-nav-item href="#/admin-apoteke-promocija">Promocije</b-nav-item>
            <b-nav-item href="#/admin-apoteke-odsustvo">Odsustva</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
	  <b-container id="page_content">
      	<b-card style="margin: 30px auto;">
			<b-list-group flush style="max-height: 200px; 
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
            <b-list-group-item v-for="odsustvo in odsustva" :key="odsustvo" class="list-group-item px-0">
            
            <b-row align-v="centar" >
                <b-col md="auto">
                    <b>{{odsustvo.username}}</b>
                      <p class="text-sm mb-0"> Pocetak odsustva: {{fixDate(odsustvo.pocetak)}}  </p>
                      <p class="text-sm mb-0"> Kraj odsustva: {{fixDate(odsustvo.kraj)}}  </p>
                </b-col>
                <b-col md="auto" class="float-right">
  					<b-button type="button" size="sm" v-on:click="odobriOdsustvo(odsustvo)"variant="primary">Odobri</b-button>
					<b-button type="button" size="sm" v-on:click="odbijOdsustvo(odsustvo)" variant="primary">Odbij</b-button>
                </b-col>
        	</b-row>
        </b-list-group-item>
        </b-list-group>
		</b-card>
	</b-container>
	

	<!-- forma za odsustvo -->
	    <b-modal ref="my-modal" hide-footer>
  <b-card style="max-width: 500px; margin: 30px auto;">
    <b-form @submit.prevent="onOdbijOdsustvo">
    <h3>Odbijanja Odsustva</h3>
   <b-form-group id="input-group-3" label="Razlog odbijanja: ">
        <b-form-input required v-model="izabranoOdsustvo.razlog"></b-form-input>
		<br>
      <b-button type="submit" variant="primary">Odbij</b-button>
       </b-form>
    </b-card>
  </b-modal>	
		</div>
	
	`,
  methods: {
    odobriOdsustvo(odsustvo) {
      console.log(odsustvo);
      odsustvo.status = "odobreno";
      odsustvo.cookie = this.cookie;
      axios
        .put("zdravstveniradnik/updateOdsustvo/", odsustvo)
        .then((response) => {
          console.log(response.data);
        });
             this.odsustva = []
      this.loadOdsustva();
    },
    odbijOdsustvo(odsustvo) {
      this.izabranoOdsustvo = odsustvo;
      this.$refs["my-modal"].show();
    },
    onOdbijOdsustvo() {
      this.izabranoOdsustvo.status = "odbijeno";
      this.izabranoOdsustvo.cookie = this.cookie;
      axios
        .put("zdravstveniradnik/updateOdsustvo/", this.izabranoOdsustvo)
        .then((response) => {
          console.log(response.data);
        });
      this.odsustva = []
      this.loadOdsustva();
      this.$refs["my-modal"].hide();
    },
    fixDate(date) {
      return moment(date).format("DD/MM/YYYY");
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    loadOdsustva() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("zdravstveniradnik/allOdsustvo/", info).then((response) => {
        console.log(response.data);
        this.odsustva = response.data;
      });
    },
  },
});
