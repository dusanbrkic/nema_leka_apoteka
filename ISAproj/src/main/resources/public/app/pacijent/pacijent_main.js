Vue.component("PacijentMain", {
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
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih apoteka u sistemu
              </b-card-text>
              
              <a href="#/home-pacijent/apoteke" class="stretched-link"></a>

            </b-card>
          </b-col>
          
          <b-col>
            <b-card 
                title="Lekovi"
                img-src="https://bbj.hu/uploads/banners/201405/pills_661126_20140520091109346.jpg"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi listu svih lekova u sistemu
              </b-card-text>
              
              <a href="#/home-pacijent/lekovi" class="stretched-link"></a>

            </b-card>
          </b-col>

          <b-col>
            <b-card 
                title="Posete dermatologu"
                img-src="https://mediniz-images-2018-100.s3.ap-south-1.amazonaws.com/post-images/dermatologist_1556130488.jpg"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi i zakazivanje poseta kod dermatologa
              </b-card-text>
              
              <!-- <a href="#/home-pacijent/pregledi" class="stretched-link"></a> -->

            </b-card>
          </b-col>
          
          
          <b-col>
            <b-card 
                title="Savetovanje kod farmaceuta"
                img-src="https://editiadedimineata.ro/wp-content/uploads/2018/01/farmacisti.jpg"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Prikazi i zakazivanje savetovanja kod farmaceuta
              </b-card-text>
             
             

            </b-card>
          </b-col>
          
          
           <b-col>
            <b-card 
                title="Rezervisani lekovi"
                img-src="https://radiotrebinje.com/rt/wp-content/uploads/2019/12/apoteka-radnica-990x659.jpg"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2"
            >
              <b-card-text>
                Moji rezervisani lekovi
              </b-card-text>
             
             <a href="#/home-pacijent/narudzbenice" class="stretched-link"></a>

            </b-card>
          <b-col>
          
          
          
          
        </b-row>
        
        
        
      </b-container>

      </div>
    `,
    methods: {},
});
