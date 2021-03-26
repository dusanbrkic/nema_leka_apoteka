Vue.component("HelloWorld", {
    data: function () {
        return {
            helloWorld: 'waiting for server response'
        }
    },
    mounted() {
        this.loadHelloWorld()
    },
    template: `
      <div>
      <p>Server kaze: {{ helloWorld }}</p>
      <div>
        <button v-on:click="redirectToHome">Home Page</button>
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