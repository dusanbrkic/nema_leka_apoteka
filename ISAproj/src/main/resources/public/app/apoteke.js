Vue.component("Apoteke", {
    data: function () {
        return {
            apoteke: 'waiting for server response'
        }
    },
      mounted () {
      	this.loadApoteka()
  	},
    template: `
    	<div>
    	<ul>
    		<li v-for="apoteka in apoteke" :key="apoteka.id">
    		{{apoteka}}</li>
    	</ul>
   	  	<div>
        	<button v-on:click="redirectToHome">Home Page</button>
      	</div>

      </div>
    `
    ,
    methods: {
        loadApoteka: function () {
            axios.get("http://localhost:8080/apoteke")
      		.then(response => (this.apoteke = response.data))
        },
        redirectToHome: function () {
            app.$router.push("/")
        },
    }
});

