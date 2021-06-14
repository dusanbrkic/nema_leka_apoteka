Vue.component("HomeMain", {
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
                title="Apoteke"
                img-src="https://assets.siccode.com/i-s-b/sic-code-5912-drug-stores-proprietary-stores.jpg"
                img-alt="Image"
                img-top
                tag="article"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih apoteka u sistemu
              </b-card-text>
              
              <a href="#/apoteke" class="stretched-link"></a>

            </b-card>
          </b-col>
          
          <b-col>
            <b-card 
                title="Lekovi"
                img-src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg"
                img-alt="Image"
                img-top
                tag="article"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih lekova u sistemu
              </b-card-text>
              
              <a href="#/lekovi" class="stretched-link"></a>

            </b-card>
          </b-col>


        </b-row>
      </b-container>

      </div>
    `,
    methods: {},
});
