Vue.component("PacijentNarudzbenice", {
    data: function () {
        return {
            cookie: '',
            fields: [
                {
                    key: 'idRezervacije',
                    sortable: false
                },
                {
                    key: 'lekovi',
                    sortable: false
                },
                {
                    key: 'apoteka',
                    sortable: false
                },
                {
                    key: 'datumIstekaRezervacije',
                    sortable: false
                },
            ],

            table_is_busy: false,

        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-container>

          <b-row>
            <b-table
                id="rezervacije-tabela"
                hover
                :items="itemProvider"
                :fields="fields"
                :busy.sync="table_is_busy"
            ></b-table>
          </b-row>

        </b-container>
      </b-card>
      </div>
    `,
    methods: {
        loadPregledi: async function () {
            this.table_is_busy = true
            let items = []
            await axios
                .get("rezervacije/moje_rezervacije", {
                    params:
                        {
                            'cookie': this.cookie,
                        }
                })
                .then(response => {
                    let pregledi = response.data
                    for (const p of pregledi) {
                    	console.log(p);
                        items.push({
                            idRezervacije: p.id,
                            lekovi: p.sifraLeka,
                            apoteka: p.apotekaId,
                            datumIstekaRezervacije: p.datumRezervacije.slice(0, 10)
                        })
                    }
                })
            this.table_is_busy = false
            return items
        },
        itemProvider: function (ctx) {
            return this.loadPregledi()
        },
    },
});
