Vue.component("Login", {
    data: function () {
        return {
            username: "",
            password: "",
            cookie: "",
            userRole: "",
            wrongUsername : false,
            wrongPassword : false,
            Blocked : false,
			firstLogin: "",
			ver: false,
        }
    },

    template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <b-alert style="text-align: center;" v-model="ver" variant="danger">Niste izvrsili verifikaciju!</b-alert>
      <b-alert style="text-align: center;" v-model="wrongUsername" variant="danger">Wrong Username!</b-alert>
      <b-alert style="text-align: center;" v-model="wrongPassword" variant="danger">Wrong Password!</b-alert>
      <b-alert style="text-align: center;" v-model="Blocked" variant="danger"> Nalog je blokiran! </b-alert>
        <b-card title="Log in" id="login_screen">
          <b-form @submit.prevent="login">
            <b-form-input required type="text" v-model="username" placeholder="Enter Username"/>
            <b-form-input required type="password" v-model="password" placeholder="Enter Password"/>
            <div class="mt-2">
              <b-button variant="danger" type="button" v-on:click="cancel" class="ml-2">Cancel</b-button>
              <b-button variant="primary" type="submit" v-on:submit="login">Log in</b-button>
            </div>
          </b-form>
        </b-card>
      </div>
    `
    ,
    methods: {
        cancel: function () {
            app.$router.push("/")
        },
        login: async function () {
        	this.ver = false;
            let user = {
                params: {
                    "username": this.username, "password": this.password
                }
            }
            this.loadUserInfo
            await axios
                .get("korisnici/loginUser", user)
                .then(response => {
                    console.log(response)
                    this.cookie = response.data.cookie
                    this.userRole = response.data.rola
                    this.firstLogin = response.data.firstLogin
                })
                .catch(error => {
                    if (error.request.status==404) {
                        this.wrongUsername = true
                        this.wrongPassword = false
                        this.Blocked = false
                    } else if (error.request.status==401) {
                        this.wrongUsername = false
                        this.wrongPassword = true
                        this.Blocked = false
                    }
                    else if (error.request.status==403) {
                        this.wrongUsername = false
                        this.wrongPassword = false
                        this.Blocked = true
                    }
                    else if(error.request.status==400){
                    		this.ver = true;
                    	}
                })
            localStorage.setItem("cookie", this.cookie)
            localStorage.setItem("userRole", this.userRole)
            if (this.userRole === "PACIJENT") {
                app.$router.push("/home-pacijent")
            } else if (this.userRole === "DERMATOLOG") {
                if(this.firstLogin)
                    app.$router.push("/promena-lozinke")
                else
                    app.$router.push("/home-dermatolog")
            } else if (this.userRole === "FARMACEUT") {
                if(this.firstLogin)
                    app.$router.push("/promena-lozinke")
                else
                    app.$router.push("/home-farmaceut")
            } else if (this.userRole === "ADMIN_APOTEKE") {

            	if(this.firstLogin){
            		app.$router.push("/promena-lozinke")
            	}else{
            		app.$router.push("/home-admin_apoteke")
            	}

            }
        }

    }
});