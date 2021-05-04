Vue.component("UrediDermatologe", {
  data: function () {
    return {
      dermatolozi: [],
      cookie: "",
      apotekaID: "",
      page: 1,
      count: 0,
      pageSize: 6,
      apotekaID: "",
      imaZakazeno: false,
      radnoVremePocetak: "",
      radnoVremeKraj: "",
      preklopRadnogVremena: false,
      radnaVremena: [],
      dermatologDTO: {
        cookie: "",
        cena: "",
        radnoVremePocetak: "",
        radnoVremeKraj: "",
      },
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.retrieveDermatologe();
  },
  template: `
		<div>
		    <!-- nav bar -->
		    
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
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
      <b-alert style="text-align: center;" v-model="this.imaZakazeno" variant="danger">Ima zakazane preglede, ne moze se obrisati!!</b-alert>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
      <b-container id="page_content">
		<!-- PRIKAZ DERMATOLOGA -->
		<b-row>
		 <b-card-group deck v-for="dermatolog in dermatolozi">
            <b-card
            >
            
            <p class="card-text">
                Ime  i prezime: {{dermatolog.ime}} {{dermatolog.prezime}}
            </p>
           <p class="card-text">
               Korisnicko ime: {{dermatolog.username}}
            </p>
                       
                       <p class="card-text">
               Cena: {{dermatolog.cena}}
            </p>
                       <p class="card-text">
              Pocetak radnog vremena: {{fixDate(dermatolog.radnoVremePocetak)}}
 			</p>
 		   <p class="card-text">
              Kraj radnog vremena: {{fixDate(dermatolog.radnoVremeKraj)}}
 			</p>
 		 		   <p class="card-text">
              Prosecna ocena: {{dermatolog.prosecnaOcena}}
 			</p>
              <b-button  v-on:click="izmeniDermatologa(dermatolog)" variant="primary">Izmeni dermatologa</b-button>
              <b-button  v-on:click="obrisiDermatologa(dermatolog)" variant="primary">Obrisi dermatologa</b-button>
            </b-card>
           </b-card-group>
           </b-row>
           	<!-- forma za dodavanje dermatologa -->
	<b-modal ref="my-modal" hide-footer title="Izmeni dermatologa">
	<b-alert style="text-align: center;" v-model="this.preklopRadnogVremena" variant="danger">Preklop radnog vremena</b-alert>
      <b-card style="max-width: 500px; margin: 30px auto;" >
		<h3>{{this.dermatologDTO.ime}} {{this.dermatologDTO.prezime}}</h3>
		<div v-if="this.radnaVremena.length == 0">
		<p> Njegova radna vremena: </p>
		</div>
      	<b-list-group flush>
	    <b-list-group-item v-for="radnoVreme in radnaVremena"
        style="max-width: 400px;"
        class="list-group-item px-0">
        <b-row align-v="centar" >
            <b-col md="auto">
			{{fixDate(radnoVreme.radnoVremePocetak)}} - {{fixDate(radnoVreme.radnoVremeKraj)}}
			
    	</b-list-group-item>
		</b-list-group>
        <b-form @submit.prevent="onIzmeniDermatologa">
          <b-form-group id="input-group-3" label="Cena:" label-for="input-3">
            <b-form-input
            	required
            	type = "number"
				v-model="dermatologDTO.cena"
				:min = 0 
            />
          <b-form-group id="input-group-3" label="Pocetak radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="dermatologDTO.radnoVremePocetak"
				:max = this.dermatologDTO.radnoVremeKraj
            />
           <b-form-group id="input-group-3" label="Kraj radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="dermatologDTO.radnoVremeKraj"
				:min = this.dermatologDTO.radnoVremePocetak
            />
		<br>
          <b-button type="submit" variant="primary">Sacuvaj izmene</b-button>
          <b-button type="reset" variant="primary">Resetuj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>
		</b-container>
		</div>
		`,
  methods: {
    fixTime(date) {
      return moment(date).format("HH:mm");
    },
    onIzmeniDermatologa() {
      this.preklopRadnogVremena = false;
      let info = {
        params: {
          start:
            "2008-01-01T" + this.dermatologDTO.radnoVremePocetak + ":00.000Z",
          end: "2008-01-01T" + this.dermatologDTO.radnoVremeKraj + ":00.000Z",
          cena: this.dermatologDTO.cena,
          cookie: this.cookie,
          username: this.dermatologDTO.username,
        },
      };
      console.log(info);
      axios
        .get("/zdravstveniradnik/changeDermatolog", info)
        .then((response) => {
          this.$refs["my-modal"].hide();
        })
        .catch((error) => {
          if (error.request.status == 400) {
            this.preklopRadnogVremena = true;
          }
        });
    },
    izmeniDermatologa(dermatolog) {
      let info = {
        params: {
          username: dermatolog.username,
        },
      };
      axios
        .get("/zdravstveniradnik/getDermaWorkingHours", info)
        .then((response) => (this.radnaVremena = response.data));
      console.log(this.radnaVremena);
      this.dermatologDTO = dermatolog;
      this.dermatologDTO.radnoVremePocetak = this.fixTime(
        dermatolog.radnoVremePocetak
      );
      this.dermatologDTO.radnoVremeKraj = this.fixTime(
        dermatolog.radnoVremeKraj
      );
      this.radnoVremePocetak = this.dermatologDTO.radnoVremePocetak;
      this.radnoVremeKraj = this.dermatologDTO.radnoVremeKraj;
      this.$refs["my-modal"].show();
    },
    obrisiDermatologa(dermatolog) {
      this.imaZakazeno = false;
      var today = new Date();
      var date =
        today.getFullYear() +
        "-" +
        String(today.getMonth() + 1).padStart(2, "0") +
        "-" +
        String(today.getDate()).padStart(2, "0") +
        "T" +
        String(today.getHours()).padStart(2, "0") +
        ":" +
        String(today.getMinutes()).padStart(2, "0") +
        ":" +
        String(today.getSeconds()).padStart(2, "0") +
        ".000Z";
      let info = {
        params: {
          username: dermatolog.username,
          startDate: date,
          cookie: this.cookie,
        },
      };
      console.log(info);
      axios
        .delete("/zdravstveniradnik/deleteDermatolog", info)
        .then((response) => console.log(response.data))
        .catch((error) => {
          if (error.request.status == 400) {
            this.imaZakazeno = true;
          }
        });
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    fixDate(date) {
      //return date;
      return moment(date).format("HH:mm");
    },
    retrieveDermatologe() {
      const params = this.getRequestParams(
        this.searchTitle,
        this.page,
        this.pageSize,
        this.cookie
      );
      axios
        .get("zdravstveniradnik/getAllDermatologApoteka/", { params })
        .then((response) => {
          const { dermatolozi, totalItems } = response.data;
          this.dermatolozi = dermatolozi;
          this.count = totalItems;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getRequestParams(searchTitle, page, pageSize, cookie) {
      let params = {};
      if (searchTitle) {
        params["title"] = searchTitle;
      } else {
        params["title"] = "";
      }
      if (page) {
        params["page"] = page - 1;
      }

      if (pageSize) {
        params["size"] = pageSize;
      }
      params["cookie"] = cookie;
      return params;
    },
  },
});
