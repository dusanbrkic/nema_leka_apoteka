Vue.component("CalendarView", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            invalidCookie: false,
            calendar: null,
            selectedEvent: {
                id: null,
                title: null,
                start: new Date(),
                end: new Date(),
                cena: null,
                apoteka: {naziv: null, adresa: null},
                pacijent: {ime: null, prezime: null},
                preporuceniLekovi: [],
                dijagnoza: null,
                pregledZakazan: null,
                pregledObavljen: null,
                eventType: null
            }
        }
    },

    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
        let calendarEl = document.getElementById('calendar');
        let that = this
        let calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            initialDate: new Date(),
            customButtons: {
                homeButton: {
                    text: 'home',
                    click: function () {
                        if (that.rola == "FARMACEUT") app.$router.push("/home-farmaceut")
                        else if (that.rola == "DERMATOLOG") app.$router.push("/home-dermatolog")
                        else {
                            localStorage.clear()
                            app.$router.push("home")
                        }
                    }
                }
            },
            headerToolbar: {
                left: 'homeButton prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            themeSystem: 'bootstrap4',
            events: that.loadData,
            nowIndicator: true,
            expandRows: true,
            slotMinTime: '08:00',
            slotMaxTime: '20:00',
            navLinks: true,
            dayMaxEvents: true,
            eventClick: that.eventSelected,
            locale: 'sr-latn',
            buttonText: {
                today: 'danas',
                month: 'mesec',
                week: 'nedelja',
                day: 'dan',
                list: 'lista'
            },
            weekText: 'sed',
            allDayText: 'ceo dan',
            moreLinkText: function (n) {
                return '+ još ' + n
            },
            noEventsText: 'nеma događaja za prikaz',
            firstDay: 1,
            week: {
                dow: 1, // Monday is the first day of the week.
                doy: 7, // The week that contains Jan 1st is the first week of the year.
            },
        });
        calendar.render();
        this.calendar = calendar
    },
    template:
        `
          <div>
          <link rel="stylesheet" href="css/dermatolog-farmaceut/calendar_view.css" type="text/css">
          <b-alert style="text-align: center;" v-model="invalidCookie" variant="danger">Nisi ulogovan kao
            dermatolog/farmaceut!
          </b-alert>
          <b-modal id="eventModal" centered  title="Detalji">
            <b-container>
              <b-row>
                <b-col><strong>Pocetak:</strong></b-col>
                <b-col>{{ moment(String(selectedEvent.start)).format("DD/MM/YYYY HH:mm") }}</b-col>
              </b-row>
              <b-row>
                <b-col><strong>Kraj:</strong></b-col>
                <b-col>{{ moment(String(selectedEvent.end)).format("DD/MM/YYYY HH:mm") }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.pregledZakazan)">
                <b-col><strong>Ime pacijenta:</strong></b-col>
                <b-col>{{ selectedEvent.pacijent.ime }}</b-col>
              </b-row>
              <b-row v-if="selectedEvent.pregledZakazan">
                <b-col><strong>Prezime pacijenta:</strong></b-col>
                <b-col>{{ selectedEvent.pacijent.prezime }}</b-col>
              </b-row>
              <b-row>
                <b-col><strong>Ime Apoteke:</strong></b-col>
                <b-col>{{ selectedEvent.apoteka.naziv }}</b-col>
              </b-row>
              <b-row>
                <b-col><strong>Adresa apoteke:</strong></b-col>
                <b-col>{{ selectedEvent.apoteka.adresa }}</b-col>
              </b-row>
              <b-row v-if="selectedEvent.pregledObavljen">
                <b-col><strong>Dijagnoza:</strong></b-col>
                <b-col>{{ selectedEvent.dijagnoza }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.pregledZakazan)">
                <b-col><strong>Cena:</strong></b-col>
                <b-col>{{ selectedEvent.cena }}</b-col>
              </b-row>
              <b-row v-if="selectedEvent.pregledObavljen">
                <b-col><strong>Preporuceni lekovi:</strong></b-col>
                <b-col>
                  <b-row v-for="pregledLek in selectedEvent.preporuceniLekovi">
                    - {{ pregledLek.lek.naziv }} - {{ pregledLek.trajanjeTerapije }} dana
                  </b-row>
                  <b-row v-if="selectedEvent.preporuceniLekovi.length==0">Nema preporucenih lekova</b-row>
                </b-col>
              </b-row>
              <b-row v-if="!selectedEvent.pregledZakazan">
                <br>
                <p>Ovo je dodeljen termin od strane apoteke. Mozete zakazati pregled u ovom terminu.</p>
              </b-row>
              <b-row v-if="selectedEvent.pacijentSeNijePojavio">
                <b-col style="text-align: center;"><strong>Pacijent se nije pojavio!</strong></b-col>
              </b-row>
            </b-container>
            <template #modal-footer="{ ok }">
              <b-button
                  @click="ok()"
                  v-if="selectedEvent.pregledZakazan && !selectedEvent.pregledObavljen"
                  v-on:click="pacijent_pobegao"
                  variant="danger">
                Pacijent se nije pojavio
              </b-button>
              <b-button variant="success" @click="ok()"
                        v-if="(selectedEvent.pregledObavljen || !selectedEvent.pregledZakazan)">
                OK
              </b-button>
              <b-button variant="success" @click="ok()" v-on:click="zapocni_pregled"
                        v-if="selectedEvent.pregledZakazan && !selectedEvent.pregledObavljen">
                <template v-if="rola=='DERMATOLOG' && selectedEvent.pregledZakazan && !selectedEvent.pregledObavljen">
                  Zapocni pregled
                </template>
                <template v-if="rola=='FARMACEUT' && !selectedEvent.pregledObavljen">
                  Zapocni savetovanje
                </template>
              </b-button>
            </template>
          </b-modal>
          <b-card id="outline">
            <div id="calendar"/>
          </b-card>
          </div>

        `
    ,
    methods: {
        pacijent_pobegao: async function () {
            await axios
                .post("pregledi/updatePregledBezPacijenta", null, {params:{
                    'pregledId': this.selectedEvent.id,
                    'cookie': this.cookie,
                    'pacijentId': this.selectedEvent.pacijent.id
                }})
            this.calendar.refetchEvents()
        },

        zapocni_pregled: function () {
            let pregled = {
                id: this.selectedEvent.id,
                start: this.selectedEvent.start,
                end: this.selectedEvent.end,
                apoteka: this.selectedEvent.apoteka,
                pacijent: this.selectedEvent.pacijent,
                preporuceniLekovi: this.selectedEvent.preporuceniLekovi,
                dijagnoza: this.selectedEvent.dijagnoza,
                pregledZakazan: this.selectedEvent.pregledZakazan,
                pregledObavljen: this.selectedEvent.pregledObavljen,
            }
            localStorage.setItem("pregled", JSON.stringify(pregled))
            app.$router.push("/pregled-forma")
        },

        loadData: async function (fetchInfo, successCallback, failureCallback) {
            let retVal = []
            await axios
                .get("pregledi/getPreglediByZdravstveniRadnik", {
                    params: {
                        "cookie": this.cookie,
                        "start": fetchInfo.start,
                        "end": fetchInfo.end
                    }
                })
                .then(response => {
                    let events = response.data
                    let rola = this.rola
                    for (let event of events) {
                        event.eventType = "PREGLED"
                        retVal.push({
                            title: (() => {
                                if (event.pregledZakazan) {
                                    if (rola == "FARMACEUT")
                                        return "savetovanje"
                                    if (rola == "DERMATOLOG") {
                                        return "pregled"
                                    }
                                } else {
                                    return "dodeljeni termin"
                                }
                            })(),
                            start: new Date(event.start + ".000Z"),
                            end: new Date(event.end + ".000Z"),
                            event: event,
                            color: (() => {
                                if (event.pacijentSeNijePojavio)
                                    return 'purple'
                                if (event.pregledZakazan) {
                                    if (event.pregledObavljen)
                                        return "gray"
                                    else if (event.start < new Date())
                                        return "red"
                                } else {
                                    if (!event.pregledZakazan)
                                        return "green"
                                }
                            })()
                        })
                    }
                })
                .catch(error => {
                    if (error.request.status == 404) {
                        this.invalidCookie = true
                    }
                })
            await axios
                .get("zdravstveniradnik/fetchOdsustvaInDateRange", {
                    params: {
                        "cookie": this.cookie,
                        "start": fetchInfo.start,
                        "end": fetchInfo.end
                    }
                })
                .then(response => {
                    let events = response.data
                    for (let event of events) {
                        event.eventType = "ODMOR"
                        retVal.push({
                            allDay: true,
                            title: "odmor",
                            start: new Date(event.pocetak + ".000Z"),
                            end: new Date(event.kraj + ".000Z"),
                            event: event,
                            color: (() => {
                                if (new Date() > new Date(event.end)) {
                                    return "gray"
                                }
                            })()
                        })
                    }
                })
            return retVal
        },
        eventSelected: function (info) {
            let event = info.event.extendedProps.event
            this.selectedEvent.eventType = event.eventType
            if (event.eventType == "PREGLED") {
                this.selectedEvent.id = event.id
                this.selectedEvent.title = event.pacijent.ime + " " + event.pacijent.prezime
                this.selectedEvent.start = new Date(event.start + ".000Z")
                this.selectedEvent.cena = event.cena
                this.selectedEvent.end = new Date(event.end + ".000Z")
                this.selectedEvent.apoteka = event.apoteka
                this.selectedEvent.pacijent = event.pacijent
                this.selectedEvent.preporuceniLekovi = event.preporuceniLekovi
                this.selectedEvent.dijagnoza = event.dijagnoza
                this.selectedEvent.pregledZakazan = event.pregledZakazan
                this.selectedEvent.pregledObavljen = event.pregledObavljen
                this.selectedEvent.pacijentSeNijePojavio = event.pacijentSeNijePojavio
                this.$bvModal.show('eventModal')
            }
        }
    }
});
