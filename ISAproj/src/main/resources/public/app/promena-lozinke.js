Vue.component("PromenaLozinke", {
    data: function () {
        return {
            cookie: "",
            currentPass: "",
            repeatPass: "",
            badPassword: false,
            samePassword: false,
            badRepeatPassword: false,
            new_password: "",
        }
    },
    mounted() {
        this.cookie = localStorage.getItem("cookie");
        this.userRole = localStorage.getItem("userRole");
    },
    template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <b-alert style="text-align: center;" dismissible>Prvi put se logujete, promenite lozinku!</b-alert>
      <b-alert style="text-align: center;" v-model="samePassword" variant="danger" dismissible>Ista lozinka!</b-alert>
      <b-alert style="text-align: center;" v-model="badRepeatPassword" variant="danger" dismissible>Lozinke se ne
        poklapaju!
      </b-alert>
      <b-alert style="text-align: center;" v-model="badPassword" variant="danger" dismissible>Pogresna lozinka!
      </b-alert>
      <b-card title="Promena Lozinke" style="max-width: 500px; margin: 30px auto;">
        <b-form @submit.prevent="redirectUser">
          <label for="pass">Stara lozinka:</label>
          <b-form-input id="pass" required type="password" v-model="currentPass" placeholder="Unesite vasu lozinku:"/>
          <label for="newpass">Nova Lozinka:</label>
          <b-form-input id="newpass" required type="password" v-model="new_password"
                        placeholder="Unesite novu lozinku:"/>
          <label for="repeatpass">Ponovo nova lozinka:</label>
          <b-form-input id="repeatpass" required type="password" v-model="repeatPass"
                        placeholder="Ponovite novu lozinku:"/>
          <div class="mt-2">
            <b-button style="float: right" variant="primary" type="submit" v-on:submit="redirectUser">Promeni lozinku</b-button>
          </div>
        </b-form>
      </b-card>
      </div>
    `,
    methods: {
        changePass: async function () {
            let info = {

                params: {
                    "cookie": this.cookie,
                    "currentPass": this.currentPass,
                    "newPass": this.new_password
                }
            }
            await axios
                .post("korisnici/updatePass", null, info)
                .then((response) => {
                    localStorage.setItem("cookie", response.data.cookie)
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
                })
                .catch(reason => {
                    if (reason.request.status === 400) {
                        this.badRepeatPassword = false;
                        this.samePassword = false
                        this.badPassword = true
                    }
                })
        },
        redirectUser: function () {
            if (this.new_password !== this.repeatPass) {
                this.badRepeatPassword = true;
                this.samePassword = false
                this.badPassword = false
                return;
            }
            if (this.new_password === this.currentPass) {
                this.samePassword = true;
                this.badRepeatPassword = false;
                this.badPassword = false
                return;
            }
            this.changePass()
        },

    },
});