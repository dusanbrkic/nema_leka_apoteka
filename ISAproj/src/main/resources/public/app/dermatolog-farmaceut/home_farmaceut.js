Vue.component("HomeFarmaceut", {
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
      <link rel="stylesheet" href="css/dermatolog-farmaceut/home_farmaceut.css" type="text/css">
      <b-navbar toggleable="lg" type="dark" variant="dark">
        <img src="../../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-farmaceut">Home</b-nav-item>
            <b-nav-item href="#/home-farmaceut/pregledani-pacijenti">Pregledani pacijenti</b-nav-item>
            <b-nav-item href="#/zakazivanje">Zakazi savetovanje</b-nav-item>
            <b-nav-item href="#/odsustvo-forma">Godisnji odmor</b-nav-item>
            <b-nav-item href="#/pregled-forma">Zapocni savetovanje</b-nav-item>
            <b-nav-item href="#/home-farmaceut/calendar-view">Radni kalendar</b-nav-item>
            <b-nav-item href="#/home-farmaceut/izdaj-lek">Izdaj lek</b-nav-item>
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
