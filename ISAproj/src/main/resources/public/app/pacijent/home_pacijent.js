Vue.component("HomePacijent", {
  data: function () {
    return {
      message: '',
      apoteka: '',
      cookie: '',
    };
  },
  mounted() {
  	this.cookie = localStorage.getItem("cookie")
  },
  template: `
    <div>

      <b-navbar toggleable="lg" type="dark" variant="dark">
        <img src="../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-pacijent/">Home</b-nav-item>
            <b-nav-item href="#/home-pacijent/apoteke">Apoteke</b-nav-item>
            <b-nav-item href="#/home-pacijent/lekovi">Lekovi</b-nav-item>
            <b-nav-item href="#/home-pacijent/pregledi">Pregledi</b-nav-item>
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
