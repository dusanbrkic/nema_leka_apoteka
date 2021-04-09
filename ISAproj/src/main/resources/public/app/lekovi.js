Vue.component("Lekovi", {
    data: function () {
        return {
           lekovi: 'waiting for server response'
        }
    },
      mounted () {
      	this.loadLekovi()
  	},
    template: `
    	<div>


		<div class="container">    
		  <div class="row">
			<div class="col-sm-4" v-for="lek in lekovi" :key="lek.id">
			  <div class="panel panel-default">
				<div class="panel-heading">{{lek.naziv}}</div>
				  Sastav: {{lek.sastav}} <br>
				  Uputstvo: {{lek.uputstvo}}
			</div>
		  </div>
		</div><br><br>


      </div>
    `
    ,
    methods: {
        loadLekovi:async function () {
            await axios.get("lekovi")
      		.then(response => (this.lekovi = response.data))
        },
        redirectToHome: function () {
            app.$router.push("/")
        },
    }
});

