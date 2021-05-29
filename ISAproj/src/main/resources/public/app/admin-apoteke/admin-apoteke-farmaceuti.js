Vue.component("UrediFarmaceute", {
  data: function () {
    return {
      imaZakazeno: false,
      farmaceuti: [],
      cookie: "",
      apotekaID: "",
      farmaceutDTO: {
        ime: "",
        prezime: "",
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
        },
        {
          key: "radnoVremeKraj",
          sortable: true,
          label: "Kraj radnogVremena",
          //formatter: (value, key, item) => {
          //return moment(value).format("HH:mm");
          // },
        },
        {
          key: "obrisiFarmaceuta",
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
    <!--<link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css"> -->
    <b-card style="margin: 40px auto; max-width: 2000px">
        <b-container>
          <b-row align="center">
            <b-col>
              <b-form-input v-model="pretragaIme" placeholder="Pretrazite po imenu" align="right"></b-form-input>
            </b-col>
            <b-col>
              <b-form-input v-model="pretragaPrezime" placeholder="Pretrazite po prezimenu" align="right"></b-form-input>
            </b-col>
            <b-col>
              <b-button v-on:click="pretraga" style="float: right">Pretrazi</b-button>
              <b-button v-on:click="obrisiPretragu" style="float: right" variant="danger">Resetuj</b-button>
            </b-col>
            </b-row>
            <b-row>
            <br>
              <div>
        <label for="range-2">Prosecna ocena:
        <b-form-input id="range-2" v-model="ocena" @change="handleOcenaChange($event)" type="range" size = "sm" min="0" max="5" step="0.5"></b-form-input>
        
        <div class="mt-2">Farmaceuti sa prosecnom ocenom vecom od: {{ ocena }}</div>
        </label>
        </div>

        
        </b-row>
        <b-row>
          <b-col><b-form-input type= "time"   sm="5" v-model="pocetakRadnog" @change="handlePocetakChange($event)":max=this.krajRadnog ></b-form-input></b-col>
          <b-col><b-form-input type= "time" sm="5" v-model="krajRadnog" @change="handleKrajChange($event)" :min=pocetakRadnog></b-form-input></b-col>
          <b-col sm="7" md="6" class="my-1">
            <b-pagination
              align = "right"
              v-model="page"
              :total-rows="count"
              :per-page="pageSize"
              align="fill"
              size="sm"
              class="my-0"
              @change="handlePageChange"
            ></b-pagination>
          </b-col>
                   <b-col sm="5" md="6" class="my-1">
        <b-form-select
                  id="per-page-select"
                  v-model="pageSize"
                  :options="pageSizes"
                  size="sm"
                  class="text-right"
                ></b-form-select>
                </b-col>
        </b-row>
        
            <br>
		          <b-row>
                    <b-table
                    	striped 
                    	borderless 
                    	outlined 
                    	head-variant="light"
                        stacked="md"
                        id="farmaceuti-tabela"
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
                    <template #cell(obrisiFarmaceuta)="row">
                    <b-button @click="obrisiFarmaceuta(row.item)"  variant="primary">Obrisi</b-button>
                    </template>
                    <template  #cell(izmeniRadnoVreme)="row">
                    <b-button v-on:click="izmeniFarmaceuta(row.item)" variant="primary">Izmeni</b-button></template> </b-table>
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
           	<!-- forma za menjanje farmaceuta -->
	<b-modal ref="my-modal" hide-footer title="Izmeni farmaceuta">
      <b-card style="max-width: 500px; margin: 30px auto;" >
		<h3>{{this.farmaceutDTO.ime}} {{this.farmaceutDTO.prezime}}</h3>
		<p> Njegovo radno vremene: </p>
        {{this.farmaceutDTO.radnoVremePocetak}} - {{this.farmaceutDTO.radnoVremeKraj}}
        <b-form @submit.prevent="onIzmeniFarmaceuta">
          <b-form-group id="input-group-3" label="Cena:" label-for="input-3">
            <b-form-input
            	required
            	type = "number"
				v-model="farmaceutDTO.cena"
				:min = 0 
            />
          <b-form-group id="input-group-3" label="Pocetak radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="farmaceutDTO.radnoVremePocetak"
				:max = this.farmaceutDTO.radnoVremeKraj
            />
           <b-form-group id="input-group-3" label="Kraj radnog vremena:" label-for="input-3">
            <b-form-input
            	required
            	type = "time"
				v-model="farmaceutDTO.radnoVremeKraj"
				:min = this.farmaceutDTO.radnoVremePocetak
            />
		<br>
          <b-button type="submit" variant="primary">Sacuvaj izmene</b-button>
          <b-button type="reset" variant="primary">Resetuj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>
		</b-container>
		</b-container>
    </div>
    `,

  methods: {
    onIzmeniFarmaceuta() {
      let info = {
        params: {
          start:
            "2008-01-01T" + this.farmaceutDTO.radnoVremePocetak + ":00.000Z",
          end: "2008-01-01T" + this.farmaceutDTO.radnoVremeKraj + ":00.000Z",
          cena: this.farmaceutDTO.cena,
          cookie: this.cookie,
          username: this.farmaceutDTO.username,
        },
      };
      console.log(info);
      axios.get("/zdravstveniradnik/changeFarmaceut", info).then((response) => {
        this.$refs["my-modal"].hide();
      });
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
    fixTime(date) {
      return moment(date).format("HH:mm");
    },
    fixDate(date) {
      return moment(date).format("HH:mm");
    },
    itemProvider: function (ctx) {
      console.log(ctx);
      return this.retrieveFarmaceute(
        ctx.page,
        ctx.pageSize,
        ctx.sortBy,
        ctx.sortDesc,
        this.pretragaIme,
        this.pretragaPrezime
      );
    },
    retrieveFarmaceute: async function (
      page,
      pageSize,
      sortBy,
      sortDesc,
      pretragaIme,
      pretragaPrezime
    ) {
      this.table_is_busy = true;
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
      var date1 =
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
          cookie: this.cookie,
          page: this.page - 1,
          size: this.pageSize,
          sortBy: sortBy,
          sortDesc: sortDesc,
          pretraziIme: pretragaIme,
          pretraziPrezime: pretragaPrezime,
          ocena: this.ocena,
          pocetak: this.pocetakRadnog + ":00.000Z",
          kraj: this.krajRadnog + ":00.000Z",
        },
      };
      await axios
        .get("/zdravstveniradnik/getAllFarmaApotekaPage", info)
        .then((response) => {
          console.log(response);
          this.farmaceuti = response.data["content"];
          this.count = response.data["totalElements"];
        })
        .catch((e) => {
          console.log(e);
        });
      console.log(this.farmaceuti);
      this.table_is_busy = false;
      return this.farmaceuti;
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    izmeniFarmaceuta(farmaceut) {
      this.farmaceutDTO = farmaceut;
      this.farmaceutDTO.radnoVremePocetak = this.fixTime(
        farmaceut.radnoVremePocetak
      );
      this.farmaceutDTO.radnoVremeKraj = this.fixTime(farmaceut.radnoVremeKraj);
      this.$refs["my-modal"].show();
    },
    pretraga: function () {
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
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
    handleOcenaChange(event) {
      this.page = 1;
      this.ocena = event;
      console.log(this.ocena);
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
    handlePocetakChange(event) {
      this.page = 1;
      this.pocetakRadnog = event;
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
    handleKrajChange(event) {
      this.page = 1;
      this.krajRadnog = event;
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
    obrisiPretragu: function () {
      this.pretragaIme = "";
      this.pretragaPrezime = "";
      this.page = 1;
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
    obrisiFarmaceuta(farmaceut) {
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
          username: farmaceut.username,
          startDate: date,
          cookie: this.cookie,
        },
      };
      if (
        confirm("Da li ste sigurni da zelite da obrisete farmaceuta?") == true
      ) {
        axios
          .delete("/zdravstveniradnik/deleteFarmaceut", info)
          .then((response) => this.retrieveFarmaceute())
          .catch((error) => {
            if (error.request.status == 400) {
              this.imaZakazeno = true;
            }
          });
      }
      this.$root.$emit("bv::refresh::table", "farmaceuti-tabela");
    },
  },
});
