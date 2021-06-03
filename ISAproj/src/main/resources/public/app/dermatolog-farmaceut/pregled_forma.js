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
            pageLekova: 1,
            pageSizeLekova: 6,
            pagepreporucenihLekova: 1,
            pageSizepreporucenihLekova: 6,
            totalpreporuceniLekovi: 0,
            preporuceniLekoviPrikaz: [],
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
            },
            showPraznaPoljaAlert: false,
            lekNijeDostupan: false,
            odabirLekaTitle: 'Preporuci lek pacijentu:',
            nedostupanLek: '',
            kolicinaNedostupnogLeka: '-1',
            showZakazivanjeAlert: false,
            zakazivanjeAlertReason: ''
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
          <!--modal odabira kolicine-->
          <b-modal size="sm" id="odabirKolicineModal" title="Odredi terapiju">
            <b-alert variant="danger" v-model="showPraznaPoljaAlert">Imate prazna polja!</b-alert>
            <label for="unos_kolicine">Kolicina za rezervaciju:</label>
            <b-form-input id="unos_kolicine" min="1" type="number" v-model="kolicinaLeka">
            </b-form-input>
            <label for="unos_kolicine">Trajanje terapije u danima:</label>
            <b-form-input id="unos_trajanja" min="1" type="number" v-model="trajanjeTerapijeLeka">
            </b-form-input>
            <template #modal-footer="{ ok }">
              <b-button @click="ok()">
                Otkazi
              </b-button>
              <b-button v-on:click="dodaj_lek" variant="success">
                Dodaj lek
              </b-button>
            </template>
          </b-modal>
          <!--modal zakazivanja-->
          <b-modal id="zakazivanjeModal" title="Zakazi pregled pacijentu">
            <b-alert variant="warning" v-model="showZakazivanjeAlert">
              {{zakazivanjeAlertReason}}
            </b-alert>
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
              <b-button v-on:click="zakazi_pregled" variant="success">
                Zakazi
              </b-button>
            </template>
          </b-modal>
          <!--modal odabira leka-->
          <b-modal size="lg" id="dodajLekModal" :title="odabirLekaTitle">
            <b-alert variant="warning" v-model="lekNijeDostupan">
              Lek koji ste izabrali nije dostupan, izaberite neki od zamenskih lekova.
            </b-alert>
            <b-container>
              <b-row>
                <b-col>
                  <b-form-input
                      placeholder="Pretrazi lekove..."
                      v-on:input="handleSearch"
                      v-model="pretragaLekova"
                      type="search"/>
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
                v-if="totalLekovi>0"
                pills
                align="center"
                style="margin: 10px auto;"
                v-model="pageLekova"
                :total-rows="totalLekovi"
                :per-page="pageSizeLekova"
                @change="handlePageChange"
            ></b-pagination>
            <h6 align="center" style="margin: 10px;" v-if="totalLekovi==0">Nema lekova za prikaz</h6>
            <template #modal-footer="{ ok }">
              <b-button @click="ok()" variant="primary" v-if="!lekNijeDostupan">
                OK
              </b-button>
              <b-button v-on:click="nazadNaSveLekove" v-if="lekNijeDostupan">
                Nazad na sve lekove
              </b-button>
            </template>
          </b-modal>
          <!--main view-->
          <b-card style="max-width: 700px; margin: 30px auto;" title="Upisite podatke o pregledu:">
            <b-form @submit.prevent="onSubmit">
              <b-container>
                <b-row>
                  <b-col>
                    <strong>Pacijent:</strong> {{ pregled.pacijent.ime }} {{ pregled.pacijent.prezime }}
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
                    <b-button v-on:click="dodavanje_forma" variant="primary" style="float:right;">Dodaj lekove
                    </b-button>
                  </b-col>
                </b-row>
                <label for="container2" v-if="totalpreporuceniLekovi>0">Preporuceni lekovi:</label>
                <template id="container2">
                  <b-row v-for="chungus in this.preporuceniLekoviPrikaz">
                    <b-col v-for="pregledlek in chungus">
                      <b-card
                          style="margin: 10px auto; max-width: 175px"
                          :header="pregledlek.lek.naziv"
                          class="text-center"
                      >
                        <b-card-text>
                          Terapija: {{ pregledlek.trajanjeTerapije }} dan(a) <br>
                          Kolicina: {{ pregledlek.kolicina }}
                        </b-card-text>
                        <b-button variant="danger" v-on:click="izbaci_lek(pregledlek)">Izbaci</b-button>
                      </b-card>
                    </b-col>
                  </b-row>
                </template>
                <b-row>
                  <b-col>
                    <b-pagination
                        v-if="totalpreporuceniLekovi>0"
                        pills
                        align="center"
                        style="margin: 10px auto;"
                        v-model="pagepreporucenihLekova"
                        :total-rows="totalpreporuceniLekovi"
                        :per-page="pageSizepreporucenihLekova"
                        @change="handlePageChangePreporucenihLekova"
                    ></b-pagination>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col>
                    <b-button style="float: right; margin-top: 10px;" variant="primary" v-on:click="zakazivanje_forma">
                      Zakazi dodatan pregled
                    </b-button>
                  </b-col>
                </b-row>
                <br>
                <b-row>
                  <b-col>
                    <b-button style="float:right; margin-left: 5px" variant="success" v-on:click="onSubmit">Sacuvaj</b-button>
                    <b-button style="float:right; " v-on:click="returnToCalendar">Nazad</b-button>
                  </b-col>
                </b-row>
              </b-container>
            </b-form>
          </b-card>
          </div>

        `
    ,
    methods: {
        nazadNaSveLekove: function () {
            this.lekNijeDostupan = false
            this.odabirLekaTitle = "Preporuci lek pacijentu:"
            this.pageLekova = 1
            this.loadLekovi()
        },
        izbaci_lek: function (lek) {
            this.pregled.preporuceniLekovi.splice(this.pregled.preporuceniLekovi.indexOf(lek), 1)
            if (this.pagepreporucenihLekova != 1 && ((this.pagepreporucenihLekova - 1)
                * this.pageSizepreporucenihLekova <= this.pregled.preporuceniLekovi.length))
                this.pagepreporucenihLekova = this.pagepreporucenihLekova - 1
            this.dobavi_preporucene_lekove(this.pagepreporucenihLekova);
        },
        onSubmit: async function () {
            await axios
                .put("pregledi/updatePregled", this.pregled)
                .then(response => {
                    localStorage.removeItem("pregled")
                    if (this.rola == "FARMACEUT") app.$router.push("/home-farmaceut/calendar-view")
                    else if (this.rola == "DERMATOLOG") app.$router.push("/home-dermatolog/calendar-view")
                })
                .catch(reason => {
                    alert(reason.request.response)
                })
        },
        zakazivanje_forma: function () {
            this.$bvModal.show('zakazivanjeModal')
        },
        izaberi_lek: function (lek) {
            this.izabraniLek = lek
            this.showPraznaPoljaAlert = false
            this.$bvModal.show('odabirKolicineModal')
        },
        dodaj_lek: async function () {
            if (this.kolicinaLeka == '' || this.trajanjeTerapijeLeka == '') {
                this.showPraznaPoljaAlert = true
                return
            } else this.showPraznaPoljaAlert = false

            if (!
                await axios
                    .get("lekovi/proveriDostupnostLeka", {
                        params: {
                            'sifraLeka': this.izabraniLek.sifra,
                            'idApoteke': this.pregled.apoteka.id,
                            'kolicina': this.kolicinaLeka,
                            'cookie': this.cookie,
                        }
                    })
                    .then(response => response.data)
            ) {
                this.lekNijeDostupan = true
                this.nedostupanLek = this.izabraniLek
                this.kolicinaNedostupnogLeka = this.kolicinaLeka
                this.pageLekova = 1
                this.odabirLekaTitle = "Izaberite zamenski lek:"
                this.loadLekovi()
                this.$bvModal.hide('odabirKolicineModal')
                return
            } else {
                this.lekNijeDostupan = false
                this.odabirLekaTitle = "Preporuci lek pacijentu:"
            }

            this.pregled.preporuceniLekovi.push({
                lek: this.izabraniLek,
                kolicina: this.kolicinaLeka,
                trajanjeTerapije: this.trajanjeTerapijeLeka
            })
            if (this.pageLekova != 1 && this.lekovi[0].length == 1)
                this.pageLekova = this.pageLekova - 1
            this.dobavi_preporucene_lekove(this.pagepreporucenihLekova)
            this.$bvModal.hide('odabirKolicineModal')
            this.loadLekovi()
        },
        handlePageChangePreporucenihLekova: function (value) {
            this.pagepreporucenihLekova = value
            this.dobavi_preporucene_lekove(this.pagepreporucenihLekova)
        },
        dobavi_preporucene_lekove: function (brojStranice) {
            this.totalpreporuceniLekovi = this.pregled.preporuceniLekovi.length
            this.preporuceniLekoviPrikaz = []
            let k = 0
            for (let i = (brojStranice - 1) * 2; i < (brojStranice - 1) * 2 + 2; i = i + 1) {
                this.preporuceniLekoviPrikaz.push([])
                for (let j = 0; j < 3; j = j + 1) {
                    let item = this.pregled.preporuceniLekovi[j + i * 3]
                    if (typeof item === 'undefined')
                        return
                    this.preporuceniLekoviPrikaz[k].push(item)
                }
                k = k + 1
            }
        },
        dodavanje_forma: function () {
            this.pageLekova = 1
            this.loadLekovi()
            this.$bvModal.show('dodajLekModal')
        },
        handlePageChange(value) {
            this.pageLekova = value
            this.loadLekovi()
        },
        handleSearch: function () {
            this.pageLekova = 1
            this.loadLekovi()
        },
        loadLekovi: function () {
            let vecPreporuceniSifre = []
            for (const p of this.pregled.preporuceniLekovi)
                vecPreporuceniSifre.push(p.lek.sifra)
            axios
                .post("/lekovi/getAllByPacijentNotAllergic", {
                    'idPacijenta': this.pregled.pacijent.id,
                    'cookie': this.cookie,
                    'page': this.pageLekova - 1,
                    'pageSize': this.pageSizeLekova,
                    'pretraga': this.pretragaLekova,
                    'vecPreporuceniSifre': vecPreporuceniSifre,
                    'nedostupanLek': (() => {
                        if (this.lekNijeDostupan)
                            return this.nedostupanLek.sifra
                        else
                            return null
                    })(),
                    'kolicina': this.kolicinaNedostupnogLeka,
                    'apotekaID': this.pregled.apoteka.id
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
                            this.lekovi[i].push(item)
                        }
                    }

                })
        },
        returnToCalendar: function () {
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
                    .then(response => {
                        this.showZakazivanjeAlert = false
                        this.$bvModal.hide('zakazivanjeModal')
                    })
                    .catch(reason => {
                        if(reason.request.status === 400){
                            this.showZakazivanjeAlert = true
                            this.zakazivanjeAlertReason = reason.request.response
                        }
                    })
            else if (this.optionPicked == 'TERM')
                if (this.selectedTerm === null){
                    this.showZakazivanjeAlert = true
                    this.zakazivanjeAlertReason = "Niste izabrali termin!"
                    return
                } else {
                    this.showZakazivanjeAlert = false
                    this.zakazivanjeAlertReason = ""
                }
                axios
                    .post("pregledi/updateZakazanPregled", null, {
                        params: {
                            'cookie': this.cookie,
                            'idPacijenta': this.pregled.pacijent.id,
                            'idTermina': this.selectedTerm
                        }
                    })
                    .then(response => {
                        this.showZakazivanjeAlert = false
                        this.datumUnesen()
                        this.selectedTerm = this.nullTerm
                        this.$bvModal.hide('zakazivanjeModal')
                    })
                    .catch(reason => {
                        if(reason.request.status === 400){
                            this.showZakazivanjeAlert = true
                            this.zakazivanjeAlertReason = reason.request.response
                        }
                    })
        }
    }
});
