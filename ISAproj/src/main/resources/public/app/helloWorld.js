Vue.component("HelloWorld", {
    data: function () {
        return {
            nazivLeka: "",
            tipLeka: ""
        }
    },
    mounted() {
    },
    template: `
      <div>
      <link rel="stylesheet" href="css/login.css" type="text/css">
     <!-- <b-alert style="text-align: center;" v-model="wrongUsername" variant="danger">Wrong Username!</b-alert>
      <b-alert style="text-align: center;" v-model="wrongPassword" variant="danger">Wrong Password!</b-alert> -->
      <div id="login-div" style="max-width: 40rem; text-align: center; margin: auto;" class="mt-5">
        <b-card title="Dodavanje leka">
          <b-form>
            <b-form-input required type="text" v-model="nazivLeka" placeholder="Unesite naziv leka"/>
            <b-form-input required type="password" v-model="password" placeholder="Unesite tip leka"/>
            <div class="mt-2">
             <!-- <b-button variant="danger" type="button" v-on:click="cancel" class="ml-2">Cancel</b-button> -->
              <b-button variant="primary" type="submit" v-on:submit="Dodaj lek">Log in</b-button>
            </div>
          </b-form>
        </b-card>
      </div>
      </div>
    `
    ,
    methods: {
        loadHelloWorld: function () {
            axios
                .get("hello")
                .then((response) => (this.helloWorld = response.data))
        },
        redirectToHome: function () {
            app.$router.push("/")
        }
    }
});