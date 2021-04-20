Vue.component("PregledaniPacijenti", {
    data: function () {
        return {
            cookie: '',
            rola: '',
            pageSizes:[2, 10, 20, 50],
            fields: [
                {
                    key: 'ime',
                    sortable: true
                },
                {
                    key: 'prezime',
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
                    key: 'datum_pregleda',
                    sortable: true,
                    formatter: (value, key, item) => {
                        return moment(value).format("DD/MM/YYYY");
                    },
                },
            ],
            items_per_page: 2,
            page: 1,
            totalItems: '',
            table_is_busy: false
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-table hover :items="itemProvider" :fields="fields" :per-page="items_per_page" :current-page="page"
                 :busy.sync="table_is_busy"></b-table>
        <b-container>
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
        loadPregledi: async function (currentPage, perPage) {
            this.table_is_busy = true
            let items = []
            await axios
                .get("pregledi/getPreglediByDermatolog", {
                    params:
                        {
                            'cookie': this.cookie,
                            'page': currentPage - 1,
                            'size': perPage
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
                            datum_pregleda: p.start
                        })
                    }
                })
            this.table_is_busy = false
            return items
        },
        loadSavetovanja: async function (currentPage, perPage) {
            this.table_is_busy = true
            let items = []
            await axios
                .get("savetovanja/getSavetovanjaByFarmaceut", {
                    params:
                        {
                            'cookie': this.cookie,
                            'page': currentPage - 1,
                            'size': perPage
                        }
                })
                .then(response => {
                    let savetovanja = response.data['content']
                    this.totalItems =  response.data['totalElements']
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
                            datum_pregleda: s.start
                        })
                    }
                })
            this.table_is_busy = false
            return items
        },
        itemProvider: function (ctx) {
            if (this.rola == "FARMACEUT") return this.loadSavetovanja(ctx.currentPage, ctx.perPage)
            else if (this.rola == "DERMATOLOG") return this.loadPregledi(ctx.currentPage, ctx.perPage)
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
