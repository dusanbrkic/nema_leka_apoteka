Vue.component("HomeAdminApoteke", {
  data: function () {
    return {
      cookie: "",
      user: "",
      korisnik: {
        "username": "",
        "password": "",
        "ime": "",
        "prezime": "",
        "datumRodjenja": "",
        "emailAdresa": "",
      },
      apoteka:{

      }
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadUserInfo();
  },
  template: `
        <div>
	      <link rel="stylesheet" href="css/login.css" type="text/css">
	      <h1 id="h1-login">Informacije</h1>
	      <form @submit.prevent="saveUserInfo">
	        <table id="login-table">
	          <tr>
	            <td>Username:</td>
	            <td><input type="text" v-model="this.korisnik.username" readOnly></td>
	          </tr>
	          <tr>
	            <td>Password:</td>
	            <td><input type="password" v-model="this.korisnik.password" readOnly></td>
	          </tr>
            <tr>
            <td>Ime:</td>
            <td><input type="ime" v-model="this.korisnik.ime" readOnly></td>
          </tr>
          <tr>
            <td>Prezime:</td>
            <td><input type="prezime" v-model="this.korisnik.prezime" readOnly></td>
          </tr>
          <tr>
            <td>Datum rodjenja:</td>
            <td><input type="datumRodjenja" v-model="this.korisnik.datumRodjenja" readOnly></td>
          </tr>
          <tr>
            <td>Email:</td>
            <td><input type="emailAdresa" v-model="this.korisnik.emailAdresa" readOnly></td>
          </tr>
          <tr>
            <td id="logout-button">
              <input type="button" v-on:click="logout" value="Logout"></td>
            <td id="save-button">
              <input type="submit" v-on:submit="saveUserInfo" value="Save information"></td>
          </tr>
	        </table>
	      </form>
	      <input type="button" v-on:click="getApotekaInfo" value="Informacije o apoteci"></td>
        
        <table id="apoteka-table">
          <tr>
          <td>Naziv apoteke:</td>
          <td><input type="text" v-model="this.apoteka.naziv" readOnly></td>
          </tr>
          <tr>
	          <td>Adresa:</td>
	          <td><input type="text" v-model="this.apoteka.adresa" readOnly></td>
          </tr>
          <tr>
	          <td>Prosecna ocena:</td>
	          <td><input type="text" v-model="this.apoteka.prosecnaOcena" readOnly></td>
          </tr>
          <tr>
	          <td>Opis:</td>
	          <td><input type="text" v-model="this.apoteka.opis" readOnly></td>
          </tr>
          <tr>
	          <td>Farmaceuti:</td>
	          <td><input type="text" v-model="this.apoteka.farmaceuti" readOnly></td>
          </tr>
        </table>

        </div>
    `,
  methods: {
    loadUserInfo: async function () {
      let user = {
        params: {
          username: this.cookie.split("-")[0],
          password: this.cookie.split("-")[1],
        },
      };
      await axios.get("korisnici/infoUser", user).then((response) => {
        this.korisnik = response.data
        console.log(response)
      });
    },
    logout: function() {
      localStorage.clear();
			app.$router.push("/")
		},
    saveUserInfo: async function(){
      this.korisnik.ime = this.korisnik.ime + "1"
      await axios.put("korisnici/updateUser",this.korisnik
      ).then((response) => {
        this.korisnik = response.data
      });
    },
    getApotekaInfo: async function(){
      let user = {
        params: {
          username: this.korisnik.username,
          password: this.korisnik.password
        },
      };
      
      await axios.get("apoteke/getByAdmin",user)
      .then((response => {
        console.log(response.data)
        this.apoteka = response.data}))
    }
  },
});
