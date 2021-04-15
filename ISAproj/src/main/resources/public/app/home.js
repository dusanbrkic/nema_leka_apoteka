Vue.component("Home", {
    data: function () {
        return {

        };
    },
    mounted() {

    },
    template: `
      <div>
      <b-navbar toggleable="lg" type="dark" variant="dark">
        <img src="../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#">Home</b-nav-item>
            <b-nav-item href="#/lekovi">Lekovi</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/registracija" right>Registruj se</b-nav-item>
            <b-nav-item href="#/login" right>Prijavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      </div>
      
    `
    ,
    methods: {

    },
});
