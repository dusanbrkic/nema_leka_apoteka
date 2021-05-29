
// neregistrovani korisnik
const Home  = { template: '<Home></Home>'}
const HomeMain  = { template: '<HomeMain></HomeMain>'}

// pacijent
const HomePacijent = {template: '<HomePacijent></HomePacijent>'}
const PacijentMain = {template: '<PacijentMain></PacijentMain>'}
const PacijentApoteke = {template: '<PacijentApoteke></PacijentApoteke>'}
const PacijentLekovi = {template: '<PacijentLekovi></PacijentLekovi>'}
const PacijentNarudzbenice = {template: '<PacijentNarudzbenice></PacijentNarudzbenice>'}
const NaruciPacijent = {template: '<NaruciPacijent></NaruciPacijent>'}
const PacijentZakaziPregled = {template: '<PacijentZakaziPregled></PacijentZakaziPregled>'}
const PacijentPoseteDermatologu = {template: '<PacijentPoseteDermatologu></PacijentPoseteDermatologu>'}
const PacijentDermatolozi = { template: '<PacijentDermatolozi></PacijentDermatolozi>' };
const PacijentFarmaceuti = {template: '<PacijentFarmaceuti></PacijentFarmaceuti>'}
const PacijentZakazivanjeSavetovanja = {template: '<PacijentZakazivanjeSavetovanja></PacijentZakazivanjeSavetovanja>'}
const PacijentSavetovanja = {template: '<PacijentSavetovanja></PacijentSavetovanja>'}


// dodatno
const Apoteke = { template: '<Apoteke></Apoteke>'}
const Apoteka = {template: '<Apoteka></Apoteka>'}
const Login = {template: '<Login></Login>'}
const Registracija = {template: '<Registracija></Registracija>'}
const MailVer = {template: '<MailVer></MailVer>'}
const IzmenaPodataka = {template: '<IzmenaPodataka></IzmenaPodataka>'}
const Lekovi = {template: '<Lekovi></Lekovi>'}
const PromenaLozinke = {template: '<PromenaLozinke></PromenaLozinke>'}

// admin apoteke
const HomeAdminApoteke = {template: '<HomeAdminApoteke></HomeAdminApoteke>'}
const AdminApotekeLekovi = {template: '<AdminApotekeLekovi></AdminApotekeLekovi>'}
const DodajLekAdmin = {template: '<DodajLekAdmin></DodajLekAdmin>'}
const PretragaLekAdmin = {template: '<PretragaLekAdmin></PretragaLekAdmin>'}
const LozinkaAdmin = {template: '<LozinkaAdmin></LozinkaAdmin>'}
const NaruciAdmin = {template: '<NaruciAdmin></NaruciAdmin>'}
const AdminApoteka = {template: '<AdminApoteka></AdminApoteka>'}
const AdminSlobodniTermini = {template: '<AdminSlobodniTermini></AdminSlobodniTermini>'}
const DodajFarmaceuta = {template: '<DodajFarmaceuta></DodajFarmaceuta>'}
const DodajDermatologa = {template: '<DodajDermatologa></DodajDermatologa>'}
const UrediDermatologe = {template: '<UrediDermatologe></UrediDermatologe>'}
const UrediFarmaceute = {template: '<UrediFarmaceute></UrediFarmaceute>'}
const AdminNarudzbenice = {template: '<AdminNarudzbenice></AdminNarudzbenice>'}
const AdminPromocija = {template: '<AdminPromocija></AdminPromocija>'}
const AdminOdsustvo = {template: '<AdminOdsustvo></AdminOdsustvo>'}
const AdminIzvestaji = {template: '<AdminIzvestaji></AdminIzvestaji>'}

//dermatolog-farmaceut
const HomeDermatolog = {template: '<HomeDermatolog></HomeDermatolog>'}
const HomeFarmaceut = {template: '<HomeFarmaceut></HomeFarmaceut>'}
const CalendarView = {template: '<CalendarView></CalendarView>'}
const DermatologMain = {template: '<DermatologMain></DermatologMain>'}
const FarmaceutMain = {template: '<FarmaceutMain></FarmaceutMain>'}
const PregledaniPacijenti = {template: '<PregledaniPacijenti></PregledaniPacijenti>'}
const OdsustvoForma = {template: '<OdsustvoForma></OdsustvoForma>'}
const PregledForma = {template: '<PregledForma></PregledForma>'}
const IzdavanjeLeka = {template: '<IzdavanjeLeka></IzdavanjeLeka>'}

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
                },
            ]},
        {path: '/home-farmaceut', component: HomeFarmaceut,  children: [
                {
                    path: '',
                    component: FarmaceutMain
                },
                {
                    path: 'izdaj-lek',
                    component: IzdavanjeLeka
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
                component: PacijentApoteke
            },
            {
                path: 'lekovi',
                component: PacijentLekovi
            },
            {
                path: 'narudzbenice',
                component: PacijentNarudzbenice
            },
            {
                path: 'rezervacija',
                component: NaruciPacijent
            },
            {
            	path: 'zakazivanje_kod_dermatologa',
            	component: PacijentZakaziPregled
            },
            {
            	path: 'posete-dermatologu',
            	component: PacijentPoseteDermatologu
            },
            {
            	path: 'zakazivanje-savetovanja',
            	component: PacijentZakazivanjeSavetovanja
            },
            {
            	path: 'savetovanja',
            	component: PacijentSavetovanja
            },
	    ]},
        {path: '/admin-apoteke-lekovi', component: AdminApotekeLekovi},
        {path: '/lekovi', component: Lekovi},
        {path: '/dodaj-lek-admin', component: DodajLekAdmin},
        {path: '/pretraga-lek-admin', component: PretragaLekAdmin},
        {path: '/admin-apoteke-lozinka', component: LozinkaAdmin},
        {path: '/registracija', component: Registracija},
        {path: '/mail-verification', component: MailVer},
        {path: '/admin-apoteke-narudzbina',component: NaruciAdmin},
        {path: '/admin-apoteke-apoteka',component: AdminApoteka},
        {path: '/admin-apoteke-slobodan-termin', component: AdminSlobodniTermini},
        {path: '/admin-apoteke-dodaj-farmaceuta', component: DodajFarmaceuta},
        {path: '/admin-apoteke-dodaj-dermatologa', component: DodajDermatologa},
        {path: '/pregled-forma', component: PregledForma},
        {path: '/promena-lozinke',component: PromenaLozinke},
        {path: '/admin-apoteke-dermatolozi', component: UrediDermatologe},
        {path: '/admin-apoteke-farmaceuti', component: UrediFarmaceute},
        {path: '/admin-apoteke-narudzbenice', component: AdminNarudzbenice},
        {path: '/admin-apoteke-promocija', component: AdminPromocija },
        {path: '/pacijent_dermatolozi', component: PacijentDermatolozi },
        {path: '/pacijent_farmaceuti',component: PacijentFarmaceuti},
        {path: '/admin-apoteke-odsustvo', component: AdminOdsustvo},
        {path: '/admin-apoteke-izvestaji', component: AdminIzvestaji},
        
    ]
});


var app = new Vue({
    router,
    el: '#apoteka-app'
})
