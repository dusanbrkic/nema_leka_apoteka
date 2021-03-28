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



		<div class="jumbotron">
		  <div class="container text-center">
			<h1>Apoteka</h1>      
			<p>Lekovi, preparati i konsultacije sa doktorima</p>
		  </div>
		</div>


		<nav class="navbar navbar-expand-lg navbar-inverse">
		  <div class="container-fluid">
			<a class="navbar-brand" href="#">Logo</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			  <span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="nav navbar-nav">
				<li><a href="#" v-on:click="redirectToHome">Početna strana</a></li>
				<li class="active"><a href="#">       Apoteke       </a></li>
				<li><a href="#">    Lekovi        </a></li>
				<li><a href="#">         Uloguj se     </a></li>
				<li><a href="#">Kontakt</a></li>
			  </ul>
  
			  <ul class="nav navbar-nav navbar-right">
				<li><a href="#"><span class="glyphicon glyphicon-user"></span> Moj nalog</a></li>
				<li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Korpa</a></li>
			  </ul>
  
			</div>
		  </div>
		</nav>
		
		<div class="container">    
		  <div class="row">
			<div class="col-sm-4" v-for="apoteka in apoteke" :key="apoteka.id">
			  <div class="panel panel-default">
				<div class="panel-heading">Apoteke</div>
				  <div class="panel-body"><img src="https://assets.siccode.com/i-s-b/sic-code-5912-drug-stores-proprietary-stores.jpg" class="img-responsive" style="width:100%" alt="Image"></div>
			</div>
		  </div>
		</div><br><br>


      </div>
    `
    ,
    methods: {
        loadApoteka: function () {
            axios.get("apoteke")
      		.then(response => (this.apoteke = response.data))
        },
        redirectToHome: function () {
            app.$router.push("/")
        },
    }
});

