const Home  = { template: '<Home></Home>'}

const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home }
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})