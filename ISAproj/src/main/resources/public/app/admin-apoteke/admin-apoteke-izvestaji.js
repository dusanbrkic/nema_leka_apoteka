Vue.component("AdminIzvestaji", {
  data() {
    const { reactiveProp } = VueChartJs.mixins;
    return {
      cookie: "",
      apoteka: [],
      dermatolozi: [],
      farmaceuti: [],
      chart: "",
      label: "Broj pregleda",
      labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      dataChart: [1, 2, 3, 4, 5],
      izvestaj: "",
      izabranTip: "Odrzani pregledi",
      izabranKvartal: 0,
      izabranVP: "Mesecni",
      izabranMesec: null,
      isPrihod: false,
      isNotGodisnji: true,
      izabranaGodina: "2021",
      tipIzvestaja: ["Odrzani pregledi", "Potrosnja lekova", "Prihod apoteke"],
      vremenskiPeriod: ["Mesecni", "Kvartalni", "Godisnji"],
      kvartali: [
        { value: 0, text: "Izaberite kvartal" },
        { value: 1, text: "Prvi" },
        { value: 2, text: "Drugi" },
        { value: 3, text: "Treci" },
        { value: 4, text: "Cetvrti" },
      ],
      godine: ["2019", "2020", "2021"],
      pocetak: "2018-01-01",
      kraj: "2021-12-12",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadApoteka();
    this.loadDermatologe();
    this.loadFarmaceute();
    this.loadIzvestajPregled();
    //this.loadData();
  },
  template: `<div>
     	      <link rel="stylesheet" href="css/dermatolog-farmaceut/home_dermatolog.css" type="text/css">
      <b-navbar toggleable="lg" href="#/home-admin_apoteke" type="dark" variant="dark">
        <img src="../../res/pics/logo.png" alt="Logo">
        <b-navbar-brand href="#">Sistem Apoteka</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/home-admin_apoteke">Home</b-nav-item>
            <b-nav-item href="#/dodaj-lek-admin">Dodaj lek</b-nav-item>
            <b-nav-item href="#/pretraga-lek-admin">Pretrazi, obrisi i uredi lekove</b-nav-item>
            <b-nav-item >Izmeni podatke o apoteci</b-nav-item>
            <b-nav-item href="#/admin-apoteke-narudzbina">Naruci lekove</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dodaj-farmaceuta">Dodaj farmaceuta</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dodaj-dermatologa">Dodaj dermatologa</b-nav-item>
            <b-nav-item href="#/admin-apoteke-dermatolozi">Dermatolozi</b-nav-item>
            <b-nav-item href="#/admin-apoteke-farmaceuti">Farmaceuti</b-nav-item>
            <b-nav-item href="#/admin-apoteke-narudzbenice">Narudzbine</b-nav-item>
            <b-nav-item href="#/admin-apoteke-promocija">Promocije</b-nav-item>
            <b-nav-item href="#/admin-apoteke-odsustvo">Odsustva</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#/izmena-podataka" right>Profil</b-nav-item>
            <b-nav-item v-on:click="logout" right>Odjavi se</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
		      </b-navbar>
      <router-view/>

     <b-card style="margin:40px auto; max-width: 2000px">
      			      <div class="text-center"><h2>Izvestaji</h2></div>
          <hr>
      		<h3> Prosecna ocena Apoteke:  {{apoteka.prosecnaOcena}}<h3>
          <hr>
      		    	<label>Prosecne ocene Farmaceuta:</label>
      		    	
		<b-col>
		<br>
		</b-list-group-item>
        </b-list-group>

        <b-list-group flush style="max-height: 200px; max-width : 300 px;
	        overflow:scroll; 
	        margin-bottom: 10px;
	         overflow:scroll;
    		-webkit-overflow-scrolling: touch;" >
    	<b-list-group-item v-for="farmaceut in farmaceuti" class="list-group-item px-0">
    		<p>{{farmaceut.ime}} {{farmaceut.prezime}}  - {{farmaceut.prosecnaOcena}}</p>
    	</b-list-group-item>	
    	</b-list-group>
			</b-col>

      			<b-col>
			        <label>Prosecne ocene Dermatologa:</label>
			        <b-list-group flush style="max-height: 200px; max-width : 300 px;
				        overflow:scroll; 
				        margin-bottom: 10px;
			    		-webkit-overflow-scrolling: touch;">
			    	<b-list-group-item v-for="dermatolog in dermatolozi" class="list-group-item px-0">
			    		<p>{{dermatolog.ime}} {{dermatolog.prezime}} - {{dermatolog.prosecnaOcena}}</p>
			    	</b-list-group-item>	
			    	</b-list-group>
				</b-col>
        <hr>
  <b-row class="mb-1">
    <b-col>
      <b-form-select  class="mb-2" v-model="izabranTip" :options="tipIzvestaja" @change="handleYearChange"></b-form-select>
    </b-col>
    <b-col>
      <div v-if="isPrihod">
        <b-form-datepicker   class="mb-2" v-model="pocetak" @input="handleYearChange"></b-form-datepicker>  
      </div>
      <div v-else>
      <b-form-select class="mb-2" v-model="izabranVP" :options="vremenskiPeriod" @change="handleYearChange"></b-form-select>
      </div>
    </b-col>
    <b-col>
      <div v-if="isPrihod">
      <b-form-datepicker   class="mb-2" v-model="kraj" @input="handleYearChange"></b-form-datepicker>
      </div>

      <div v-else>
      <div v-if="isNotGodisnji">
      <b-form-select class="mb-2" v-if="checkType" v-model="izabranaGodina" :options="godine" @change="handleYearChange"></b-form-select>
      </div>
      </div>
    </b-col>

  </b-row>
<div class="app">
  <AdminApotekeLekovi :data="izvestaj" :labels = "labels" :label = "label" :options="{responsive: true, maintainAspectRatio: false}"></AdminApotekeLekovi>
 
</div>
 <!--   <bars
  :key="izvestaj"
  :data="izvestaj"
  :labelData="labels"
  :gradient="['#3a86ff', '#d6d6d6']"
  :labelSize="0.3"
  :labelRotate="45"
  :padding = "4"
  :width="1000"
  :height="500"
  :barWidth="15.2">
  </bars> -->
  </div>`,
  methods: {
    checkType: function () {
      if (izabranVP === "Godisnji") {
        return false;
      }
      return true;
    },
    refreshData: function () {},
    loadData: async function () {
      new Chart(this.$refs.chart, {
        type: "line",
        mixins: [VueChartJs.mixins.reactiveProp],
        watch: {
          chartData: function () {
            this.$data._chart.update();
          },
        },
        data: {
          labels: this.labels,
          datasets: [
            {
              label: "CASES",
              backgroundColor: "rgba(144,238,144 , 0.9 )",
              data: this.izvestaj,
            },
          ],
        },
        options: {
          scales: {
            yAxes: [{}],
          },
          responsive: true,
        },
      });
    },
    fillData() {
      this.datacollection = {
        labels: [this.getRandomInt(), this.getRandomInt()],
        datasets: [
          {
            label: "Data One",
            backgroundColor: "#f87979",
            data: [this.getRandomInt(), this.getRandomInt()],
          },
          {
            label: "Data One",
            backgroundColor: "#f87979",
            data: [this.getRandomInt(), this.getRandomInt()],
          },
        ],
      };
    },
    getRandomInt() {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5;
    },
    logout: function () {
      localStorage.clear();
      app.$router.push("/");
    },
    loadApoteka() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios.get("apoteke/getByAdmin/", info).then((response) => {
        this.apoteka = response.data;
        (this.center = [this.apoteka.latitude, this.apoteka.longitude]),
          (this.location = this.center);
        //this.fixAdresu();
      });
    },
    loadDermatologe() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
        .get("zdravstveniradnik/getAllDermatologApotekaList/", info)
        .then((response) => {
          this.dermatolozi = response.data;
          console.log(this.dermatolozi);
        });
    },
    loadFarmaceute() {
      let info = {
        params: {
          cookie: this.cookie,
        },
      };
      axios
        .get("zdravstveniradnik/getAllFarmaceutsApoteka/", info)
        .then((response) => {
          this.farmaceuti = response.data;
          console.log(this.farmaceuti);
        });
    },
    loadIzvestajLek() {
      let info = {
        params: {
          cookie: this.cookie,
          tipIzvestaja: this.izabranTip,
          vremenskiPeriod: this.izabranVP,
          godina: this.izabranaGodina,
        },
      };
      this.label = "Kolicina lekova";
      axios.get("rezervacije/lekIzvestaj/", info).then((response) => {
        console.log(response.data);
        const { key, values } = response.data;
        this.izvestaj = Object.values(response.data);
        this.labels = Object.keys(response.data);
        this.test = response.data;
        //console.log(this.labels, this.izvestaj);
      });
    },
    loadIzvestajPregled() {
      let info = {
        params: {
          cookie: this.cookie,
          tipIzvestaja: this.izabranTip,
          vremenskiPeriod: this.izabranVP,
          godina: this.izabranaGodina,
        },
      };
      this.label = "Broj pregleda";
      axios.get("pregledi/pregledIzvestaj/", info).then((response) => {
        console.log(response.data);
        const { key, values } = response.data;
        this.izvestaj = Object.values(response.data);
        this.labels = Object.keys(response.data);
        this.test = response.data;
        //console.log(this.labels, this.izvestaj);
      });
    },
    loadIzvestajPrihod() {
      let info = {
        params: {
          cookie: this.cookie,
          tipIzvestaja: this.izabranTip,
          vremenskiPeriod: this.izabranVP,
          godina: this.izabranaGodina,
          pocetak: this.pocetak + "T" + "00:00:00.000Z",
          kraj: this.kraj + "T" + "00:00:00.000Z",
        },
      };
      this.label = "Prihodi apoteke";
      axios.get("rezervacije/prihodIzvestaj/", info).then((response) => {
        console.log(response.data);
        const { key, values } = response.data;
        this.izvestaj = Object.values(response.data);
        this.labels = Object.keys(response.data);
        this.test = response.data;
        //console.log(this.labels, this.izvestaj);
      });
    },
    handleYearChange(event) {
      console.log("test");
      this.isPrihod = false;
      this.isNotGodisnji = true;
      if (this.izabranVP === "Godisnji") {
        this.isNotGodisnji = false;
      }
      if (this.izabranTip === "Potrosnja lekova") {
        this.loadIzvestajLek();
      } else if (this.izabranTip === "Odrzani pregledi") {
        this.loadIzvestajPregled();
      } else if (this.izabranTip === "Prihod apoteke") {
        this.isPrihod = true;
        this.loadIzvestajPrihod();
        console.log("asda");
      }
      //this.chart.data.datasets.data = this.izvestaj;
      //this.chart.destroy();
      //this.loadData();
    },
    changeData: function () {},
  },
});
