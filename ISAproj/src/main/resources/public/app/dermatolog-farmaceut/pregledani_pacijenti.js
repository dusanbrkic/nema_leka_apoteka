Vue.component("PregledaniPacijenti", {
    data: function () {
        return {
            cookie: '',
            rola: '',
            pageSizes: [2, 10, 20, 50],
            fields: [
                {
                    key: 'prezime',
                    sortable: true
                },
                {
                    key: 'ime',
                    sortable: true
                },
                {
                    key: 'datum_rodjenja',
                    sortable: true,
                    formatter: (value, key, item) => {
                        return moment(value).format("DD/MM/YYYY");
                    },
                },
                {
                    key: 'email_adresa',
                    sortable: true
                },
                {
                    key: 'adresa',
                    sortable: true
                },
                {
                    key: 'grad',
                    sortable: true
                },
                {
                    key: 'drzava',
                    sortable: true
                },
                {
                    key: 'broj_telefona',
                    sortable: false
                },
                {
                    key: 'vreme',
                    sortable: true,
                    formatter: (value, key, item) => {
                        return moment(value).format("DD/MM/YYYY");
                    },
                },
            ],
            items_per_page: 2,
            page: 1,
            totalItems: '',

            table_is_busy: false,
            sortBy: 'vreme',
            sortDesc: false,

            pretragaIme: '',
            pretragaPrezime: ''
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-container>
          <b-row>
            <b-col>
              <b-form-input v-model="pretragaPrezime" placeholder="Pretrazite prezime"></b-form-input>
            </b-col>
            <b-col>
              <b-form-input v-model="pretragaIme" placeholder="Pretrazite ime"></b-form-input>
            </b-col>
            <b-col>
              <b-button v-on:click="pretraga" style="float: right">Pretrazi</b-button>
              <b-button v-on:click="obrisiPretragu" style="float: right" variant="danger">Resetuj</b-button>
            </b-col>
          </b-row>
          <br>
          <b-row>
            <b-table
                id="pacijenti-tabela"
                hover
                :items="itemProvider"
                :fields="fields"
                :per-page="items_per_page"
                :current-page="page"
                :busy.sync="table_is_busy"
                :sort-by.sync="sortBy"
                :sort-desc.sync="sortDesc"
            ></b-table>
          </b-row>
          <b-row>
            <b-col>
              <b-pagination
                  v-model="page"
                  :total-rows="totalItems"
                  :per-page="items_per_page"
                  @change="handlePageChange"
              ></b-pagination>
            </b-col>
            <b-col>
              <select v-model="items_per_page" @change="handlePageSizeChange($event)" style="float: right">
                <option v-for="size in pageSizes" :key="size" :value="size">
                  {{ size }}
                </option>
              </select>
            </b-col>
          </b-row>
        </b-container>
      </b-card>
      </div>
    `,
    methods: {
        loadPregledi: async function (currentPage, perPage, sortBy, sortDesc, pretragaIme, pretragaPrezime) {
            this.table_is_busy = true
            let items = []
            await axios
                .get("pregledi/getPreglediByDermatolog", {
                    params:
                        {
                            'cookie': this.cookie,
                            'page': currentPage - 1,
                            'size': perPage,
                            'sortBy': sortBy,
                            'sortDesc': sortDesc,
                            'obavljeni': true,
                            'pretragaIme': pretragaIme,
                            'pretragaPrezime': pretragaPrezime
                        }
                })
                .then(response => {
                    let pregledi = response.data['content']
                    this.totalItems = response.data['totalElements']
                    for (const p of pregledi) {
                        items.push({
                            ime: p.pacijent.ime,
                            prezime: p.pacijent.prezime,
                            datum_rodjenja: p.pacijent.datumRodjenja,
                            email_adresa: p.pacijent.emailAdresa,
                            adresa: p.pacijent.adresa,
                            grad: p.pacijent.grad,
                            drzava: p.pacijent.drzava,
                            broj_telefona: p.pacijent.brojTelefona,
                            vreme: p.start
                        })
                    }
                })
            this.table_is_busy = false
            return items
        },
        loadSavetovanja: async function (currentPage, perPage, sortBy, sortDesc, pretragaIme, pretragaPrezime) {
            this.table_is_busy = true
            let items = []
            await axios
                .get("savetovanja/getSavetovanjaByFarmaceut", {
                    params:
                        {
                            'cookie': this.cookie,
                            'page': currentPage - 1,
                            'size': perPage,
                            'sortBy': sortBy,
                            'sortDesc': sortDesc,
                            'obavljena': true,
                            'pretragaIme': pretragaIme,
                            'pretragaPrezime': pretragaPrezime
                        }
                })
                .then(response => {
                    let savetovanja = response.data['content']
                    this.totalItems = response.data['totalElements']
                    for (const s of savetovanja) {
                        items.push({
                            ime: s.pacijent.ime,
                            prezime: s.pacijent.prezime,
                            datum_rodjenja: s.pacijent.datumRodjenja,
                            email_adresa: s.pacijent.emailAdresa,
                            adresa: s.pacijent.adresa,
                            grad: s.pacijent.grad,
                            drzava: s.pacijent.drzava,
                            broj_telefona: s.pacijent.brojTelefona,
                            vreme: s.start
                        })
                    }
                })
            this.table_is_busy = false
            return items
        },
        itemProvider: function (ctx) {
            if (this.rola == "FARMACEUT") return this.loadSavetovanja(ctx.currentPage, ctx.perPage, ctx.sortBy, ctx.sortDesc, this.pretragaIme, this.pretragaPrezime)
            else if (this.rola == "DERMATOLOG") return this.loadPregledi(ctx.currentPage, ctx.perPage, ctx.sortBy, ctx.sortDesc, this.pretragaIme, this.pretragaPrezime)
        },
        handlePageChange(value) {
            this.page = value;
        },
        handlePageSizeChange(event) {
            this.page = 1;
            this.items_per_page = event.target.value;
        },
        pretraga: function () {
            this.page = 0
            this.$root.$emit('bv::refresh::table', 'pacijenti-tabela')
        },
        obrisiPretragu: function () {
            this.pretragaPrezime = ""
            this.pretragaIme = ""
            this.page = 0
            this.$root.$emit('bv::refresh::table', 'pacijenti-tabela')
        }
    },
});
