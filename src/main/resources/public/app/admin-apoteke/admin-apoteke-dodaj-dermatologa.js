Vue.component("DodajDermatologa", {
  data: function () {
    return {
      cookie: "",
      apotekaID: "",
      dermatolozi: [],
      radnaVremena: [],
      preklopRadnogVremena: false,
      dermatologDTO: {
        cookie: "",
        cena: "",
        radnoVremePocetak: "",
        radnoVremeKraj: "",
      },
    };
  },

  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.dermatologDTO.cookie = localStorage.getItem("cookie");
    this.loadDermatologe();
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
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>

	    <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
      <b-container id="page_content">
      
      
           <b-card style="max-width: 450px; max-margin: 5px auto;" title="Dermatolozi">
      	<b-list-group flush>
	    <b-list-group-item v-for="dermatolog in dermatolozi"
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
				<b>{{dermatolog.ime}} {{dermatolog.prezime}}</b>
				Prosecna ocena: {{dermatolog.prosecnaOcena}}
				<div>
				</div>
                  
		            </b-col>
		    </b-col>
		    <b-col md="auto" class="float-right">
		    	<div>
		    		<b-button type="button" size="sm" v-on:click="dodajDerma(dermatolog)" variant="primary">Zaposli dermatologa</b-button>
		    	</div>
            </b-col>
    </b-list-group-item>
	</b-list-group>
     </b-card>
		</b-container>
	
	
	<!-- forma za dodavanje dermatologa -->
	<b-modal ref="my-modal" hide-footer title="Dodavanje dermatologa">
	<b-alert style="text-align: center;" v-model="this.preklopRadnogVremena" variant="danger">Preklop radnog vremena</b-alert>
      <b-card style="max-width: 500px; margin: 30px auto;" >
		<h3>{{this.dermatologDTO.ime}} {{this.dermatologDTO.prezime}}</h3>
		<p> Njegova radna vremena: </p>
      	<b-list-group flush>
	    <b-list-group-item v-for="radnoVreme in radnaVremena"
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
			{{radnoVreme.radnoVremePocetak}} - {{radnoVreme.radnoVremeKraj}}
			
    	</b-list-group-item>
		</b-list-group>
        <b-form @submit.prevent="onDodajTermin">
          <b-form-group id="input-group-3" label="Pocetak radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="dermatologDTO.radnoVremePocetak"
				:max = this.dermatologDTO.radnoVremeKraj
            />
           <b-form-group id="input-group-3" label="Kraj radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="dermatologDTO.radnoVremeKraj"
				:min = this.dermatologDTO.radnoVremePocetak
            />
		<br>
          <b-button type="submit" variant="primary">Dodaj dermatologa</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
		 	 </div> 

		`,

  methods: {
    fixDate(date) {
      return moment(date).format("HH:mm");
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    onDodajTermin() {
      this.preklopRadnogVremena = false;
      console.log(this.dermatologDTO.radnoVremePocetak);
      let info = {
        params: {
          start: this.dermatologDTO.radnoVremePocetak + ":00.000Z",
          end: this.dermatologDTO.radnoVremeKraj + ":00.000Z",
          cena: this.dermatologDTO.cena,
          cookie: this.cookie,
          username: this.dermatologDTO.username,
        },
      };
      console.log(info);
      axios
        .get("/zdravstveniradnik/addDermatologApoteke", info)
        .then((response) => {
          this.$refs["my-modal"].hide();
        })
        .catch((error) => {
          if (error.request.status == 400) {
            this.preklopRadnogVremena = true;
          }
        });
      this.loadDermatologe();
    },
    dodajDerma(dermatolog) {
      let info = {
        params: {
          username: dermatolog.username,
        },
      };
      axios
        .get("/zdravstveniradnik/getDermaWorkingHours", info)
        .then((response) => (this.radnaVremena = response.data));
      this.dermatologDTO = dermatolog;

      console.log(this.radnaVremena);

      this.$refs["my-modal"].show();
    },
    loadDermatologe() {
      axios
        .get("/zdravstveniradnik/allDermaNotInApoteka", {
          params: {
            cookie: this.cookie,
          },
        })
        .then((response) => {
          this.dermatolozi = response.data;
        });
    },
  },
});
