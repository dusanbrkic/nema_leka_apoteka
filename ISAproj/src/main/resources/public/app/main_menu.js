Vue.component("MainMenu", {
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
              <div v-on:click="redirectToLekovi" class="panel-body"><img src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
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
