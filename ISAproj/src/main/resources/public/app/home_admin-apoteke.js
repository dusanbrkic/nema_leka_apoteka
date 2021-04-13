Vue.component("HomeAdminApoteke", {
  data: function () {
    return {
      message: "",
      apoteka: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
   	this.getApotekaInfo();
  },
  template: `
    <div>


      
      <div class="container">    
        <div class="row">
          </div>
          
          <div class="col-sm-4"> 
            <div class="panel panel-default">
              <div class="panel-heading">Dodaj lek</div>
              <div  v-on:click="redirectToAADodaj"  class="panel-body"><img src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
            </div>
          </div>
                    <div class="col-sm-4"> 
            <div class="panel panel-default">
              <div class="panel-heading">Pretrazi lekove</div>
              <div  v-on:click="redirectToAAPretraga"  class="panel-body"><img src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
            </div>
          </div>
          <div class="col-sm-4"> 
            <div class="panel panel-default">
              <div class="panel-heading">Uredjuj lek</div>
              <div  v-on:click="redirectToAALekovi"  class="panel-body"><img src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
            </div>          
          </div>
          

          

          <div class="col-sm-4">
            <div class="panel panel-default">
              <div v-on:click="redirectToLicniPodaci" class="panel-heading">Izmeni licne podatke</div>
                <div v-on:click="redirectToLicniPodaci" class="panel-body"><img src="https://i.imgur.com/erULHsS.png" fluid class="img-responsive" style="width:100%" alt="Image"></div>
          	</div>
          </div>
          		  <div class="col-sm-4">
            <div class="panel panel-default">
              <div v-on:click="redirectToApotekaIzmeni" class="panel-heading">Izmeni apoteku</div>
                <div v-on:click="redirectToApotekaIzmeni" class="panel-body"><img src="https://assets.siccode.com/i-s-b/sic-code-5912-drug-stores-proprietary-stores.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
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
    redirectToIzmenaPacijent: function () {
      app.$router.push("/izmena-podataka");
    },
	redirectToAALekovi: function(){
		app.$router.push("/admin-apoteke-lekovi");
	},
	redirectToAADodaj: function(){
		app.$router.push("/dodaj-lek-admin");
	},
	redirectToAAPretraga: function(){
		app.$router.push("/pretraga-lek-admin");
	},
	redirectToLicniPodaci: function(){
		app.$router.push("/izmena-podataka");
	},
	redirectToApotekaIzmeni: function(){
		app.$router.push("/apoteka/" + this.apoteka.id);
	}
  },
});
