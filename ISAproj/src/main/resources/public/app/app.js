const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}
const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld },
		{path: '/apoteke', component: Apoteke},
		{path: '/apoteka/:id', component: Apoteka},
		{path: '/login', component: Login},
		{path: '/home-dermatolog', component: HomeDermatolog},
		{path: '/home-admin_apoteke', component : HomeAdminApoteke},
		{path: '/home-pacijent', component : HomePacijent}
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})