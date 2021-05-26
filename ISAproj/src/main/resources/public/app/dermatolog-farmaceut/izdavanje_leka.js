Vue.component("IzdavanjeLeka", {
    data: function () {
        return {
            cookie: "",
            idRezervacije: '',
            idNeodgovarajuciAlert: false,
            idUspesnoPoslat: false,
            lekoviIzdati: false,
            rezervacije: [],
            ukupnaCena: '',
            fields: [
                {
                    key: 'sifra',
                    label: 'Sifra leka',
                },
                {
                    key: 'naziv',
                    label: 'Naziv leka',
                },
                {
                    key: 'kolicina',
                    label: 'Kolicina',
                },
                {
                    key: 'cena',
                    label: 'Cena',
                }
            ]
        }
    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
    },
    template: `
      <div>
      <b-alert style="text-align: center;" v-model="idNeodgovarajuciAlert" variant="danger">Broj rezervacije nije
        ispravan!
      </b-alert>
      <b-alert style="text-align: center;" v-model="lekoviIzdati" variant="primary">Uspesno ste izdali lekove!
      </b-alert>
      <b-card style="max-width: 500px; margin: 30px auto;" title="Izdavanje leka">
        <template v-if="!idUspesnoPoslat">
          <b-form @submit.prevent="onSubmit">
            <b-form-group
                id="input-group-1"
                label="Broj rezervacije:"
                label-for="input-1"
                description="Unesite jedinstveni broj rezervacije">
              <b-form-input
                  id="input-1"
                  v-model="idRezervacije"
                  type="number"
                  required
              ></b-form-input>
            </b-form-group>
            <b-button align="center" type="submit" variant="primary">Proveri</b-button>
          </b-form>
        </template>
        <template v-if="idUspesnoPoslat">
          <b-table
              id="pacijenti-tabela"
              hover
              stripped
              :items="rezervacije"
              :fields="fields"
          ></b-table>
          <b-container>
            <b-row>
              <label style="align-items: end">Ukupna cena: {{ ukupnaCena }}</label>
            </b-row>
            <b-row>
              <b-button style="float: right; margin: 5px" v-on:click="izdajLekove" variant="primary">Izdaj</b-button>
              <b-button style="float: right; margin: 5px" v-on:click="returnToUnosId">Nazad</b-button>
            </b-row>
          </b-container>
        </template>
      </b-card>
      <template v-if="idUspesnoPoslat">
      </template>
      </div>
    `,
    methods: {
        onSubmit: async function (event) {
            event.preventDefault()
            await axios
                .get("rezervacije/proveriRezervaciju", {
                        params:
                            {
                                'cookie': this.cookie,
                                'idRezervacije': this.idRezervacije
                            }
                    }
                )
                .then(response => {
                    this.idNeodgovarajuciAlert = false
                    this.lekoviIzdati = false
                    this.idUspesnoPoslat = true
                    this.ukupnaCena = 0
                    for (const lek of response.data) {
                        this.ukupnaCena += lek.cena * lek.kolicina
                        this.rezervacije.push({
                            naziv: lek.lek.naziv,
                            cena: lek.cena,
                            kolicina: lek.kolicina,
                            sifra: lek.lek.sifra
                        })
                    }
                })
                .catch(error => {
                    if (error.request.status == 400) {
                        this.idNeodgovarajuciAlert = true
                        this.idUspesnoPoslat = false
                        this.lekoviIzdati = false
                    }
                })
        },
        izdajLekove: async function () {
            await axios
                .get("rezervacije/izdajLekove", {
                        params:
                            {
                                'cookie': this.cookie,
                                'idRezervacije': this.idRezervacije
                            }
                    }
                )
                .then(response => {
                    this.idNeodgovarajuciAlert = false
                    this.idUspesnoPoslat = false
                    this.lekoviIzdati = true
                })
                .catch(error => {
                    if (error.request.status == 400) {
                        this.idNeodgovarajuciAlert = true
                        this.idUspesnoPoslat = false
                        this.lekoviIzdati = false
                    }
                })
        },
        returnToUnosId: function () {
            this.idUspesnoPoslat = false
        }
    },
});
