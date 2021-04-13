Vue.component("CalendarView", {
    data: function () {
        return {
            cookie: "dusan-dusan",
            rola: "DERMATOLOG",

        }
    },

    mounted() {
        // this.cookie = localStorage.getItem("cookie")
        // this.rola = localStorage.getItem("userRole")
        this.loadData()
        window.onload = function () {
            let calendarEl = document.getElementById('calendar');

            let calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                initialDate: new Date(),
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                },

                events: []
            });
            calendar.render();
        }
    },
    template:
        `
          <div>
          <link rel="stylesheet" href="css/calendar_view.css" type="text/css">
          <b-card id="outline">
            <b-container>
              <b-row>
                <b-col>
                  <b-button>Back</b-button>
                </b-col>
                <b-col></b-col>
                <b-col>
                  <b-button style="float: right" variant="success">Novi pregled</b-button>
                </b-col>
              </b-row>
            </b-container>
            <div id="calendar"/>
          </b-card>
          </div>

        `
    ,
    methods: {
        loadData: function () {
            if (this.rola=="FARMACEUT") this.loadSavetovanja()
            else if (this.rola=="DERMATOLOG") this.loadPregledi()
        },
        loadPregledi: function () {
            axios
                .get("pregledi/getPreglediByDermatolog", {params: {"cookie": this.cookie}})
                .then(response => {
                    console.log(response.data)
                })
        },
        loadSavetovanja: function () {

        },
    }
});
