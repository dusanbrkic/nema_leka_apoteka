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
      pageSizes: [2, 6, 10, 20, 50],
      fields: [
        {
          key: "username",
          sortable: true,
        },
        {
          key: "prezime",
          sortable: true,
        },
        {
          key: "ime",
          sortable: true,
        },
        {
          key: "prosecnaOcena",
          sortable: true,
        },
        {
          key: "radnoVremePocetak",
          sortable: true,
          label: "Pocetak radnog vremena",
          formatter: (value, key, item) => {
            return moment(value).format("HH:mm");
          },
        },
        {
          key: "radnoVremeKraj",
          sortable: true,
          label: "Kraj radnogVremena",
          formatter: (value, key, item) => {
            return moment(value).format("HH:mm");
          },
        },
        {
          key: "obrisiDermatologa",
          sortable: false,
        },
        {
          key: "izmeniRadnoVreme",
          sortable: false,
        },
      ],
      page: 1,
      count: "",
      pageSize: 6,
      table_is_busy: false,
      sortDesc: false,
      sortBy: "username",
      pretragaIme: "",
      pretragaPrezime: "",
      ocena: 0,
      pocetakRadnog: "00:00",
      krajRadnog: "23:59",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
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
    <b-card style="margin: 40px auto; max-width: 2000px">
        <b-container>
          <b-row>
            <b-col>
              <b-form-input v-model="pretragaIme" placeholder="Pretrazite po imenu"></b-form-input>
            </b-col>
            <b-col>
              <b-form-input v-model="pretragaPrezime" placeholder="Pretrazite po prezimenu"></b-form-input>
            </b-col>
            <b-col>
              <b-button v-on:click="pretraga" style="float: right">Pretrazi</b-button>
              <b-button v-on:click="obrisiPretragu" style="float: right" variant="danger">Resetuj</b-button>
            </b-col>
            </b-row>
            <br>
            <div>
              <label for="range-2">Prosecna ocena:
              <b-form-input id="range-2" v-model="ocena" @change="handleOcenaChange($event)" type="range" size = "sm" min="0" max="5" step="0.5"></b-form-input>
              
              <div class="mt-2">Farmaceuti sa prosecnom ocenom vecom od: {{ ocena }}</div>
              </label>
              </div>

              
              </b-row>
              
              <b-row>
                <b-col><b-form-input type= "time" v-model="pocetakRadnog" @change="handlePocetakChange($event)":max=this.krajRadnog ></b-form-input></b-col>
                <b-col><b-form-input type= "time" v-model="krajRadnog" @change="handleKrajChange($event)" :min=pocetakRadnog></b-form-input></b-col>
      </b-row>
            <br>
		          <b-row>
                    <b-table
                        striped
                        id="dermatolozi-tabela"
                        hover
                        :items="itemProvider"
                        :fields="fields"
                        :per-page="pageSize"
                        :current-page="page"
                        :busy.sync="table_is_busy"
                        :sort-by.sync="sortBy"
                        sort-icon-left
                        responsive="sm"
                        :sort-desc.sync="sortDesc">
                    <template #cell(obrisiDermatologa)="row">
                    <b-button @click="obrisiDermatologa(row.item)"  variant="primary">Obrisi</b-button>
                    
                    </template>
                    <template  #cell(izmeniRadnoVreme)="row">
                    <b-button v-on:click="izmeniDermatologa(row.item)" variant="primary">Izmeni</b-button></template> </b-table>
         </b-row>
         <b-row>
          <b-col>
            <b-pagination
                  v-model="page"
                  :total-rows="count"
                  :per-page="pageSize"
                  @change="handlePageChange"
              ></b-pagination>
              </b-col>
           <b-col>
              <select v-model="pageSize" @change="handlePageSizeChange($event)" style="float: right">
                <option v-for="size in pageSizes" :key="size" :value="size">
                  {{ size }}
                </option>
              </select>
          </b-col>
         </b-row>
         </b-containter>
      </b-card>

      <b-container id="page_content">
		<!-- PRIKAZ DERMATOLOGA -->
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
    info(item, index, button) {
      console.log(item);
    },
    fixTime(date) {
      return moment(date).format("HH:mm");
    },
    onIzmeniDermatologa() {
      this.preklopRadnogVremena = false;
      let info = {
        params: {
          start:
            this.dermatologDTO.radnoVremePocetak + ":00.000Z",
          end: this.dermatologDTO.radnoVremeKraj + ":00.000Z",
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
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    izmeniDermatologa: async function (dermatolog) {
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
    obrisiDermatologa: async function (dermatolog) {
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
      if (confirm("Da li ste sigurni?") == true) {
        await axios
          .delete("/zdravstveniradnik/deleteDermatolog", info)
          .then((response) =>
            this.$bvToast.toast(`hello`, {
              title: "toast",
              autoHideDelay: 5000,
              toaster: "b-toaster-top-center",
            })
          )
          .catch((error) => {
            if (error.request.status == 400) {
              this.imaZakazeno = true;
            }
          });
        this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
      }
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    fixDate(date) {
      return moment(date).format("HH:mm");
    },
    itemProvider: function (ctx) {
      console.log(ctx);
      return this.retrieveDermatologe(
        ctx.page,
        ctx.pageSize,
        ctx.sortBy,
        ctx.sortDesc,
        this.pretragaIme,
        this.pretragaPrezime
      );
    },
    retrieveDermatologe: async function (
      page,
      pageSize,
      sortBy,
      sortDesc,
      pretragaIme,
      pretragaPrezime
    ) {
      this.table_is_busy = true;
      let info = {
        params: {
          cookie: this.cookie,
          page: this.page - 1,
          size: this.pageSize,
          sortBy: sortBy,
          sortDesc: sortDesc,
          pretraziIme: pretragaIme,
          pretraziPrezime: pretragaPrezime,
          ocena: this.ocena,
          pocetak: this.pocetakRadnog + ":00.000Z",
          kraj:  this.krajRadnog + ":00.000Z",
        },
      };
      console.log("123");
      console.log(info);
      await axios
        .get("zdravstveniradnik/getAllDermatologApotekaPage/", info)
        .then((response) => {
          this.dermatolozi = response.data["content"];
          this.count = response.data["totalElements"];
          console.log(this.dermatolozi);
        })
        .catch((e) => {
          console.log(e);
        });
      this.table_is_busy = false;
      return this.dermatolozi;
    },
    pretraga: function () {
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handlePageChange(value) {
      this.page = value;
    },
    handlePageSizeChange(event) {
      this.page = 1;
      this.items_per_page = event.target.value;
    },

    handlePageSizeChange(event) {
      this.page = 1;
      this.pageSize = event.target.value;
    },
    obrisiPretragu: function () {
      this.pretragaIme = "";
      this.pretragaPrezime = "";
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handleOcenaChange(event) {
      this.page = 1;
      this.ocena = event;
      console.log(this.ocena);
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handlePocetakChange(event) {
      this.page = 1;
      this.pocetakRadnog = event;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
    handleKrajChange(event) {
      this.page = 1;
      this.krajRadnog = event;
      this.$root.$emit("bv::refresh::table", "dermatolozi-tabela");
    },
  },
});
