Vue.component("HomeDermatolog", {
    data: function () {
        return {
            cookie: '',
        }
    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
    },
    template: `
      <div>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/home_dermatolog.css" type="text/css">
      <b-navbar toggleable="lg" type="dark" variant="dark">
        <img src="../../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-dermatolog">Home</b-nav-item>
            <b-nav-item href="#/pregledani-pacijenti">Pregledani pacijenti</b-nav-item>
            <b-nav-item href="#/zakazivanje">Zakazi Pregled</b-nav-item>
            <b-nav-item href="#/godisnji-odmor">Godisnji odmor</b-nav-item>
            <b-nav-item href="#/pregled-forma">Zapocni pregled</b-nav-item>
            <b-nav-item href="#/home-dermatolog/calendar-view">Radni kalendar</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
      </div>
    `,
    methods: {
        logout: function () {
            localStorage.clear()
            app.$router.push("/");
        },
    },
});
