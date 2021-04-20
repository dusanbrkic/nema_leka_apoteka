const Home  = { template: '<Home></Home>'}
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}
const AdminApotekeLekovi = {template: '<AdminApotekeLekovi></AdminApotekeLekovi>'}
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}
const IzmenaPodataka = {template: '<IzmenaPodataka></IzmenaPodataka>'}
const Lekovi = {template: '<Lekovi></Lekovi>'}
const DodajLekAdmin = {template: '<DodajLekAdmin></DodajLekAdmin>'}
const PretragaLekAdmin = {template: '<PretragaLekAdmin></PretragaLekAdmin>'}
const LozinkaAdmin = {template: '<LozinkaAdmin></LozinkaAdmin>'}

//dermatolog-farmaceut
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}
const HomeFarmaceut = {template: '<HomeFarmaceut></HomeFarmaceut>'}
const CalendarView = {template: '<CalendarView></CalendarView>'}
const DermatologMain = {template: '<DermatologMain></DermatologMain>'}
const FarmaceutMain = {template: '<FarmaceutMain></FarmaceutMain>'}
const PregledaniPacijenti = {template: '<PregledaniPacijenti></PregledaniPacijenti>'}
const OdsustvoForma = {template: '<OdsustvoForma></OdsustvoForma>'}

const router = new VueRouter({
    mode : 'hash',
    routes : [
        {path: '/', component: Home},
        {path: '/apoteke', component: Apoteke},
        {path: '/apoteka/:id', component: Apoteka},
        {path: '/login', component: Login},
        {path: '/izmena-podataka', component: IzmenaPodataka},
        {path: '/home-dermatolog', component: HomeDermatolog,  children: [
                {
                    path: '',
                    component: DermatologMain
                },
                {
                    path: 'calendar-view',
                    component: CalendarView
                },
                {
                    path: 'pregledani-pacijenti',
                    component: PregledaniPacijenti
                }
            ]},
        {path: '/home-farmaceut', component: HomeFarmaceut,  children: [
                {
                    path: '',
                    component: FarmaceutMain
                },
                {
                    path: 'calendar-view',
                    component: CalendarView
                },
                {
                    path: 'pregledani-pacijenti',
                    component: PregledaniPacijenti
                }
            ]},
        {path: '/odsustvo-forma', component: OdsustvoForma},
        {path: '/home-admin_apoteke', component : HomeAdminApoteke},
        {path: '/home-pacijent', component : HomePacijent},
        {path: '/admin-apoteke-lekovi', component: AdminApotekeLekovi},
        {path: '/lekovi', component: Lekovi},
        {path: '/dodaj-lek-admin', component: DodajLekAdmin},
        {path: '/pretraga-lek-admin', component: PretragaLekAdmin},
        {path: '/admin-apoteke-lozinka', component: LozinkaAdmin}
    ]
});


var app = new Vue({
    router,
    el: '#apoteka-app'
})
