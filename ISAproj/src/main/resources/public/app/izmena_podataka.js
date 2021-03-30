Vue.component("IzmenaPodataka", {
  data: function () {
    return {
      cookie: "",
      korisnik: {
        "username": "",
        "password": "",
        "ime": "",
        "prezime": "",
        "datumRodjenja": "",
        "emailAdresa": "",
        "cookie":""
      }
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.loadUserInfo()
  },
  template: `
        <div>
        <div class="container">
          <h2>Moj Nalog</h2>
          <form @submit.prevent="saveUserInfo">

            <div class="form-group">
              <label for="username">Username:</label>
              <input type="text" class="form-control" id="username" v-model="korisnik.username" readonly>
            </div>

            <div class="form-group">
              <label for="ime">Ime:</label>
              <input type="text" class="form-control" id="ime" v-model="korisnik.ime">
            </div>

            <div class="form-group">
              <label for="prezime">Prezime:</label>
              <input type="text" class="form-control" id="prezime" v-model="korisnik.prezime">
            </div>

            <div class="form-group">
              <label for="datumRodjenja">Datum rođenja:</label>
              <input type="date" class="form-control" id="datumRodjenja" v-model="korisnik.datumRodjenja">
            </div>

            <div class="form-group">
              <label for="emailAdresa">Email:</label>
              <input type="text" class="form-control" id="email" v-model="korisnik.emailAdresa" readonly>
            </div>

            <input type="button" v-on:click="logout" value="Logout">
            <input type="submit" v-on:submit="saveUserInfo" value="Save information">

          </form>
        </div>



        </div>
    `,
  methods: {
    loadUserInfo: function () {
      let cookie = {
        params: {
          "cookie": this.cookie
        }
      }
      axios
          .get("korisnici/infoUser", cookie)
          .then((response) => {
            this.korisnik = response.data
          })
    },
    logout: function() {
      localStorage.clear();
			app.$router.push("/")
		},

    saveUserInfo: function(){
      axios
          .put("korisnici/updateUser",this.korisnik)
          .then((response) => (this.korisnik = response.data))
    }
  },
});
