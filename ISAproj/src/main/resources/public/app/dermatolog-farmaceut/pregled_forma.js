Vue.component("PregledForma", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            optionPicked: "DATE",
            datumPregleda: '',
            datumTermina: '',
            today: new Date(),
            pregledStart: '',
            pregledEnd: '',
            selectedTerm: null,
            nullTerm: {value: null, text: 'Izaberite termin:'},
            terms: [
                {value: null, text: 'Izaberite termin:'}
            ],
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
            <template v-if="rola=='DERMATOLOG'">
              <label for="radio-group-1">Izaberite nacin zakazivanja:</label>
              <b-form-group v-slot="{ ariaDescribedby }">
                <b-form-radio-group
                    id="radio-group-1"
                    v-model="optionPicked"
                    :aria-describedby="ariaDescribedby"
                    name="radio-sub-component"
                >
                  <b-form-radio value="DATE">Novi datum</b-form-radio>
                  <b-form-radio value="TERM">Definisani termin</b-form-radio>
                </b-form-radio-group>
              </b-form-group>
            </template>
            <template v-if="optionPicked=='DATE'">
              <label for="pregled-datepicker">Datum termina:</label>
              <b-form-datepicker
                  :min="today"
                  required="true"
                  placeholder="Izaberite datum"
                  locale="sr-latn"
                  id="pregled-datepicker"
                  v-model="datumPregleda"
                  class="mb-2"/>
              <b-container>
                <b-row>
                  <b-col>
                    <label for="timepicker-start">Pocetak termina:</label>
                    <b-form-timepicker
                        v-model="pregledStart"
                        id="timepicker-start"
                        placeholder="Izaberite vreme"
                        locale="sr-latn"
                        start="08:00"
                        end="20:00"
                    />
                  </b-col>
                  <b-col>
                    <label for="timepicker-end">Kraj termina:</label>
                    <b-form-timepicker
                        v-model="pregledEnd"
                        id="timepicker-end"
                        placeholder="Izaberite vreme"
                        locale="sr-latn"
                        start="08:00"
                        end="20:00"
                    />
                  </b-col>
                </b-row>
              </b-container>
            </template>
            <template v-if="optionPicked=='TERM'">
              <label for="pregled-datepicker">Datum termina:</label>
              <b-form-datepicker
                  :min="today"
                  required="true"
                  placeholder="Izaberite datum"
                  locale="sr-latn"
                  id="pregled-datepicker"
                  v-model="datumTermina"
                  class="mb-2"
                  @input="datumUnesen"
              />
              <label for="izbor_termina">Vreme termina:</label>
              <b-form-select id="izbor_termina" v-model="selectedTerm" :options="terms"></b-form-select>
            </template>
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
            if (this.rola == "FARMACEUT") app.$router.push("/home-farmaceut/calendar-view")
            else if (this.rola == "DERMATOLOG") app.$router.push("/home-dermatolog/calendar-view")
            else {
                localStorage.clear()
                app.$router.push("home")
            }
            localStorage.removeItem("pregled")
        },
        datumUnesen: function (){
            let endDate = new Date(this.datumTermina + " 00:00")
            let startDate = new Date(this.datumTermina + " 00:00")
            axios
                .get("pregledi/getTerminiInDateRangeByDermatolog", {
                    params: {
                        'start': startDate,
                        'end': new Date(endDate.setDate(endDate.getDate() + 1)),
                        'cookie': this.cookie,
                    }
                })
                .then(response => {
                    this.terms = [this.nullTerm]
                    for (const term of response.data){
                        let startDate = new Date(term.start + ".000Z")
                        let endDate = new Date(term.end+ ".000Z")
                        this.terms.push({
                            value: term.id,
                            text: moment(String(startDate)).format("HH:mm") + ' - ' +
                                moment(String(endDate)).format("HH:mm"),
                        })
                    }
                })
                .catch()
        },
        zakazi_pregled: function () {
            if (this.optionPicked == 'DATE')
                axios
                    .post("pregledi/createZakazanPregled", null, {
                        params: {
                            'start': new Date(this.datumPregleda + " " + this.pregledStart),
                            'end': new Date(this.datumPregleda + " " + this.pregledEnd),
                            'cookie': this.cookie,
                            'idPacijenta': this.pregled.pacijent.id,
                            'idApoteke': this.pregled.apoteka.id
                        }
                    })
                    .then()
                    .catch()
            else if (this.optionPicked == 'TERM')
                axios
                    .post("pregledi/updateZakazanPregled", null, {
                        params: {
                            'cookie': this.cookie,
                            'idPacijenta': this.pregled.pacijent.id,
                            'idTermina': this.selectedTerm
                        }
                    })
                    .then()
                    .catch()
        }
    }
});
