Vue.component("AdminApoteka", {
  data() {
    let map = new ol.Map({
      target: "mapOL",
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM(),
        }),
      ],

      view: new ol.View({
        center: [0, 0],
        zoom: 2,
      }),
    });
    return {
      apoteka: {
        id: "",
        naziv: "",
        adresa: "",
        prosecnaOcena: "",
        opis: "",
        dermatolozi: "",
        farmaceuti: "",
        lekovi: "",
      },
      lekovi: [],
      dermatolozi: [],
      farmaceuti: [],
      cookie: "",
      zoom: 15,
      center: "",
      location: [20.391395129936356, 45.38985699678626],
      rotation: 0,
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadApoteka();
    this.loadLekoveApoteka();
    this.loadDermatologe();
    this.loadFarmaceute();
  },
  template: `
		<div>
	<link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
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
        <b-nav-item href="#/admin-apoteke-apoteka">Izmeni podatke o apoteci</b-nav-item>
        <b-nav-item href="#/admin-apoteke-narudzbina">Naruci lekove</b-nav-item>
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
        <b-form @submit.prevent="onSubmit">
	        <h2>Izmena podataka apoteke</h2>
	        <form @submit.prevent="saveApoteka">
	
	          <div class="form-group">
	            <label for="username">Naziv:</label>
	            <input type="text" class="form-control" id="naziv" v-model="apoteka.naziv">
	          </div>
  
	          <div class="form-group">
  				<b-row>
				 <b-col>
				 	<label for="ime">Adresa:</label>
	            	<input type="text" class="form-control" id="adresa" v-model="apoteka.adresa">
				 </b-col>
				 <b-col>
				     <vl-map data-projection="EPSG:4326" style="height: 500px; width: 500px">
					<vl-view :zoom.sync="zoom" :center.sync="center" :rotation.sync="rotation"></vl-view>

					<vl-layer-tile>
					<vl-source-osm></vl-source-osm>
					</vl-layer-tile>

					<vl-feature>
						<vl-geom-point :coordinates="location">
						</vl-geom-point>
						<vl-style-box>
							<vl-style-icon
									src="../res/pics/marker.png"
									:scale="0.05"
									:anchor="[0.5, 1]"
							></vl-style-icon>
						</vl-style-box>
					</vl-feature>
				</vl-map>
				 
				 </b-col>
				  </b-row>

	          </div>
	
	          <div class="form-group">
	            <label>Opis:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.opis">
	          </div>
			  <div class="form-group">
  				<label>Cena pregleda:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.cenaPregleda">
			  </div>
			  <div class="form-group">
  				<label>Cena savetovanja:</label>
	            <input type="text" class="form-control" id="opis" v-model="apoteka.cenaSavetovanja">
			  </div>
	          <div class="form-group">
			  
			  <b-row>
			  <b-col>
	            <label>Lekovi:</label>
	        <b-list-group flush style="max-height: 200px; 
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
            <b-list-group-item v-for="lek in lekovi" class="list-group-item px-0">
            
            <b-row align-v="centar" >
                <b-col md="auto">
                    <b>{{lek.naziv}}</b>
                      <p class="text-sm mb-0"> Trenutno na stanju: {{lek.kolicina}}  </p>
                      <p class="text-sm mb-0"> Cena: {{lek.cena}}  </p>
                        </b-col>
                </b-col>
        	</b-row>
        	<b-row align-v="right" >
        	<div class="col-md-12 bg-light text-right">
        	<b-button size="sm" href="#/pretraga-lek-admin" variant="primary"> Uredi lekove </b-button>
        	</div>
        	</b-row>
        </b-list-group-item>
        </b-list-group>
		</b-col>
		<b-col>
        <label>Dermatolozi:</label>

        <b-list-group flush style="max-height: 200px;
	        overflow:scroll; 
	        margin-bottom: 10px;
    		-webkit-overflow-scrolling: touch;">
    	<b-list-group-item v-for="dermatolog in dermatolozi" class="list-group-item px-0">
    		<b>{{dermatolog.ime}} {{dermatolog.prezime}}</b>
    	</b-list-group-item>	
    	</b-list-group>
		</b-col>
		        	<b-row align-v="right" >
        	<div class="col-md-12 bg-light text-right">
        	<b-button size="sm" variant="primary" href="#/admin-apoteke-dermatolozi"> Uredi dermatologe </b-button>
        	</div>
        	</b-row>
    	<label>Farmaceuti:</label>
		<b-col>
		</b-list-group-item>
        </b-list-group>

        <b-list-group flush style="max-height: 200px;
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
    	<b-list-group-item v-for="farmaceut in farmaceuti" class="list-group-item px-0">
    		<b>{{farmaceut.ime}} {{farmaceut.prezime}}</b>
    	</b-list-group-item>	
    	</b-list-group>
			</b-col>
		</b-row>
		        	<b-row align-v="right" >
        	<div class="col-md-12 bg-light text-right">
        	<b-button size="sm" variant="primary" href="#/admin-apoteke-farmaceuti"> Uredi farmaceute </b-button>
        	</div>
        	</b-row>
	    </div>
			<b-button type="button" size="sm" v-on:click="redirectToHome()" variant="primary">Povratak na glavnu stranu</b-button>
			<b-button type="button" size="sm" v-on:click="saveApoteka()" variant="primary">Sacuvaj podatke</b-button>
        <div>

      </div>

	        </form>
		</b-card>
		</b-containter>
		      </div>
    `,
  methods: {
    loadLekoveApoteka: function () {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
        .get("lekovi/basic/", info)
        .then((response) => (this.lekovi = response.data));
    },
    saveApoteka: function () {},
    loadApoteka() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("apoteke/getByAdmin/", info).then((response) => {
        this.apoteka = response.data;
        this.fixAdresu();
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
    redirectToHome: function () {
      console.log(this.lekovi);
      app.$router.push("/home-admin_apoteke");
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    fixAdresu() {
      const words = this.apoteka.adresa.split("|");
      this.apoteka.adresa = words[0];

      this.center = words[1].split(",");
      this.location = this.center;
      console.log(this.center);
    },
  },
});
