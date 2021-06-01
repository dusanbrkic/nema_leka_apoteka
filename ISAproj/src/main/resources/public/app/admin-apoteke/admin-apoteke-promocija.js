Vue.component("AdminPromocija", {
  data: function () {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const minDate = new Date(today);
    return {
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
          key: "dodajPromociju",
          label: "",
          sortable: false,
        },
      ],
      fieldsPromocija: [
        {
          key: "naziv",
          sortable: true,
        },
        {
          key: "sifra",
          sortable: true,
        },
        {
          key: "kolicina",
          sortable: true,
        },
        {
          key: "cena",
          label: "Nova cena",
          sortable: true,
        },
        {
          key: "promotivnaCena",
          sortable: true,
        },
        { key: "obrisiLek", label: "", sortable: false },
      ],
      izabranLek: "",
      cookie: "",
      lekovi: [],
      lekoviPromocija: [],
      page: 1,
      count: "",
      pageSize: 6,
      pageSizes: [2, 6, 10, 20, 50],
      table_is_busy: false,
      sortDesc: false,
      sortBy: "naziv",
      searchTitle: "",
      min: minDate,
      pocetakVazenja: "",
      krajVazenja: "",
      tekstPromocije: "",
      lekIndex: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.apotekaID = localStorage.getItem("apotekaID");
    this.itemProvider(this);
  },
  template: `    <div>
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
            <b-nav-item href="#/admin-apoteke-narudzbenice">Narudzbine</b-nav-item>
            <b-nav-item href="#/admin-apoteke-promocija">Promocije</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>






		<b-card style="margin: 40px auto; max-width: 2000px" >
      <b-container id="page_content">
      <div class="text-center"><h2>Dodavanje promocije</h2></div>
          <br>
    <b-form @submit.prevent="onDodajPromociju">
      <b-row class="mt-2">
      <b-col sm="2">
        <label for="textarea-default">Opis promocije:</label>
      </b-col>
      <b-col sm="10">
        <b-form-textarea
          required
          v-model = "tekstPromocije"
          id="textarea-default"
          placeholder="Unesite opis promocije"
        ></b-form-textarea>
      </b-col>
    </b-row>
    <br>
            <b-row >
        <b-col sm="2">
        <label>Pocetak promocije:</label>
        </b-col>
        <b-col sm="2"><b-form-datepicker required="true" v-model = "pocetakVazenja" required :min="min" type= "date"></<b-form-datepicker></b-col>
        <b-col sm="2">
        <label>Kraj promocije:</label>
        </b-col>
        <b-col sm="2"><b-form-datepicker required="true" v-model = "krajVazenja" :min="min"></<b-form-datepicker></b-col>
        <b-col>
         <div v-if="lekoviPromocija.length != 0">
        <b-button class="text-right" style="float: right" type="submit" variant="primary">Dodaj promociju</b-button>
          </div>
        </b-col>
        </b-row>

                </b-form>
        <br>
    <hr>
    <b-tabs content-class="mt-3" fill>

      <b-tab title="Lekovi" active>
         <!-- Tabela ostalih -->
        <b-row>
          <b-table
              id="lekovi-tabela"
              striped 
              borderless 
              outlined 
              head-variant="light"
              stacked="md"
              :items="lekovi"
              :fields="fields"
              :per-page="pageSize"
              :current-page="page"
              :busy.sync="table_is_busy"
              :sort-by.sync="sortBy"
              sort-icon-left
              responsive="sm"
              :sort-desc.sync="sortDesc">
          <template #cell(dodajPromociju)="row">
            <b-button v-on:click="dodajLekPromociju(row)" variant="primary">Dodaj u promociju</b-button>
          </template>
        </b-table>
        </b-row>
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
      <b-tab title="Lekovi u promociji">

      <!-- Tabela dodatih -->
        <div class="text-center">
            <b-row >
            <div v-if="lekoviPromocija.length != 0">
        <b-table
            id="promocija-tabela"
            style="min-width: 1000px;"
            striped 
            borderless 
            outlined 
            head-variant="light"
            stacked="md"
            :items="lekoviPromocija"
            :fields="fieldsPromocija"
            :per-page="pageSize"
            :current-page="page"
            :busy.sync="table_is_busy"
            :sort-by.sync="sortBy"
            sort-icon-left
            responsive="sm"
            :sort-desc.sync="sortDesc">
          <template #cell(obrisiLek)="row">
            <b-button v-on:click="obrisiIzPromocije(row.index)" variant="danger">Obrisi</b-button>
          </template>
          </b-table>
          </div>
          </b-row>
        <br>


        <br>
      </div>
      </b-tab>
         </b-tabs>
			</b-container>
		</b-card>

		<!-- forma za dodavanje -->
     <b-modal ref="my-modal" hide-footer title="Dodaj lek u promociju">
  <b-card style="max-width: 500px; margin: 30px auto;" >
    <b-form @submit.prevent="onDodajLekPromociju">
    <h3>{{izabranLek.naziv}}</h3>
   <b-form-group id="input-group-3" label="Trenutna cena:" label-for="input-3">
        <b-form-input
          readonly
            id="input-3"
            type="number"
            v-model="izabranLek.cena"
        ></b-form-input>
        </b-form-group>
    <b-form-group id="input-group-3" label="Kolicina:" label-for="input-3">
      <b-form-input
          readonly
            id="input-3"
            type="number"
            v-model="izabranLek.kolicina"
        ></b-form-input>
        </b-form-group>
  <b-form-group id="input-group-3" label="Nova cena:" label-for="input-3">
      <b-form-input
          required
            id="input-3"
            type="number"
            v-model="izabranLek.promotivnaCena"
        ></b-form-input>
        </b-form-group>
<br>
      <b-button type="submit" variant="primary">Dodaj u promociju</b-button>
       </b-form>
    </b-card>
  </b-modal>	
    
		</div>`,

  methods: {
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    onDodajPromociju() {
      for (i = 0; i < this.lekoviPromocija.length; i++) {
        this.lekoviPromocija[i].pocetakVazenja =
          this.pocetakVazenja + "T" + "00:00:00.000Z";
        this.lekoviPromocija[i].krajVazenja =
          this.krajVazenja + "T" + "00:00:00.000Z";
        this.lekoviPromocija[i].tekstPromocije = this.tekstPromocije;
        console.log(this.pocetakVazenja);
        this.$root.$emit("bv::refresh::table", "lekovi-tabela");
      }
      axios
        .post(
          "/promocije/addPromocija",
          JSON.parse(JSON.stringify(this.lekoviPromocija))
        )
        .then((response) => console.log(response.data));

      this.$bvToast.toast(`Dodata nova promocija`, {
        toaster: "b-toaster-top-center",
        variant: "success",
        solid: true,
        autoHideDelay: 4000,
        noCloseButton: true,
      });
      this.lekoviPromocija = [];
    },
    obrisiIzPromocije(index) {
      console.log();
      this.lekovi.push(this.lekoviPromocija[index]);
      console.log(this.lekovi);
      this.lekoviPromocija.splice(index, 1);
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
    },
    dodajLekPromociju(row) {
      console.log(row);
      this.izabranLek = JSON.parse(JSON.stringify(row.item));
      this.lekIndex = row.index;
      console.log(this.lekIndex);
      this.$refs["my-modal"].show();
    },
    onDodajLekPromociju() {
      this.lekoviPromocija.push(this.izabranLek);
      this.izabranLek.cookie = this.cookie;
      for (i = 0; i < this.lekovi.length; i++) {
        if (this.lekovi[i].sifra === this.izabranLek.sifra) {
          this.lekovi.splice(i, 1);
        }
      }
      //this.lekovi.splice(this.lekIndex, 1);
      //console.log(this.lekIndex);
      this.izabranLek = "";
      this.$root.$emit("bv::refresh::table", "lekovi-tabela");
      this.$refs["my-modal"].hide();
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
    handlePageChange(value) {
      this.page = value;
    },
    handlePageSizeChange(event) {
      this.page = 1;
      this.items_per_page = event.target.value;
    },
  },
});
