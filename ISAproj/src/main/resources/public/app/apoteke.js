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
		<!--
    	<table>
    		<tr>
    			<th>id</th>
    			<th>naziv</th> 
    			<th>adresa</th>
    			<th>prosecna ocena</th>
    			<th>opis</th>
    			<th>dermatolozi</th>
    			<th>farmaceuti</th>
    		</tr>
    		<tr v-for="apoteka in apoteke" :key="apoteka.id">
    			<td>{{apoteka.id}}</td>
    			<td>{{apoteka.naziv}}</td>
    			<td>{{apoteka.adresa}}</td>
    			<td>{{apoteka.prosecnaOcena}}</td>
    			<td>{{apoteka.opis}}</td>
    			<td>{{apoteka.dermatolozi}}</td>
    			<td>{{apoteka.farmaceuti}}</td>
    			
    		</tr>
    	</table>
   	  	<div>
        	<button v-on:click="redirectToHome">Home Page</button>
      	</div>
		-->


		<div class="container">    
		  <div class="row">
			<div class="col-sm-4" v-for="apoteka in apoteke" :key="apoteka.id">
			  <div class="panel panel-default">
				<div class="panel-heading">{{apoteka.naziv}}</div>
				  Adresa: {{apoteka.adresa}} <br>
				  Ocena: {{apoteka.prosecnaOcena}} <br>
				  Opis: {{apoteka.opis}}
			</div>
		  </div>
		</div><br><br>


      </div>
    `
    ,
    methods: {
        loadApoteka:async function () {
            await axios.get("apoteke")
      		.then(response => (this.apoteke = response.data))
        },
        redirectToHome: function () {
            app.$router.push("/")
        },
    }
});

