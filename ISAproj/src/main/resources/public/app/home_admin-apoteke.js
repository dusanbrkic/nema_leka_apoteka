Vue.component("HomeAdminApoteke", {
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
                <li><a><div v-on:click="redirectToHelloWorld">    Lekovi        </div></a></li>
                <li><a><div>                                      Kontakt        </div></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
            
              <li><a><div><span class="glyphicon glyphicon-shopping-cart"></span> Korpa</div></a></li>
              
              <li><a><div v-on:click="redirectToIzmenaPacijent"><span class="glyphicon glyphicon-user"></span> Moj nalog</div></a></li>

			  <li><a><div v-on:click="redirectToHome"><span class="glyphicon glyphicon-user"></span> Odjavi se</div></a></li>
            </ul>

          </div>
        </div>
      </nav>
      
      <div class="container">    
        <div class="row">
          <div class="col-sm-4">
            <div class="panel panel-default">
              <div class="panel-heading">Apoteke</div>
                <div v-on:click="redirectToApoteke" class="panel-body"><img src="https://assets.siccode.com/i-s-b/sic-code-5912-drug-stores-proprietary-stores.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
          </div>

          </div>
          <div class="col-sm-4"> 
            <div class="panel panel-default">
              <div class="panel-heading">Lekovi</div>
              <div class="panel-body"><img src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
            </div>
          </div>
          <div class="col-sm-4"> 
            <div class="panel panel-default">
              <div class="panel-heading">Pregledi</div>
              <div class="panel-body"><img src="https://mediniz-images-2018-100.s3.ap-south-1.amazonaws.com/post-images/dermatologist_1556130488.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
            </div>
          </div>
        </div>
      </div><br><br>
      
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
    redirectToApoteka: function (id) {
      app.$router.push("/apoteka/" + id);
    },
    redirectToHome: function () {
      app.$router.push("/");
    },
    redirectToIzmenaPacijent : function () {
      app.$router.push("/izmena-podataka");
    }
  },
});
