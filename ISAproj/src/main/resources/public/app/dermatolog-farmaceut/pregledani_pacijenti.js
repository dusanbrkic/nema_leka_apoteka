Vue.component("PregledaniPacijenti", {
    data: function () {
        return {
            cookie: '',
            rola: '',
            fields: [
                {
                    key: 'ime',
                    sortable: true
                },
                {
                    key: 'prezime',
                    sortable: true
                },
                {
                    key: 'datum_rodjenja',
                    sortable: true,
                    formatter: (value, key, item) => {
                        return moment(value).format("DD/MM/YYYY");
                    },
                },
                {
                    key: 'email_adresa',
                    sortable: true
                },
                {
                    key: 'adresa',
                    sortable: true
                },
                {
                    key: 'grad',
                    sortable: true
                },
                {
                    key: 'drzava',
                    sortable: true
                },
                {
                    key: 'broj_telefona',
                    sortable: false
                },
                {
                    key: 'datum_pregleda',
                    sortable: true,
                    formatter: (value, key, item) => {
                        return moment(value).format("DD/MM/YYYY");
                    },
                },
            ],
            items: []
        }

    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
        this.rola = localStorage.getItem("userRole")
        this.loadPacijenti()
    },
    template: `
      <div>
      <b-card style="margin: 40px auto; max-width: 1200px">
        <b-table hover :items="items" :fields="fields"></b-table>
      </b-card>
      </div>
    `,
    methods: {
        loadPacijenti: function () {
            if (this.rola == "FARMACEUT") this.loadSavetovanja()
            else if (this.rola == "DERMATOLOG") this.loadPregledi()
        },
        loadPregledi: function () {
            axios
                .get("pregledi/getPreglediByDermatolog", {params: {'cookie': this.cookie}})
                .then(response => {
                    let pregledi = response.data
                    for (const p of pregledi) {
                        this.items.push({
                            ime: p.pacijent.ime,
                            prezime: p.pacijent.prezime,
                            datum_rodjenja: p.pacijent.datumRodjenja,
                            email_adresa: p.pacijent.emailAdresa,
                            adresa: p.pacijent.adresa,
                            grad: p.pacijent.grad,
                            drzava: p.pacijent.drzava,
                            broj_telefona: p.pacijent.brojTelefona,
                            datum_pregleda: p.start
                        })
                    }
                })
        },
        loadSavetovanja: function () {
            axios
                .get("savetovanja/getSavetovanjaByFarmaceut", {params: {'cookie': this.cookie}})
                .then(response => {
                    let savetovanja = response.data
                    for (const s of savetovanja) {
                        this.items.push({
                            ime: s.pacijent.ime,
                            prezime: s.pacijent.prezime,
                            datum_rodjenja: s.pacijent.datumRodjenja,
                            email_adresa: s.pacijent.emailAdresa,
                            adresa: s.pacijent.adresa,
                            grad: s.pacijent.grad,
                            drzava: s.pacijent.drzava,
                            broj_telefona: s.pacijent.brojTelefona,
                            datum_pregleda: s.start
                        })
                    }
                })
        }
    },
});
