Vue.component("DodajFarmaceuta", {
  data: function () {
    return {
      cookie: "",
      farmaceutDTO: {
        username: "",
        password: "",
        ime: "",
        prezime: "",
        datumRodjenja: "",
        emailAdresa: "",
        cookie: "",
        firstLogin: "",
        adresa: "",
        grad: "",
        drzava: "",
        brojTelefona: "",
        minDate: "",
        radnoVremePocetak: "",
        radnoVremeKraj: "",
        cena: "",
        cookie: "",
      },
      postojiMail: false,
      postojiKorisnicko: false,
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.farmaceutDTO.cookie = localStorage.getItem("cookie");
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
      	      <b-card style="max-width: 500px; margin: 30px auto;" title="Registracija">
      <b-alert style="text-align: center;" v-model="this.postojiMail" variant="danger">Vec postoji nalog sa ovom email adresom!</b-alert>
      <b-alert style="text-align: center;" v-model="this.postojiKorisnicko" variant="danger">Vec postoji nalog sa ovim korisnickim imenom!</b-alert>
        <b-form @submit.prevent="onSubmit">
          <b-form-group id="input-group-1" label="Email adresa:" label-for="input-1">
            <b-form-input
                id="input-1"
                v-model="farmaceutDTO.emailAdresa"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-2" label="Korisnicko ime:" label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="farmaceutDTO.username"
            ></b-form-input>
          </b-form-group>
		  <b-form-group id="input-group-10" label="Lozinka:" label-for="input-2">
            <b-form-input
                id="input-10"
                v-model="farmaceutDTO.password"
                type="password"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Ime:" label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="farmaceutDTO.ime"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-4" label="Prezime:" label-for="input-4">
            <b-form-input
                id="input-4"
                v-model="farmaceutDTO.prezime"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-5" label="Datum rodjenja:" label-for="input-5">
            <b-form-input
                id="input-5"
                type="date"
                v-model="farmaceutDTO.datumRodjenja"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-6" label="Adresa:" label-for="input-6">
            <b-form-input
                id="input-6"
                v-model="farmaceutDTO.adresa"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-7" label="Grad:" label-for="input-7">
            <b-form-input
                id="input-7"
                v-model="farmaceutDTO.grad"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-8" label="Drzava:" label-for="input-8">
            <b-form-input
                id="input-8"
                v-model="farmaceutDTO.drzava"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-9" label="Broj telefona:" label-for="input-9">
            <b-form-input
                id="input-9"
                v-model="farmaceutDTO.brojTelefona"
            ></b-form-input>
		
		<b-form-group id="input-group-10" label="Pocetak radnog vremena:" label-for="input-10">
            <b-form-input
                id="input-10"
                v-model="farmaceutDTO.radnoVremePocetak"
                type="time"
                :max = this.farmaceutDTO.radnoVremeKraj
            ></b-form-input>
		<b-form-group id="input-group-11" label="Kraj radnog vremena:" label-for="input-11">
            <b-form-input
                id="input-11"
                v-model="farmaceutDTO.radnoVremeKraj"
                type="time"
                :min = this.farmaceutDTO.radnoVremePocetak
            ></b-form-input>
          </b-form-group>
          <b-button type="submit" variant="primary">Registruj se</b-button>
        </b-form>
      </b-card>
      
      </b-container">
      </div>
      `,
  methods: {
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    onSubmit: function () {
      this.farmaceutDTO.radnoVremePocetak =
        "2008-01-01T" + this.farmaceutDTO.radnoVremePocetak + ":00.000Z";
      this.farmaceutDTO.radnoVremeKraj =
        "2008-01-01T" + this.farmaceutDTO.radnoVremeKraj + ":00.000Z";
      this.postojiMail = false;
      this.postojiKorisnicko = false;
      console.log(this.farmaceutDTO);
      axios
        .post("zdravstveniradnik/addFarmaceut", this.farmaceutDTO)
        .then((response) => console.log(response.data))
        .catch((error) => {
          if (error.request.status == 400) {
            this.postojiMail = true;
          } else if (error.request.status == 404) {
            this.postojiKorisnicko = true;
          }
        });
    },
  },
});
