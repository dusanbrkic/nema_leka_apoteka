Vue.component("Apoteka", {
    data() {
    	
        return { apoteke: 'Ne postoji apoteka sa ovim id'} 

    },
      mounted () {
      	this.loadApoteka(this.$route.params.id);
  	},
    template: `
    	<div>
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
    		<tr>
    			<td>{{apoteke.id}}</td>
    			<td>{{apoteke.naziv}}</td>
    			<td>{{apoteke.adresa}}</td>
    			<td>{{apoteke.prosecnaOcena}}</td>
    			<td>{{apoteke.opis}}</td>
    			<td>{{apoteke.dermatolozi}}</td>
    			<td>{{apoteke.farmaceuti}}</td>
    			
    		</tr>
    	</table>
   	  	<div>
        	<button v-on:click="redirectToHome">Home Page</button>
      	</div>
      </div>
    `
    ,
    methods: {
    	loadApoteka(id){
    		axios.get("apoteke/" + id)
      		.then(response => {
      		(this.apoteke = response.data)
      		console.log(this.apoteke);
      		
      		})
      		
		},
		redirectToHome: function () {
            app.$router.push("/")
        }
	}
});