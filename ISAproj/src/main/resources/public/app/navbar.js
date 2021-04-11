Vue.component("Navbar", {
  data: function () {
    return {
      message: '',
      apoteka: ''
    };
  },
  mounted() {
  	
  },
  template: `
    <div>

      <div class="jumbotron">
        <div class="container text-center">
          <h1>Apoteka</h1>      
          <p>Lekovi, preparati i konsultacije sa doktorima</p>
        </div>
      </div>



      <nav class="navbar navbar-expand-lg navbar-inverse">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">Logo</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="nav navbar-nav">
                <li class="active"><a><div>  Početna strana</div></a></li>
                <li><a><div v-on:click="redirectToApoteke">       Apoteke       </div></a></li>
                <li><a><div v-on:click="redirectToLekovi">    Lekovi        </div></a></li>
                <li><a><div>                                      Kontakt        </div></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
              <li><a><div v-on:click="redirectToLogin"><span class="glyphicon glyphicon-user"></span> Uloguj se</div></a></li>
              <li><a><div><span class="glyphicon glyphicon-user"></span> Registracija</div></a></li>
              <!--
              <li><a><div><span class="glyphicon glyphicon-shopping-cart"></span> Korpa</div></a></li>
              -->
            </ul>

          </div>
        </div>
      </nav>
      
      
      
        <br/>
        <router-view/>
      

      
      <!--
        <footer class="container-fluid text-center">
          <p>Apoteka Copyright</p>  
        </footer>
      -->
    </div>
    `,
  methods: {
    redirectToHelloWorld: function () {
      app.$router.push("/helloWorld");
    },
    redirectToApoteke: function () {
      app.$router.push("/apoteke");
    },
    redirectToLekovi: function () {
      app.$router.push("/lekovi");
    },
    redirectToApoteka: function (id) {
      app.$router.push("/apoteka/" + id);
    },
    redirectToLogin: function () {
      app.$router.push("/login");
    }
  },
});
