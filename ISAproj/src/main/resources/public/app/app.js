const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}

const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld }
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})