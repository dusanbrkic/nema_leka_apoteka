Vue.component("Registracija", {
    data: function () {
        return {
            cookie: "",
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
                minDate: "",
                postojiMail: false,
                postojiKorisnicko: false,
            },
            userRole: ""
        };
    },
    mounted() {
    },
    template: `
      <div>

      <b-card style="max-width: 500px; margin: 30px auto;" title="Registracija">
      <b-alert style="text-align: center;" v-model="this.postojiMail" variant="danger">Vec postoji nalog sa ovom email adresom!</b-alert>
      <b-alert style="text-align: center;" v-model="this.postojiKorisnicko" variant="danger">Vec postoji nalog sa ovim korisnickim imenom!</b-alert>
        <b-form @submit.prevent="onSubmit">
          <b-form-group id="input-group-1" label="Email adresa:" label-for="input-1">
            <b-form-input
                id="input-1"
                v-model="forma.emailAdresa"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-2" label="Korisnicko ime:" label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="forma.username"
            ></b-form-input>
          </b-form-group>
		  <b-form-group id="input-group-10" label="Lozinka:" label-for="input-2">
            <b-form-input
                id="input-10"
                v-model="forma.password"
                type="password"
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
          <b-button type="submit" variant="primary">Registruj se</b-button>
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
        onChangePass: function (event) {
            event.preventDefault()
            app.$router.push("/admin-apoteke-lozinka")
        },
        onSubmit: function () {
        	this.postojiMail = false;
        	this.postojiKorisnicko = false;
            axios.post("korisnici/registerUser", this.forma).then(response => app.$router.push("/mail-verification"))
                .catch(error => {
                if (error.request.status==400) {
					this.postojiMail = true;
                } else if (error.request.status==404 || error.request.status == 409) {
                    this.postojiKorisnicko = true;
                }
            });
        },
        fixDate: function (date){
            return moment(date).format('YYYY-MM-DD')
        }
    },
});
