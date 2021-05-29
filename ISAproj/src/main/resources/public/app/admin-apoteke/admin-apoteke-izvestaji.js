Vue.component("AdminIzvestaji", {
  data() {
    return {
      cookie: "",
      apoteka: [],
      dermatolozi: [],
      farmaceuti: [],
      labels: ["JAN", 2, 5, 9, 5, 10, 3, 5, 2, 5, 1, 8],
    };
  },

  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadApoteka();
    this.loadDermatologe();
    this.loadFarmaceute();
    this.loadData();
  },
  template: `<div>
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
            <b-card style="margin: 40px auto; max-width: 2000px">
      			<h2>Izvestaji</h2>
      		<h3> Prosecna ocena Apoteke: {{apoteka.prosecnaOcena}}<h3>
      		    	<label>Prosecne ocene Farmaceuta:</label>
      		    	
		<b-col>
		<br>
		</b-list-group-item>
        </b-list-group>

        <b-list-group flush style="max-height: 200px;
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
    	<b-list-group-item v-for="farmaceut in farmaceuti" class="list-group-item px-0">
    		<p>{{farmaceut.ime}} {{farmaceut.prezime}}  - {{farmaceut.prosecnaOcena}}</p>
    	</b-list-group-item>	
    	</b-list-group>
			</b-col>

      			<b-col>
			        <label>Prosecne ocene Dermatologa:</label>
			        <b-list-group flush style="max-height: 200px;
				        overflow:scroll; 
				        margin-bottom: 10px;
			    		-webkit-overflow-scrolling: touch;">
			    	<b-list-group-item v-for="dermatolog in dermatolozi" class="list-group-item px-0">
			    		<p>{{dermatolog.ime}} {{dermatolog.prezime}} - {{dermatolog.prosecnaOcena}}</p>
			    	</b-list-group-item>	
			    	</b-list-group>
				</b-col>
        <bars
  :data="[1, 2, 5, 9, 5, 10, 3, 5, 2, 5, 1, 8]"
  :labelData=labels
  :gradient="['#3a86ff', '#d6d6d6']"
  :labelSize="0.4"
  :width="1500"
  :height="500"
  :barWidth="10.1">
</bars>
  </div>`,
  methods: {
    loadData: async function () {
      // this.data = await axios.get("https://covidtracking.com/v1/us/daily.json");
      // console.log(this.data);
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    loadApoteka() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("apoteke/getByAdmin/", info).then((response) => {
        this.apoteka = response.data;
        (this.center = [this.apoteka.latitude, this.apoteka.longitude]),
          (this.location = this.center);
        //this.fixAdresu();
      });
    },
    loadDermatologe() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
        .get("zdravstveniradnik/getAllDermatologApotekaList/", info)
        .then((response) => {
          this.dermatolozi = response.data;
          console.log(this.dermatolozi);
        });
    },
    loadFarmaceute() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
        .get("zdravstveniradnik/getAllFarmaceutsApoteka/", info)
        .then((response) => {
          this.farmaceuti = response.data;
          console.log(this.farmaceuti);
        });
    },
  },
});
