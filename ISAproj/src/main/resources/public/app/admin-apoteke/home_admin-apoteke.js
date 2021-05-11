Vue.component("HomeAdminApoteke", {
  data: function () {
    return {
      message: "",
      apoteka: "",
      cookie: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.getApotekaInfo();
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
        <b-row>
            <b-card
          title="Dodaj lek"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-text>
          Dodavanje novog leka u apoteku
        </b-card-text>

        <b-button href="#/dodaj-lek-admin" variant="primary">Otvori</b-button>
      </b-card>
      <b-card
          title="Pretrazi, obrisi i uredi lekove"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-text>
          Pretrazi, obrisi i uredi lekove
        </b-card-text>

        <b-button href="#/pretraga-lek-admin" variant="primary">Otvori</b-button>
            </b-card>
            
                      
          <b-card
          title="Naruci lekove"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-text>
          Narucivanje lekova za apoteku
        </b-card-text>

        <b-button href="#/dodaj-lek-admin" variant="primary">Otvori</b-button>
        </b-card>
        
        </b-row>
        <b-row>
          <b-card
          title="Izmeni podatke o apoteci"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2">
                  <b-card-text>Izmena podataka o apoteci</b-card-text>
                  <b-button href="#/admin-apoteke-apoteka" variant="primary">Otvori</b-button>
        </b-card> 
        <b-card
          title="Dodaj slobodan termin dermatologu"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2">
            <b-card-text>Dodavanje slobodnog termina dermatologu</b-card-text>
            <b-button href="#/admin-apoteke-slobodan-termin" variant="primary">Otvori</b-button>
        </b-card>
        <b-card
          title="Dodaj farmaceuta"
          img-src="https://picsum.photos/600/300/?image=25"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem;"
          class="mb-2">
            <b-card-text>Dodavanje novog farmaceuta u apoteku</b-card-text>
            <b-button href="#/admin-apoteke-dodaj-farmaceuta" variant="primary">Otvori</b-button>
          </b-card>
        </b-row>
      </b-container>
    </div>
    `,
  methods: {
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    redirectToApoteke: function () {
      app.$router.push("/apoteke");
    },
    getApotekaInfo: async function () {
      let cookie = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("apoteke/getByAdmin", cookie).then((response) => {
        console.log(response.data);
        this.apoteka = response.data;
        localStorage.setItem("apotekaID", response.data.id);
      });
    },
    redirectToHome: function () {
      localStorage.clear();
      app.$router.push("/");
    },
  },
});
