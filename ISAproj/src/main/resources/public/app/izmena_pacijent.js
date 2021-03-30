Vue.component("IzmenaPacijent", {
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
      }
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadUserInfo();
  },
  template: `
        <div>

        <!--
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
	      -->

        <div class="container">
          <h2>Moj Nalog</h2>
          <form @submit.prevent="saveUserInfo">

            <div class="form-group">
              <label for="username">Username:</label>
              <input type="text" class="form-control" id="username" v-model="this.korisnik.username">
            </div>

            <div class="form-group">
              <label for="password">Password:</label>
              <input type="text" class="form-control" id="password" v-model="this.korisnik.password">
            </div>

            <div class="form-group">
              <label for="ime">Ime:</label>
              <input type="text" class="form-control" id="ime" v-model="this.korisnik.ime">
            </div>

            <div class="form-group">
              <label for="prezime">Prezime:</label>
              <input type="text" class="form-control" id="prezime" v-model="this.korisnik.prezime">
            </div>

            <div class="form-group">
              <label for="datumRodjenja">Datum rođenja:</label>
              <input type="text" class="form-control" id="datumRodjenja" v-model="this.korisnik.datumRodjenja">
            </div>

            <div class="form-group">
              <label for="emailAdresa">Email:</label>
              <input type="text" class="form-control" id="datumRodjenja" v-model="this.korisnik.emailAdresa">
            </div>

            <input type="button" v-on:click="logout" value="Logout">
            <input type="submit" v-on:submit="saveUserInfo" value="Save information">

          </form>
        </div>



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
    }
  },
});
