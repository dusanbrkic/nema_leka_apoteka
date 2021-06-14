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
                align="center"
                title="Pregledani pacijenti"
                img-src="res/pics/patient.jpg"
                img-height="200px"
                img-alt="Image"
                img-top
                tag="article"
                style="width: 22rem; height: 21rem; margin: 5px auto"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih pregledanih pacijenata
              </b-card-text>

              <a href="#/home-dermatolog/pregledani-pacijenti" class="stretched-link"></a>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                align="center"
                title="Godisnji odmor"
                img-src="res/pics/beach.jpg"
                img-height="200px"
                img-alt="Image"
                img-top
                tag="article"
                style="width: 22rem; height: 21rem; margin: 5px auto"
                class="mb-2"
            >
              <b-card-text>
                Idi na stranicu za kreiranje zahteva za godisnji odmor
              </b-card-text>
              <a href="#/home-dermatolog/odsustvo-forma" class="stretched-link"></a>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                align="center"
                title="Zapocni pregled"
                img-src="res/pics/pregled.jpg"
                img-height="200px"
                img-alt="Image"
                img-top
                tag="article"
                style="width: 22rem; height: 21rem; margin: 5px auto"
                class="mb-2"
            >
              <b-card-text>
                Zapocni pregled koji upravo treba da se odigra
              </b-card-text>
              <a v-on:click="app.$emit('zakazivanjeChosen')" style="cursor: pointer;" class="stretched-link"></a>
            </b-card>
          </b-col>
          <b-col>
            <b-card
                align="center"
                title="Radni kalendar"
                img-src="res/pics/kalendar.jpg"
                img-height="200px"
                img-alt="Image"
                img-top
                tag="article"
                style="width: 22rem; height: 21rem; margin: 5px auto"
                class="mb-2"
            >
              <b-card-text>
                Prikazi radni kalendar
              </b-card-text>
              <a href="#/home-dermatolog/calendar-view" class="stretched-link"></a>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
      </div>
    `,
    methods: {},
});
