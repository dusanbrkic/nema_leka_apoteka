Vue.component("Login", {
    data: function () {
        return {
            username: "",
            password: "",
            cookie: "",
            userRole: "",
            wrongUsername : false,
            wrongPassword : false

        }
    },

    template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <b-alert style="text-align: center;" v-model="wrongUsername" variant="danger">Wrong Username!</b-alert>
      <b-alert style="text-align: center;" v-model="wrongPassword" variant="danger">Wrong Password!</b-alert>
      <div id="login-div" style="max-width: 40rem; text-align: center; margin: auto;" class="mt-5">
        <b-card title="Log in">
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
      </div>
    `
    ,
    methods: {
        cancel: function () {
            app.$router.push("/")
        },
        login: async function () {
            let user = {
                params: {
                    "username": this.username, "password": this.password
                }
            }
            this.loadUserInfo
            await axios
                .get("korisnici/loginUser", user)
                .then(response => {
                    this.cookie = response.data.cookie
                    this.userRole = response.data.rola
                })
                .catch(error => {
                    if (error.request.status==404) {
                        this.wrongUsername = true
                        this.wrongPassword = false
                    } else if (error.request.status==401) {
                        this.wrongUsername = false
                        this.wrongPassword = true
                    }
                })
            localStorage.setItem("cookie", this.cookie)
            if (this.userRole === "PACIJENT") {
                app.$router.push("/home-pacijent")
            } else if (this.userRole === "DERMATOLOG") {
                app.$router.push("/home-dermatolog")
            } else if (this.userRole === "FARMACEUT") {
                app.$router.push("/home-farmaceut")
            } else if (this.userRole === "ADMIN_APOTEKE") {
                app.$router.push("/home-admin_apoteke")
            }
        }

    }
});