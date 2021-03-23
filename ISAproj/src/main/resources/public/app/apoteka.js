Vue.component("Apoteka", {
    data() {
    	
        return { apoteke: 'Ne postoji apoteka sa ovim id'} 

    },
      mounted () {
      	this.loadApoteka(this.$route.params.id);
  	},
    template: `
    	<div>
    	<p>
    		{{apoteke}}
		</p>
   	  	<div>
        	<button v-on:click="redirectToHome">Home Page</button>
      	</div>
      </div>
    `
    ,
    methods: {
    	loadApoteka(id){
    		axios.get("http://localhost:8080/apoteke/" + id)
      		.then(response => (this.apoteke = response.data))
		},
		redirectToHome: function () {
            app.$router.push("/")
        }
	}
});