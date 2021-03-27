const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}

const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld },
		{path: '/apoteke', component: Apoteke},
		{path: '/apoteka/:id', component: Apoteka},
		{path: '/login', component: Login},
		{path: '/home-dermatolog', component: HomeDermatolog}
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})