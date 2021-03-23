const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteka = { template: '<Apoteka></Apoteka>'}
const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld },
		{path: '/apoteka', component: Apoteka }
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})