Vue.component("OdsustvoForma", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            forma: {
                "startDate": "",
                "endDate": ""
            },
            terminZauzet: false,
            uspesnoPoslat: false,
            datumiNeodgovarajuci: false,
            today: new Date(),
        };
    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
        this.rola = localStorage.getItem("userRole");
    },
    template: `
      <div>
      <b-alert style="text-align: center;" v-model="terminZauzet" variant="danger">Termin Zauzet!</b-alert>
      <b-alert style="text-align: center;" v-model="datumiNeodgovarajuci" variant="danger">Termin se ne poklapaju kraj
        odmora > pocetak odmora!
      </b-alert>
      <b-alert style="text-align: center;" v-model="uspesnoPoslat" variant="primary">Uspesno ste poslali!</b-alert>
      <b-card style="max-width: 500px; margin: 30px auto;" title="Zahtev za odsustvom">
        <b-form @submit.prevent="onSubmit">
          <b-form-group
              id="input-group-1"
              label="Pocetak:"
              label-for="input-1"
              description="Unesi datum pocetka godisnjeg odmora">
            <b-form-datepicker
                id="input-1"
                :min="today"
                v-model="forma.startDate"
                required="true"
                placeholder="Izaberite datum"
                locale="sr-latn"
                class="mb-2"
            ></b-form-datepicker>
          </b-form-group>
          <b-form-group
              id="input-group-2"
              label="Kraj:"
              label-for="input-2"
              description="Unesi datum kraja godisnjeg odmora">
            <b-form-datepicker
                :min="today"
                v-model="forma.endDate"
                required="true"
                placeholder="Izaberite datum"
                locale="sr-latn"
                class="mb-2"
            ></b-form-datepicker>
          </b-form-group>
          <b-button v-on:click="returnToHome">Nazad</b-button>
          <b-button type="submit" variant="primary">Posalji</b-button>
        </b-form>
      </b-card>
      </div>
    `,
    methods: {
        returnToHome: function () {
            if (this.rola === "DERMATOLOG") {
                app.$router.push("/home-dermatolog")
            } else if (this.rola === "FARMACEUT") {
                app.$router.push("/home-farmaceut")
            } else {
                localStorage.clear()
                app.$router.push("/")
            }
        },
        onSubmit: function (event) {
            event.preventDefault()
            let endDate = new Date(this.forma.endDate + " 00:00")
            let startDate = new Date(this.forma.startDate+ " 00:00")
            axios
                .get("zdravstveniradnik/putOdsustvo", {
                        params:
                            {
                                'start': startDate,
                                'end': new Date(endDate.setDate(endDate.getDate() + 1)),
                                'cookie': this.cookie
                            }
                    }
                )
                .then(response => {
                    this.terminZauzet = false
                    this.uspesnoPoslat = true
                    this.datumiNeodgovarajuci = false
                })
                .catch(error => {
                    if (error.request.status == 404) {
                        this.terminZauzet = true
                        this.uspesnoPoslat = false
                        this.datumiNeodgovarajuci = false
                    } else if (error.request.status == 401) {
                        this.terminZauzet = false
                        this.uspesnoPoslat = false
                        this.datumiNeodgovarajuci = true
                    }
                })
        }
    },
});
