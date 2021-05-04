Vue.component("PregledForma", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            kolicinaLeka: '',
            izabraniLek: '',
            trajanjeTerapijeLeka: '',
            optionPicked: "DATE",
            datumPregleda: '',
            datumTermina: '',
            today: new Date(),
            pregledStart: '',
            pregledEnd: '',
            pretragaLekova: '',
            totalLekovi: '',
            pageLekova: 0,
            pageSizeLekova: 6,
            selectedTerm: null,
            nullTerm: {value: null, text: 'Izaberite termin:'},
            terms: [
                {value: null, text: 'Izaberite termin:'}
            ],
            lekovi: [],
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
          <b-modal id="odabirKolicineModal" title="Odredi terapiju">
            <label for="unos_kolicine">Kolicina za rezervaciju:</label>
            <b-form-input id="unos_kolicine" type="number" v-model="kolicinaLeka">
            </b-form-input>
            <label for="unos_kolicine">Trajanje terapije u danima:</label>
            <b-form-input id="unos_trajanja" type="number" v-model="trajanjeTerapijeLeka">
            </b-form-input>
            <template #modal-footer="{ ok }">
              <b-button @click="ok()">
                Otkazi
              </b-button>
              <b-button v-on:click="dodaj_lek" variant="success" @click="ok()">
                Dodaj lek
              </b-button>
            </template>
          </b-modal>
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
          <b-modal size="lg" id="dodajLekModal" title="Preporuci lek pacijentu">
            <b-container>
              <b-row>
                <b-col>
                  <b-form-input v-model="pretragaLekova" type="search"/>
                </b-col>
                <b-col>
                  <b-button v-on:click="loadLekovi">Pretrazi</b-button>
                </b-col>
              </b-row>
            </b-container>
            <b-container>
              <b-row v-for="chungus in lekovi">
                <b-col v-for="lek in chungus">
                  <b-card
                      style="margin: 10px auto; max-width: 225px"
                      :header="lek.naziv"
                      class="text-center"
                  >
                    <b-card-text>
                      {{ lek.tip }} <br> {{ lek.sastav }}
                    </b-card-text>
                    <b-button variant="primary" v-on:click="izaberi_lek(lek)">Izaberi</b-button>
                  </b-card>
                </b-col>
              </b-row>
            </b-container>
            <b-pagination
                style="margin: auto; width: 50%;"
                v-model="pageLekova"
                :total-rows="totalLekovi"
                :per-page="pageSizeLekova"
                @change="handlePageChange"
            ></b-pagination>
            <template #modal-footer="{ ok }">
              <b-button @click="ok()">
                Otkazi
              </b-button>
              <b-button v-on:click="zakazi_pregled" variant="success" @click="ok()">
                Dodaj lek
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
                    <b-button v-on:click="dodavanje_forma" variant="primary" style="margin-left:10px">Dodaj lek
                    </b-button>
                  </b-col>
                </b-row>
                <template v-for="lek in pregled.preporuceniLekovi">
                  <b-row>
                    <b-col>
                      - {{ lek.lek.naziv }} - {{lek.trajanjeTerapije}} dana
                    </b-col>
                    <b-col>
                      <b-button style="margin: 2px auto;" v-on:click="returnToHome" variant="danger">Izbaci lek</b-button>
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
        izaberi_lek: function (lek) {
            this.izabraniLek = lek
            this.$bvModal.show('odabirKolicineModal')
        },
        dodaj_lek: function (){
            this.pregled.preporuceniLekovi.push({
                lek: this.izabraniLek,
                kolicina: this.kolicinaLeka,
                trajanjeTerapije: this.trajanjeTerapijeLeka
            })
        },
        dodavanje_forma: function () {
            this.pageLekova = 0
            this.loadLekovi()
            this.$bvModal.show('dodajLekModal')
        },
        handlePageChange(value) {
            this.pageLekova = value - 1
            this.loadLekovi()
        },
        loadLekovi: function () {
            axios
                .get("/lekovi/getAllByPacijentNotAllergic", {
                    params: {
                        'idPacijenta': this.pregled.pacijent.id,
                        'cookie': this.cookie,
                        'page': this.pageLekova,
                        'pageSize': this.pageSizeLekova,
                        'pretraga': this.pretragaLekova,
                    }
                })
                .then(response => {
                    this.lekovi = []
                    this.totalLekovi = response.data['totalElements']
                    for (let i = 0; i < 2; i = i + 1) {
                        this.lekovi.push([])
                        for (let j = 0; j < 3; j = j + 1) {
                            let item = response.data.content[j + i * 3]
                            if (typeof item === 'undefined')
                                return
                            this.lekovi[i].push(response.data.content[j + i * 3])
                        }
                    }

                })
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
        datumUnesen: function () {
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
                    for (const term of response.data) {
                        let startDate = new Date(term.start + ".000Z")
                        let endDate = new Date(term.end + ".000Z")
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
