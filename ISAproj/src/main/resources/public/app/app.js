const Home  = { template: '<Home></Home>'}
const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}

const IzmenaDermatolog = {template: '<IzmenaDermatolog></IzmenaDermatolog>'}
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}

const IzmenaAdminApoteke = {template: '<IzmenaAdminApoteke></IzmenaAdminApoteke>'}
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}

const IzmenaPacijent = {template: '<IzmenaPacijent></IzmenaPacijent>'}
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}

const router = new VueRouter({
	mode : 'hash',
	routes : [
		{path: '/', component: Home },
		{path: '/helloWorld', component: HelloWorld },
		{path: '/apoteke', component: Apoteke},
		{path: '/apoteka/:id', component: Apoteka},
		{path: '/login', component: Login},
		{path: '/izmena-dermatolog', component: IzmenaDermatolog},
		{path: '/home-dermatolog', component: HomeDermatolog},
		{path: '/izmena-admin_apoteke', component : IzmenaAdminApoteke},
		{path: '/home-admin_apoteke', component : HomeAdminApoteke},
		{path: '/izmena-pacijent', component : IzmenaPacijent},
		{path: '/home-pacijent', component : HomePacijent}
	]
	
});


var app = new Vue({
	router,
	el: '#apoteka-app'
})