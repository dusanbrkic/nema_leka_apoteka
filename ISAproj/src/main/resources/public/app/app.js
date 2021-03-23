const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld },
		{path: '/apoteke', component: Apoteke},
		{path: '/apoteka/:id', component: Apoteka}
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})