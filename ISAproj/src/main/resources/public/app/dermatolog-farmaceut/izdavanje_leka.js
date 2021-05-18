Vue.component("IzdavanjeLeka", {
    data: function () {
        return {
            cookie: "",
            idRezervacije: '',
            idNeodgovarajuciAlert: false,
            idUspesnoPoslat: false,
            lekoviIzdati: false,
            rezervacije: {
                kolicina: null,
                lek: null
            }
        };
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
      <template v-if="!idUspesnoPoslat">
        <b-card style="max-width: 500px; margin: 30px auto;" title="Izdavanje leka">
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
        </b-card>
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
                    this.rezervacije = response.data
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
        }
    },
});
