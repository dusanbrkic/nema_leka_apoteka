Vue.component("PromenaLozinke",{
	data: function (){
		return{
            cookie: "",
			currentPass: "",
			repeatPass: "",
            samePassword : false,
			badRepeatPassword: false,
			new_password: "",
		}
	},
	mounted(){
		this.cookie = localStorage.getItem("cookie");
		this.userRole = localStorage.getItem("userRole");
	},
	template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <b-alert style="text-align: center;" dismissible>Prvi put se logujete, promenite lozinku!</b-alert>
      <b-alert style="text-align: center;" v-model="samePassword" variant="danger" dismissible>Ista lozinka!</b-alert>
	  <b-alert style="text-align: center;" v-model="samePassword" variant="danger" dismissible>Niste uneli dobro staru lozinku</b-alert>
      <div id="login-div" style="max-width: 40rem; text-align: center; margin: auto;" class="mt-5">
        <b-card title="Promena Lozinke">
          <b-form @submit.prevent="redirectUser">
            <b-form-input required type="password" v-model="currentPass" placeholder="Trenutna lozinka"/>
 			<b-form-input required type="password" v-model="repeatPass" placeholder="Ponovite lozinku"/>
            <b-form-input required type="password" v-model="new_password" placeholder="Nova lozinka"/>
            <div class="mt-2">
              <b-button variant="primary" type="submit" v-on:submit="redirectUser">Promeni lozinku</b-button>
            </div>
          </b-form>
        </b-card>
      </div>
      </div>
	`,
	methods:{
		changePass: function () {
			let info = {
				
				params: {
					"cookie": this.cookie,
					"currentPass" : this.currentPass,
					"newPass": this.new_password
				}
			}
            axios
                .put("korisnici/updatePass", {info})
                .then((response) => (this.korisnik = response.data))
				localStorage.clear()
                app.$router.push("/")
        },
		redirectUser: function(){
			if(this.currentPass == this.repeatPass){
				this.badRepeatPassword = true;
				return;
			}
            if(this.new_password === this.currentPass){
				this.samePassword = true;
				return;
			}
            this.changePass();
			let cookie = this.korisnik.username + "-" + this.korisnik.password;
			localStorage.setItem("cookie", cookie)
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

	},
});