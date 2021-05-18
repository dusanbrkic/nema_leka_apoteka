Vue.component("AdminNarudzbenice", {
  data: function () {
    return {
      pageSizes: [2, 6, 10, 20, 50],
      narudzbine: [],
      cookie: "",
      fields: [
        {
          key: "rokPonude",
          sortable: true,
          formatter: (value, key, item) => {
            return moment(value).format("DD/MM/YYYY");
          },
        },
        {
          key: "preuzet",
          sortable: true,
        },
        {
          key: "lekovi",
          sortable: false,
        },
      ],
      page: 1,
      count: "",
      pageSize: 6,
      table_is_busy: false,
      sortDesc: false,
      sortBy: "rokPonude",
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
            <b-nav-item href="#/admin-apoteke-narudzbenice">Narudzbine</b-nav-item>
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
      <b-container id="page_content">
			      <div class="text-center"><h2>Narudzbenice</h2></div>
          <br>	
      <b-row>
                    <b-table
                        striped
                        id="narudzbine-tabela"
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
                    <template #cell(preuzet)="row">
                    <div v-if="row.value">
                        Preuzet
                      </div>
                    <div v-else>
                      Nije preuzet
                    </div>
                    </template>
          </b-table>
         </b-row>
         </b-containter>
      </b-card>
      
      
      
      </div>
	`,
  methods: {
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    itemProvider: function (ctx) {
      console.log(ctx);
      return this.retrieveNarudzbine(
        ctx.page,
        ctx.pageSize,
        ctx.sortBy,
        ctx.sortDesc
      );
    },
    retrieveNarudzbine: async function (page, pageSize, sortBy, sortDesc) {
      this.table_is_busy = true;
      let info = {
        params: {
          cookie: this.cookie,
          page: this.page - 1,
          size: this.pageSize,
          sortBy: sortBy,
          sortDesc: sortDesc,
        },
      };
      await axios.get("narudzbine/sveNarudzbine", info).then((response) => {
        console.log(response);
        this.narudzbine = response.data["content"];
        this.count = response.data["totalElements"];
      });
      for (i = 0; i < this.narudzbine.length; i++) {
        this.narudzbine[i].lekovi = "";
        for (j = 0; j < this.narudzbine[i].narudzbeniceLekDTO.length; j++) {
          el =
            this.narudzbine[i].narudzbeniceLekDTO[j].naziv +
            " x " +
            this.narudzbine[i].narudzbeniceLekDTO[j].kolicina +
            ", ";
          this.narudzbine[i].lekovi = this.narudzbine[i].lekovi + el;
        }
      }
      for (i = 0; i < this.narudzbine.length; i++) {
        if (this.narudzbine[i].preuzet) {
          this.narudzbine[i].status = "Preuzet";
        } else {
          this.narudzbine[i].status = "Nije Preuzet";
        }
      }
      this.table_is_busy = false;
      return this.narudzbine;
    },
  },
});
