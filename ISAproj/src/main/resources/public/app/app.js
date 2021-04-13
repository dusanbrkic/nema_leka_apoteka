const Navbar  = { template: '<Navbar></Navbar>'}
const MainMenu  = { template: '<MainMenu></MainMenu>'}

const HelloWorld = {template : '<HelloWorld></HelloWorld>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}
const AdminApotekeLekovi = {template: '<AdminApotekeLekovi></AdminApotekeLekovi>'}
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}
const IzmenaPodataka = {template: '<IzmenaPodataka></IzmenaPodataka>'}
const Lekovi = {template: '<Lekovi></Lekovi>'}
const CalendarView = {template: '<CalendarView></CalendarView>'}

const router = new VueRouter({
    mode : 'hash',
    routes : [
        {path: '/', component: MainMenu },
        {path: '/helloWorld', component: HelloWorld },
        {path: '/apoteke', component: Apoteke},
        {path: '/apoteka/:id', component: Apoteka},
        {path: '/login', component: Login},
        {path: '/izmena-podataka', component: IzmenaPodataka},
        {path: '/home-dermatolog', component: HomeDermatolog},
        {path: '/home-admin_apoteke', component : HomeAdminApoteke},
        {path: '/home-pacijent', component : HomePacijent},
        {path: '/admin-apoteke-lekovi', component: AdminApotekeLekovi},
        {path: '/lekovi', component: Lekovi},
        {path: '/calendar-view', component : CalendarView}
    ]
});


var app = new Vue({
    render: h => h(Navbar), // mount the base component
    router,
    el: '#apoteka-app'
})