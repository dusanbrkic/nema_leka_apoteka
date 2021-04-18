Vue.component("DermatologMain", {
    data: function () {
        return {}
    },
    mounted() {
    },
    template: `
      <div>
      <link rel="stylesheet" href="css/dermatolog-farmaceut/dermatolog_main.css" type="text/css">
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

              <b-button href="#/home-dermatolog/pregledani-pacijenti" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                title="Zakazi pregled"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Idi na stranicu za zakazivanje pregleda
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
                title="Zapocni pregled"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Zapocni pregled koji upravo treba da se odigra
              </b-card-text>
              <b-button href="#/pregled-forma" variant="primary">Otvori</b-button>
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
              <b-button href="#/home-dermatolog/calendar-view" variant="primary">Otvori</b-button>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
      </div>
    `,
    methods: {},
});
