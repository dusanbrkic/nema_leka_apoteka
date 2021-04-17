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
                "cookie": "",
                "firstLogin": "",
                "adresa": "",
                "grad": "",
                "drzava": "",
                "brojTelefona": "",
            },
            forma: {
                "username": "",
                "password": "",
                "ime": "",
                "prezime": "",
                "datumRodjenja": "",
                "emailAdresa": "",
                "cookie": "",
                "firstLogin": "",
                "adresa": "",
                "grad": "",
                "drzava": "",
                "brojTelefona": "",
            },
            userRole: ""
        };
    },
    mounted() {
        this.userRole = localStorage.getItem("userRole");
        this.cookie = localStorage.getItem("cookie");
        this.loadUserInfo();

    },
    template: `
      <div>
      <b-card style="max-width: 500px; margin: 30px auto;" title="Moj Nalog">
        <b-form @submit.prevent="onSubmit" @reset="onReset">
          <b-form-group
              id="input-group-1"
              label="Email address:"
              label-for="input-1"
              description="Nije dozvoljeno menjanje E-mail-a">
            <b-form-input
                id="input-1"
                v-model="forma.emailAdresa"
                type="email"
                readonly
            ></b-form-input>
          </b-form-group>
          <b-form-group
              id="input-group-2"
              label="Korisnicko ime:"
              label-for="input-2"
              description="Nije dozvoljeno menjanje korisnickog imena">
            <b-form-input
                id="input-2"
                v-model="forma.username"
                readonly
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Ime:" label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="forma.ime"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-4" label="Prezime:" label-for="input-4">
            <b-form-input
                id="input-4"
                v-model="forma.prezime"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-5" label="Datum rodjenja:" label-for="input-5">
            <b-form-input
                id="input-5"
                type="date"
                v-model="forma.datumRodjenja"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-6" label="Adresa:" label-for="input-6">
            <b-form-input
                id="input-6"
                v-model="forma.adresa"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-7" label="Grad:" label-for="input-7">
            <b-form-input
                id="input-7"
                v-model="forma.grad"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-8" label="Drzava:" label-for="input-8">
            <b-form-input
                id="input-8"
                v-model="forma.drzava"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-9" label="Broj telefona:" label-for="input-9">
            <b-form-input
                id="input-9"
                v-model="forma.brojTelefona"
            ></b-form-input>
          </b-form-group>
          <b-button v-on:click="returnToHome">Nazad</b-button>
          <b-button type="submit" variant="primary">Sacuvaj</b-button>
          <b-button type="reset" variant="danger">Resetuj</b-button>
          <b-button style="float: right" variant="danger" v-on:click="onChangePass">Promeni sifru</b-button>
        </b-form>
      </b-card>
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
                    this.korisnik.datumRodjenja = this.fixDate(this.korisnik.datumRodjenja)
                    this.forma = JSON.parse(JSON.stringify(this.korisnik))
                })
        },
        onReset: function (event) {
            event.preventDefault()
            this.forma = JSON.parse(JSON.stringify(this.korisnik))
        },
        returnToHome: function () {
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
        onChangePass: function (event) {
            event.preventDefault()
            app.$router.push("/admin-apoteke-lozinka")
        },
        onSubmit: function (event) {
            event.preventDefault()
            this.forma.datumRodjenja = this.fixDate(this.forma.datumRodjenja)
            this.korisnik = JSON.parse(JSON.stringify(this.forma))
            axios
                .put("korisnici/updateUser", this.korisnik)
        },
        fixDate: function (date){
            return moment(date).format('YYYY-MM-DD')
        }
    },
});
