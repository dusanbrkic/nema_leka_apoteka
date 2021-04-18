Vue.component("FarmaceutMain", {
    data: function () {
        return {}
    },
    mounted() {
    },
    template: `
      <div>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/farmaceut_main.css" type="text/css">
      <b-container id="page_content">
        <b-row>
          <b-col>
            <b-card
                title="Pregledani pacijenti"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih pregledanih pacijenata
              </b-card-text>

              <b-button href="#/home-farmaceut/pregledani-pacijenti" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Zakazi savetovanje"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Idi na stranicu za zakazivanje savetovanja
              </b-card-text>

              <b-button href="#/zakazivanje" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Godisnji odmor"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Idi na stranicu za kreiranje zahteva za godisnji odmor
              </b-card-text>
              <b-button href="#/godisnji-odmor" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Zapocni savetovanje"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Zapocni savetovanje koje upravo treba da se odigra
              </b-card-text>
              <b-button href="#/savetovanje-forma" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Radni kalendar"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi radni kalendar
              </b-card-text>
              <b-button href="#/home-farmaceut/calendar-view" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Izdaj lek"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Idi na stranicu za izdavanje leka
              </b-card-text>
              <b-button href="#/home-farmaceut/izdaj-lek" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
      </div>
    `,
    methods: {},
});
