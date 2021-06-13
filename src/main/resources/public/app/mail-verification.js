Vue.component("MailVer", {
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
            badVer: false,
			verifikacioniKod: ""
        };
    },
    mounted() {
		this.verifikacioniKod = "";
    },
    template: `
      <div>
      <b-alert style="text-align: center;" v-model="this.badVer" variant="danger">Niste uneli dobar verifikacioni kod!</b-alert>
      <b-card style="max-width: 500px; margin: 30px auto;" title="Moj Nalog">
        <b-form @submit.prevent="onSubmit">
		  <b-form-group id="input-group-2" label="Verifikacioni kod:" label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="verifikacioniKod"
            ></b-form-input>
          </b-form-group>
          <b-button style="float: right" variant="danger" v-on:click="onSubmit">Potvrdi</b-button>
        </b-form>
      </b-card>
      </div>
    `,
    methods: {
        onSubmit: function () {
        	this.badVer = false;
			console.log(this.verifikacioniKod);
            axios.post("korisnici/verifyCode", this.verifikacioniKod).then(response =>app.$router.push("/"))
            .catch(error => {
                if (error.request.status==404) {
					this.badVer = true;
                } else if (error.request.status==400) {
                    this.badVer = true;
                }
            })
        },
    },
});