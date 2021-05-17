Vue.component("PretragaLekAdmin", {
  data: function () {
    return {
      apotekaID: "",
      lekovi: [],
      izabranLek: {
        sifra: "",
        naziv: "",
        uputstvo: "",
        tip: "",
        oblikLeka: "",
        dodatneNapomene: "",
        sastav: "",
        kolicina: "",
        cena: "",
        istekVazenjaCene: "",
        staraCena: "",
        cookie: "",
        zahtevLekovi: [],
      },
      fieldsZahtev: [
        {
          key: "username",
        },
        {
          key: "ime",
        },
        {
          key: "prezime",
        },
        {
          key: "lek",
        },
        {
          key: "lekSifra",
        },
        {
          key: "kolicina",
        },
      ],
      fields: [
        {
          key: "naziv",
          sortable: true,
        },
        {
          key: "sifra",
          sortable: true,
        },
        {
          key: "sastav",
          sortable: true,
        },
        {
          key: "uputstvo",
          sortable: false,
        },
        {
          key: "tip",
          sortable: true,
        },
        {
          key: "oblikLeka",
          sortable: true,
        },
        {
          key: "kolicina",
          sortable: true,
        },
        {
          key: "cena",
          sortable: true,
        },
        {
          key: "staraCena",
          sortable: true,
        },
        {
          key: "istekVazenjaCene",
          sortable: true,
          label: "Datum isteka vazenja cene",
          formatter: (value, key, item) => {
            return moment(value).format("DD/MM/YYYY");
          },
        },
        {
          key: "obrisiLek",
          sortable: false,
        },
        {
          key: "izmeniLek",
          sortable: false,
        },
      ],
      page: 1,
      count: "",
      pageSize: 6,
      pageSizes: [6, 10, 20, 50],
      table_is_busy: false,
      sortDesc: false,
      sortBy: "naziv",
      searchTitle: "",
      pageZahtev: 1,
      countZahtev: "",
      pageSizeZahtev: 6,
      pageSizesZahtev: [6, 10, 20, 50],
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
      	
    <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
    <b-card style="margin: 40px auto; max-width: 2000px">
        <b-container>
          <b-tabs content-class="mt-3">
            <b-tab title="Lekovi" active>
          <b-row>
            <b-col>
              <b-form-input v-model="searchTitle" placeholder="Pretrazite lekove"></b-form-input>
            </b-col>
            <b-col>
              <b-button v-on:click="pretraga" style="float: right">Pretrazi</b-button>
              <b-button v-on:click="obrisiPretragu" style="float: right" variant="danger">Resetuj</b-button>
            </b-col>
            </b-row>
            <br>
		          <b-row>
                    <b-table
                        id="lekovi-tabela"
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
                    <template #cell(obrisiLek)="row">
                    <b-button @click="deleteLek(row.item)"  variant="primary">Obrisi</b-button>
                    </template>
                    <template  #cell(izmeniLek)="row">
                    <b-button v-on:click="prikaziPromeniLek(row.item)" variant="primary">Izmeni</b-button></template>
                     </b-table>
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
         </b-tab>
         <b-tab title="Zahtevi">
         		          <b-row>
                    <b-table
                        id="zahtevi-tabela"
                        hover
                        :items="itemProviderZahtev"
                        :fields="fieldsZahtev"
                        :per-page="pageSizeZahtev"
                        :current-page="pageZahtev"
                        :busy.sync="table_is_busy"
                        responsive="sm">
                     </b-table>
         </b-row>
         </b-tab>
        </b-containter>
      </b-card>



	<!-- forma za promenu podataka -->
	<b-modal ref="my-modal" hide-footer title="Izmena podataka lekova">
      <b-card style="max-width: 500px; margin: 30px auto;" >
        <b-form @submit.prevent="onIzmeniLek">
        <h3>{{this.izabranLek.naziv}}</h3>
        <b-form-group id="input-group-1" label="Cena:" label-for="input-1">
            <b-form-input
                id="input-1"
                type="number"
                v-model="izabranLek.cena"
                min = "0"
            ></b-form-input>
       <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
                v-model="izabranLek.kolicina"
                min = "0"
            ></b-form-input>
        <b-form-group id="input-group-2" label="Datum isteka vazenja cene:" label-for="input-2">
            <b-form-input
                id="input-2"
                type="date"
                v-model="izabranLek.istekVazenjaCene"
            ></b-form-input>

       <b-form-group id="input-group-3" label="Stara cena:" label-for="input-3">
            <b-form-input
                id="input-3"
                type="number"
                v-model="this.izabranLek.staraCena"
                min = "0"
            ></b-form-input>
          <b-button type="submit" variant="primary">Sacuvaj</b-button>
           </b-form>
      	</b-card>
    	</b-modal>	
      </div>
    `,
  methods: {
    onIzmeniLek: function () {
      this.izabranLek.cookie = this.cookie;
      axios.put("lekovi", this.izabranLek);
      this.izabranLek = "";
      this.$refs["my-modal"].hide();
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
    },
    onReset: function (event) {
      this.izabranLek = this.reverseLek;
    },

    prikaziPromeniLek(lek) {
      this.reverseLek = JSON.parse(JSON.stringify(lek));
      this.izabranLek = JSON.parse(JSON.stringify(lek));
      this.$refs["my-modal"].show();
    },
    redirectToHome: function () {
      app.$router.push("/home-admin_apoteke");
    },

    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    deleteLek: function (lek) {
      let info = {
        params: {
          sifraLeka: lek.sifra,
          cookie: this.cookie,
        },
      };
      axios
        .delete("/lekovi/deleteLek", info)
        .then((response) => console.log(response.data));
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
    },
    itemProviderZahtev: function (ctx) {
      return this.retrieveZahtevLek(ctx.page, ctx.pageSize);
    },
    retrieveZahtevLek: async function (page, pageSize) {
      let info = {
        params: {
          cookie: this.cookie,
          page: this.pageZahtev - 1,
          size: this.pageSizeZahtev,
        },
      };
      await axios.get("lekovi/zahteviZaLek", info).then((response) => {
        console.log(response.data);
        this.zahtevLekovi = response.data["content"];
        this.countZahtev = response.data["totalElements"];
        console.log(this.zahtevLekovi);
      });
      return this.zahtevLekovi;
    },
    itemProvider: function (ctx) {
      console.log(ctx);
      return this.retrieveLekove(
        ctx.page,
        ctx.pageSize,
        ctx.sortBy,
        ctx.sortDesc,
        this.searchTitle
      );
    },
    retrieveLekove: async function (
      page,
      pageSize,
      sortBy,
      sortDesc,
      searchTitle
    ) {
      this.table_is_busy = true;
      let info = {
        params: {
          cookie: this.cookie,
          page: this.page - 1,
          size: this.pageSize,
          sortBy: sortBy,
          sortDesc: sortDesc,
          title: searchTitle,
        },
      };
      console.log(info);
      await axios.get("lekovi/aa", info).then((response) => {
        this.lekovi = response.data["content"];
        this.count = response.data["totalElements"];
      });
      this.table_is_busy = false;
      return this.lekovi;
    },
    pretraga: function () {
      this.page = 0;
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
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
      this.searchTitle = "";
      this.page = 0;
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
    },
  },
});
