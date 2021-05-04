Vue.component("UrediFarmaceute", {
  data: function () {
    return {
      imaZakazeno: false,
      farmaceuti: [],
      cookie: "",
      apotekaID: "",
      page: 1,
      count: 0,
      pageSize: 6,
      apotekaID: "",
      radnoVremePocetak: "",
      radnoVremeKraj: "",
      radnaVremena: [],
      farmaceutDTO: {
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
    this.retrieveFarmaceute();
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
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
     <b-alert style="text-align: center;" v-model="this.imaZakazeno" variant="danger">Ima zakazane preglede, ne moze se obrisati!!</b-alert>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
      <b-container id="page_content">
		<!-- PRIKAZ FARMACEUTA -->
		<b-row>
		 <b-card-group deck v-for="farmaceut in farmaceuti">
            <b-card
            >
            
            <p class="card-text">
                Ime  i prezime: {{farmaceut.ime}} {{farmaceut.prezime}}
            </p>
           <p class="card-text">
               Korisnicko ime: {{farmaceut.username}}
            </p>
                       
                       <p class="card-text">
               Cena: {{farmaceut.cena}}
            </p>
                       <p class="card-text">
              Pocetak radnog vremena: {{fixDate(farmaceut.radnoVremePocetak)}}
 			</p>
 		   <p class="card-text">
              Kraj radnog vremena: {{fixDate(farmaceut.radnoVremeKraj)}}
 			</p>
 		 		   <p class="card-text">
              Prosecna ocena: {{farmaceut.prosecnaOcena}}
 			</p>
              <b-button  v-on:click="izmeniFarmaceuta(farmaceut)" variant="primary">Izmeni farmaceuta</b-button>
              <b-button  v-on:click="obrisiFarmaceuta(farmaceut)" variant="primary">Obrisi farmaceuta</b-button>
            </b-card>
           </b-card-group>
           </b-row>
           	<!-- forma za menjanje farmaceuta -->
	<b-modal ref="my-modal" hide-footer title="Izmeni farmaceuta">
      <b-card style="max-width: 500px; margin: 30px auto;" >
		<h3>{{this.farmaceutDTO.ime}} {{this.farmaceutDTO.prezime}}</h3>
		<p> Njegovo radno vremene: </p>
        {{this.farmaceutDTO.radnoVremePocetak}} - {{this.farmaceutDTO.radnoVremeKraj}}
        <b-form @submit.prevent="onIzmeniFarmaceuta">
          <b-form-group id="input-group-3" label="Cena:" label-for="input-3">
            <b-form-input
            	required
            	type = "number"
				v-model="farmaceutDTO.cena"
				:min = 0 
            />
          <b-form-group id="input-group-3" label="Pocetak radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="farmaceutDTO.radnoVremePocetak"
				:max = this.farmaceutDTO.radnoVremeKraj
            />
           <b-form-group id="input-group-3" label="Kraj radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="farmaceutDTO.radnoVremeKraj"
				:min = this.farmaceutDTO.radnoVremePocetak
            />
		<br>
          <b-button type="submit" variant="primary">Sacuvaj izmene</b-button>
          <b-button type="reset" variant="primary">Resetuj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>
		</b-container>
		</b-container>
    </div>
    `,

  methods: {
    onIzmeniFarmaceuta() {
      let info = {
        params: {
          start:
            "2008-01-01T" + this.farmaceutDTO.radnoVremePocetak + ":00.000Z",
          end: "2008-01-01T" + this.farmaceutDTO.radnoVremeKraj + ":00.000Z",
          cena: this.farmaceutDTO.cena,
          cookie: this.cookie,
          username: this.farmaceutDTO.username,
        },
      };
      console.log(info);
      axios
        .get("/zdravstveniradnik/changeFarmaceut", info)
        .then((response) => {
          this.$refs["my-modal"].hide();
        });
	},
    fixTime(date) {
      return moment(date).format("HH:mm");
    },
    fixDate(date) {
      return moment(date).format("HH:mm");
    },
    retrieveFarmaceute() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
       .get("/zdravstveniradnik/getAllFarmaceutsApoteka", info)
      .then((response) => {(this.farmaceuti = response.data)
						console.log(response.data)});
      console.log(this.farmaceuti);
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    izmeniFarmaceuta(farmaceut) {
		this.farmaceutDTO = farmaceut;
		this.farmaceutDTO.radnoVremePocetak = this.fixTime(
        farmaceut.radnoVremePocetak
      );
      this.farmaceutDTO.radnoVremeKraj = this.fixTime(
        farmaceut.radnoVremeKraj
      );
      this.$refs["my-modal"].show();
    },
    obrisiFarmaceuta(farmaceut) {
      this.imaZakazeno = false;
      var today = new Date();
      var date =
        today.getFullYear() +
        "-" +
        String(today.getMonth() + 1).padStart(2, "0") +
        "-" +
        String(today.getDate()).padStart(2, "0") +
        "T" +
        String(today.getHours()).padStart(2, "0") +
        ":" +
        String(today.getMinutes()).padStart(2, "0") +
        ":" +
        String(today.getSeconds()).padStart(2, "0") +
        ".000Z";
      let info = {
        params: {
          username: farmaceut.username,
          startDate: date,
          cookie: this.cookie,
        },
      };
      console.log(info);
      axios
        .delete("/zdravstveniradnik/deleteFarmaceut", info)
        .then((response) => console.log(response.data))
        .catch((error) => {
          if (error.request.status == 400) {
            this.imaZakazeno = true;
          }
        });
    },
  },
});
