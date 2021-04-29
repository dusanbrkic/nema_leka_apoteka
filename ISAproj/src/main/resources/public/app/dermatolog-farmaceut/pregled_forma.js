Vue.component("PregledForma", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            pregled: {
                start: new Date(),
                end: new Date(),
                apoteka: {naziv: null, adresa: null},
                pacijent: {ime: null, prezime: null},
                preporuceniLekovi: [],
                dijagnoza: null,
                pregledZakazan: null,
                pregledObavljen: null,
                trajanje: null,
            }
        }
    },

    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
        this.pregled = JSON.parse(localStorage.getItem("pregled"))
    },
    template:
        `
          <div>
          <b-modal id="zakazivanjeModal" title="Zakazi pregled pacijentu">
           <template #modal-footer="{ ok }">
              <b-button @click="ok()">
                Otkazi
              </b-button>
              <b-button v-on:click="zakazi_pregled" variant="success" @click="ok()">
                Zakazi
              </b-button>
           </template>
          </b-modal>
          <b-card style="max-width: 700px; margin: 30px auto;" title="Upisite podatke o pregledu:">
            <b-form @submit.prevent="onSubmit">
              <b-container>
                <b-row>
                  <b-col>
                    <strong>Ime i prezime pacijenta:</strong> {{ pregled.pacijent.ime }} {{ pregled.pacijent.prezime }}
                  </b-col>
                </b-row>
                <br>
                <b-row>
                  <b-col>
                    <b-form-group
                        id="input-group-2"
                        label="Dijagnoza:"
                        label-for="input-2">
                      <b-form-textarea
                          id="input-2"
                          placeholder="Upisite pacijentovu dijagnozu..."
                          rows="3"
                          max-rows="6"
                          v-model="pregled.dijagnoza"
                      ></b-form-textarea>
                    </b-form-group>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col>
                    Preporuceni lekovi:
                    <b-button v-on:click="returnToHome" variant="primary" style="margin-left:10px">Dodaj lek</b-button>
                  </b-col>
                </b-row>
                <template v-for="lek in pregled.preporuceniLekovi">
                  <b-row>
                    <b-col>
                      - {{ lek.naziv }}
                    </b-col>
                    <b-col>
                      <b-button v-on:click="returnToHome" variant="danger">Izbaci lek</b-button>
                    </b-col>
                  </b-row>
                </template>
                <br>
                <b-row>
                  <b-col>
                    <b-button v-on:click="returnToHome">Nazad</b-button>
                    <b-button type="submit" variant="primary">Sacuvaj</b-button>
                    <b-button style="float: right" variant="primary" v-on:click="zakazivanje_forma">
                      Zakazi dodatan pregled
                    </b-button>
                  </b-col>
                </b-row>
              </b-container>
            </b-form>
          </b-card>
          </div>

        `
    ,
    methods: {
        onSubmit: function () {
            axios
                .put("pregledi/updatePregled", this.pregled)
            localStorage.removeItem("pregled")
            if (this.rola == "FARMACEUT") app.$router.push("/home-farmaceut")
            else if (this.rola == "DERMATOLOG") app.$router.push("/home-dermatolog")
        },
        zakazivanje_forma: function () {
            this.$bvModal.show('zakazivanjeModal')
        },
        returnToHome: function () {
            if (this.rola == "FARMACEUT") app.$router.push("/home-farmaceut")
            else if (this.rola == "DERMATOLOG") app.$router.push("/home-dermatolog")
            else {
                localStorage.clear()
                app.$router.push("home")
            }
            localStorage.removeItem("pregled")
        },
        zakazi_pregled: function (){

        }
    }
});
