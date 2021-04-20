Vue.component("CalendarView", {
    data: function () {
        return {
            cookie: "",
            rola: "",
            invalidCookie: false,
            calendar: null,
            selectedEvent: {
                title: null,
                start: new Date(),
                end: new Date(),
                apoteka: {naziv: null, adresa: null},
                pacijent: {ime: null, prezime: null},
                preporuceniLekovi: [],
                dijagnoza: null,
                pregledZakazan: null,
                pregledObavljen: null,
                savetovanjeObavljeno: null,
                trajanje: null,
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
                },
                zakazivanjeButton: {
                    text: (() => {
                        if (that.rola == "FARMACEUT") return 'zakazi savetovanje'
                        else if (that.rola == "DERMATOLOG") return 'zakazi pregled'
                    })(),
                    click: function () {
                        alert('Zakazivanje!');
                    }
                }
            },
            headerToolbar: {
                left: 'homeButton prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay zakazivanjeButton'
            },
            themeSystem: 'bootstrap4',
            events: [],
            nowIndicator: true,
            expandRows: true,
            slotMinTime: '08:00',
            slotMaxTime: '20:00',
            navLinks: true,
            dayMaxEvents: true,
            eventClick: that.eventSelected
        });
        calendar.render();
        this.calendar = calendar
        this.loadData()

    },
    template:
        `
          <div>
          <link rel="stylesheet" href="css/dermatolog-farmaceut/calendar_view.css" type="text/css">
          <b-alert style="text-align: center;" v-model="invalidCookie" variant="danger">Nisi ulogovan kao
            dermatolog/farmaceut!
          </b-alert>
          <b-modal id="eventModal" title="Detalji">
            <b-container>
              <b-row>
                <b-col>Pocetak:</b-col>
                <b-col>{{ moment(String(selectedEvent.start)).format("DD/MM/YYYY HH:mm") }}</b-col>
              </b-row>
              <b-row>
                <b-col>Kraj:</b-col>
                <b-col>{{ moment(String(selectedEvent.end)).format("DD/MM/YYYY HH:mm") }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.eventType=='PREGLED' && selectedEvent.pregledObavljen) ||
                 (selectedEvent.eventType=='SAVETOVANJE' && selectedEvent.savetovanjeObavljeno)">
                <b-col>Trajanje:</b-col>
                <b-col>{{ moment(String(new Date(selectedEvent.trajanje))).format("mm") }} min</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.eventType=='PREGLED' && selectedEvent.pregledZakazan) ||
                 (selectedEvent.eventType=='SAVETOVANJE')">
                <b-col>Ime pacijenta:</b-col>
                <b-col>{{ selectedEvent.pacijent.ime }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.eventType=='PREGLED' && selectedEvent.pregledZakazan) ||
                 (selectedEvent.eventType=='SAVETOVANJE')">
                <b-col>Prezime pacijenta:</b-col>
                <b-col>{{ selectedEvent.pacijent.prezime }}</b-col>
              </b-row>
              <b-row>
                <b-col>Ime Apoteke:</b-col>
                <b-col>{{ selectedEvent.apoteka.naziv }}</b-col>
              </b-row>
              <b-row>
                <b-col>Adresa apoteke:</b-col>
                <b-col>{{ selectedEvent.apoteka.adresa }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.eventType=='PREGLED' && selectedEvent.pregledObavljen) ||
                 (selectedEvent.eventType=='SAVETOVANJE' && selectedEvent.savetovanjeObavljeno)">
                <b-col>Dijagnoza:</b-col>
                <b-col>{{ selectedEvent.dijagnoza }}</b-col>
              </b-row>
              <b-row v-if="(selectedEvent.eventType=='PREGLED' && selectedEvent.pregledObavljen) ||
                 (selectedEvent.eventType=='SAVETOVANJE' && selectedEvent.savetovanjeObavljeno)">
                <b-col>Preporuceni lekovi:</b-col>
                <b-col>
                  <b-row v-for="lek in selectedEvent.preporuceniLekovi">- {{ lek.naziv }}</b-row>
                </b-col>
              </b-row>
              <b-row v-if="selectedEvent.eventType=='PREGLED' && !selectedEvent.pregledZakazan">
                <br>
                <p>Ovo je dodeljen termin od strane apoteke. Mozete zakazati pregled u ovom terminu.</p>
              </b-row>
            </b-container>
            <template #modal-footer="{ ok }">
              <b-button variant="success" @click="ok()">
                <template 
                    v-if="(selectedEvent.eventType=='PREGLED' && (selectedEvent.pregledObavljen || !selectedEvent.pregledZakazan)) 
                          || (selectedEvent.eventType=='SAVETOVANJE' && selectedEvent.savetovanjeObavljeno)">
                  OK
                </template>
                <template 
                    v-if="selectedEvent.eventType=='PREGLED' && selectedEvent.pregledZakazan && !selectedEvent.pregledObavljen">
                  Zapocni pregled
                </template>
                <template
                    v-if="selectedEvent.eventType=='SAVETOVANJE' && !selectedEvent.savetovanjeObavljeno">
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
        loadData: function () {
            if (this.rola == "FARMACEUT") this.loadSavetovanja()
            else if (this.rola == "DERMATOLOG") this.loadPregledi()
        },
        loadPregledi: function () {
            axios
                .get("pregledi/getPreglediByDermatolog", {params: {"cookie": this.cookie}})
                .then(response => {
                    let events = response.data['content']
                    for (let event of events) {
                        event.eventType = "PREGLED"
                        this.calendar.addEvent({
                            title: "Pregled: " + event.pacijent.ime + " " + event.pacijent.prezime,
                            start: new Date(event.start),
                            end: new Date(event.end),
                            event: event,
                            color: (() => {
                                if (new Date() > new Date(event.end)) {
                                    if (event.pregledObavljen)
                                        return "gray"
                                    else
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
        },
        loadSavetovanja: function () {
            axios
                .get("savetovanja/getSavetovanjaByFarmaceut", {params: {"cookie": this.cookie}})
                .then(response => {
                    let events = response.data['content']
                    for (let event of events) {
                        event.eventType = "SAVETOVANJE"
                        this.calendar.addEvent({
                            title: "Savetovanje: " + event.pacijent.ime + " " + event.pacijent.prezime,
                            start: new Date(event.start),
                            end: new Date(event.end),
                            event: event,
                            color: (() => {
                                if (new Date() > new Date(event.end)) {
                                    if (event.savetovanjeObavljeno)
                                        return "gray"
                                    else
                                        return "red"
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
        },
        eventSelected: function (info) {
            let event = info.event.extendedProps.event
            this.selectedEvent.eventType = event.eventType
            if (event.eventType == "SAVETOVANJE" || event.eventType == "PREGLED") {
                this.selectedEvent.title = event.pacijent.ime + " " + event.pacijent.prezime
                this.selectedEvent.start = event.start
                this.selectedEvent.end = event.end
                this.selectedEvent.apoteka = event.apoteka
                this.selectedEvent.pacijent = event.pacijent
                this.selectedEvent.preporuceniLekovi = event.preporuceniLekovi
                this.selectedEvent.dijagnoza = event.dijagnoza
                if (event.eventType == "PREGLED") {
                    this.selectedEvent.pregledZakazan = event.pregledZakazan
                    this.selectedEvent.pregledObavljen = event.pregledObavljen
                } else if (event.eventType == "SAVETOVANJE") {
                    this.selectedEvent.savetovanjeObavljeno = event.savetovanjeObavljeno
                }
                this.selectedEvent.trajanje = event.trajanje
            }
            this.$bvModal.show('eventModal')
        }
    }
});
