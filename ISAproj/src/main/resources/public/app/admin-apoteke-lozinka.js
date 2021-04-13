Vue.component("LozinkaAdmin",{
	data: function (){
		return{
            cookie: "",
            korisnik: {
                "username": "",
                "password": "",
                "ime": "",
                "prezime": "",
                "datumRodjenja": "",
                "emailAdresa": "",
                "cookie": "",
                "firstLogin": ""
            },
            samePassword : false,
			new_password: "",
		}
	},
	mounted(){
		this.cookie = localStorage.getItem("cookie");
		this.userRole = localStorage.getItem("userRole");
		this.loadUserInfo()
	},
	template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <b-alert style="text-align: center;" dismissible>First time login, please change your password!</b-alert>
      <b-alert style="text-align: center;" v-model="samePassword" variant="danger" dismissible>Same Password!</b-alert>
      <div id="login-div" style="max-width: 40rem; text-align: center; margin: auto;" class="mt-5">
        <b-card title="Change Password">
          <b-form @submit.prevent="changePass">
            <b-form-input required type="password" v-model="korisnik.password" placeholder="Enter Old Password" readOnly/>
            <b-form-input required type="password" v-model="new_password" placeholder="Enter New Password"/>
            <div class="mt-2">
              <b-button variant="primary" type="submit" v-on:submit="changePass">Change Password</b-button>
            </div>
          </b-form>
        </b-card>
      </div>
      </div>
	`,
	methods:{
		changePass: function () {
			if(this.new_password === this.korisnik.password){
				this.samePassword = true;
				return;
			}
			this.korisnik.firstLogin = false;
			this.korisnik.password = this.new_password;
            axios
                .put("korisnici/updatePass", this.korisnik)
                .then((response) => (this.korisnik = response.data))
			this.redirectUser();
        },
		redirectUser: function(){
			localStorage.setItem("cookie", this.korisnik.cookie)
            if (this.userRole === "PACIJENT") {
                app.$router.push("/home-pacijent")
            } else if (this.userRole === "DERMATOLOG") {
                app.$router.push("/home-dermatolog")
            } else if (this.userRole === "FARMACEUT") {
                app.$router.push("/home-farmaceut")
            } else if (this.userRole === "ADMIN_APOTEKE") {
                app.$router.push("/home-admin_apoteke")
            } else {
                localStorage.clear()
                app.$router.push("/")
            }
			
		},
		loadUserInfo: function () {
            let cookie = {
                params: {
                    "cookie": this.cookie
                }
            }
            axios.get("korisnici/infoUser", cookie).then((response) => {
                    this.korisnik = response.data
                })
        },

	},
});