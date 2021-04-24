
// neregistrovani korisnik
const Home  = { template: '<Home></Home>'}
const HomeMain  = { template: '<HomeMain></HomeMain>'}

// pacijent
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}
const PacijentMain = {template: '<PacijentMain></PacijentMain>'}
const PacijentLekovi = {template: '<PacijentLekovi></PacijentLekovi>'}
const PacijentNarudzbenice = {template: '<PacijentNarudzbenice></PacijentNarudzbenice>'}

// dodatno
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const Registracija = {template: '<Registracija></Registracija>'}
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}
const AdminApotekeLekovi = {template: '<AdminApotekeLekovi></AdminApotekeLekovi>'}
const MailVer = {template: '<MailVer></MailVer>'} 
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
        {path: '/', component: Home,  children: [
            {
                path: '',
                component: HomeMain
            },
            {
                path: '/apoteke',
                component: Apoteke
            },
            {
                path: '/lekovi',
                component: Lekovi
            },
        ]},
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
	    {path: '/home-pacijent', component: HomePacijent,  children: [
	        {
	            path: '',
	            component: PacijentMain
	        },
			{
                path: 'apoteke',
                component: Apoteke
            },
            {
                path: 'lekovi',
                component: PacijentLekovi
            },
            {
                path: 'narudzbenice',
                component: PacijentNarudzbenice
            }
	    ]},
        {path: '/admin-apoteke-lekovi', component: AdminApotekeLekovi},
        {path: '/lekovi', component: Lekovi},
        {path: '/dodaj-lek-admin', component: DodajLekAdmin},
        {path: '/pretraga-lek-admin', component: PretragaLekAdmin},
        {path: '/admin-apoteke-lozinka', component: LozinkaAdmin},
        {path: '/registracija', component: Registracija},
        {path: '/mail-verification', component: MailVer}
        
    ]
});


var app = new Vue({
    router,
    el: '#apoteka-app'
})
