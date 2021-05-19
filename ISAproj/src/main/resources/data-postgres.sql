insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Brufen','bazinga 2mg','1','Piti jednom dnevno posle obroka', 1);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Strepsils','lozenga 1.2mg+o.6mg, blister 2X8kom','2','Max 3 puta dnevno', 2);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Gynaflor','vaginalna tableta; 1x 10exp8 CFU+0.03mg','3','Koristiti 2 puta', 2);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Aspirin','oblozena tableta; 500mg','4','Max 2 puta dnevno',0);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Andol','tableta 75mg','5','Max 2 puta dnevno',0);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Viralex','tableta: 200mg','6','Max 2 puta dnevno',1);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Humira','rastvor za injekciju u napunjenom penu 50mg','7','Max 2 puta dnevno',1);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Aspirin1','oblozena tableta; 1500mg','8','Max 2 puta dnevno',1);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Vaminolast','rastvor za infuziju','9','Max 2 puta dnevno',0);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Alpha D3','tableta za alfe','10','Max 2 puta dnevno',2);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Helex','oblozena tableta; 500mg','11','Max 2 puta dnevno',2);
insert into lek (id,naziv,sastav,sifra,uputstvo,tip_leka) values (nextval('lek_seq'),'Viagra','oblozena tableta; 40mg; 10kom','12','Africka sljiva',0);

insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (2, 3);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (2, 8);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (2, 9);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (9, 2);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (8, 2);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (2, 4);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (2, 1);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (1, 2);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (3, 1);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (1, 3);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (3, 2);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (4, 2);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (3, 7);
insert into lek_zamenski_lekovi (lek_id,zamenski_lekovi_id) values (7, 3);

insert into apoteka (id,adresa,naziv,opis,prosecna_ocena, cena_pregleda, cena_savetovanja, latitude, longitude) values (nextval('apoteka_seq'),'Cara Dušana 107, Zrenjanin','Apoteka Markovic','opis apoteke',3.6, 400, 200, 20.391395129936356, 45.38985699678626);
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena, cena_pregleda, cena_savetovanja) values (nextval('apoteka_seq'),'Lazara Obilica 15, Beograd','Apoteka Djincic','opis apoteke',4.5, 350, 250);
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena, cena_pregleda, cena_savetovanja) values (nextval('apoteka_seq'),'Cara Lazara 103, Novi Sad','Apoteka Lazarevic','opis apoteke',2.2, 600, 400);
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena, cena_pregleda, cena_savetovanja) values (nextval('apoteka_seq'),'Zmaj Jovina 45, Sid','Apoteka Zmaj','opis apoteke',4.8, 500, 440);
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena, cena_pregleda, cena_savetovanja) values (nextval('apoteka_seq'),'Matije Grupca 35, Novi Sad','Apoteka Antic','opis apoteke',3.2, 1020, 670);


insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'100','10','50','1','1');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'500','33','450','1','2');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1000','4','800','1','3');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'800','140','450','2','4');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1800','43','1450','2','5');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'80','200','45','3','6');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1200','58','1200','4','7');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'600','15','670','4','8');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'700','23','770','4','9');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'300','74','340','4','10');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'320','5','300','5','11');
insert into apoteka_lek (id,cena,kolicina,promotivna_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'540','23','500','5','12');



--insert into dermatolog_apoteka(id,cena,radno_vreme_kraj,radno_vreme_pocetak,apoteka_id,dermatolog_id) values (1,100.0,'2008-01-01 09:00:01','2008-01-01 17:00:01',1,1)
insert into dermatolog (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,prosecna_ocena) values (nextval('korisnik_seq'), 'dusan', 'dusan', 'Dusan', 'Brkic', 'dusanbrk@gmail.com', 'dusan-dusan', false, 'Alekse Santica 14', 'Novi Sad', 'Kraljevina Srbija', '0640857676','4');
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'token','10.3.1999','hajdukdusan99@gmail.com','Dusan','dule','Hajduk','dule',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');
insert into admin_apoteke (id,username,password,ime,prezime,email_adresa,first_login, apoteka_id, adresa, grad, drzava, broj_telefona,token) values (nextval('korisnik_seq'),'nikola','nikola','Nikola','Petrovic','dusanbrk@gmail.com','false',1,'Milana Mice Petrovica 55', 'Kraljevo', 'Kraljevina Srbija', '0651212333','nikola-nikola');
insert into farmaceut (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,apoteka_id,prosecna_ocena,radno_vreme_pocetak,radno_vreme_kraj) values (nextval('korisnik_seq'), 'dzon', 'dzon', 'Dzon', 'Bosnic', 'johnbosnitch@gmail.com', 'dzon-dzon', false, 'The Chetnik immigration 133 Block 14/33', 'Toronto', 'Canada', '+1/250-5550199','1','3.5','2008-01-01 09:00:01','2008-01-01 17:00:01');

--dermatolog_apoteka
insert into dermatolog_apoteka(id,radno_vreme_kraj,radno_vreme_pocetak,apoteka_id,dermatolog_id) values (nextval('dermatolog_apoteka_seq'),'2008-01-01 17:00:01','2008-01-01 09:00:01',1,1);

--pacijenti
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'osman-osman','1998.3.15.','dusanbrk@gmail.com','Semir','osman','Osmanagic','osman',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'token2','11.4.1999','dusanbrk@gmail.com','Miloslav','miloslav','Samardzic','miloslav',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'token3','10.11.1955','mojEmail3@gmail.com','Miroljub','miroljub','Petrovic','miroljub',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'tesa-tesa','4.7.1944','dusanbrk@gmail.com','Tesa','tesa','Tesanovic','tesa',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');
insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni, adresa, grad, drzava, broj_telefona) values (nextval('korisnik_seq'),'token4','3.1.1988','dusanbrk@gmail.com','Slavisa','slavisa','Miljkovic','slavisa',0,10, 'Tese Tesanovica 12', 'Sid', 'Republika Srbija', '0641212121');

insert into dermatolog (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,prosecna_ocena) values (nextval('korisnik_seq'), 'dusan1', 'dusan1', 'Dusan', 'Brkic', 'dusanbrk1@gmail.com', 'dusan1-dusan1', true, 'Alekse Santica 14', 'Novi Sad', 'Kraljevina Srbija', '0640857676','4.5');
insert into dermatolog (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,prosecna_ocena) values (nextval('korisnik_seq'), 'dusan2', 'dusan2', 'Nikola', 'Brkic', 'dusanbrk2@gmail.com', 'dusan2-dusan2', true, 'Alekse Santica 14', 'Novi Sad', 'Kraljevina Srbija', '0640857676','1.5');
insert into dermatolog_apoteka(id,radno_vreme_kraj,radno_vreme_pocetak,apoteka_id,dermatolog_id) values (nextval('dermatolog_apoteka_seq'),'2008-01-01 17:00:01','2008-01-01 09:00:01',2,10);
insert into farmaceut (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,apoteka_id,prosecna_ocena,radno_vreme_pocetak,radno_vreme_kraj) values (nextval('korisnik_seq'), 'dzon1', 'dzon1', 'Nikola', 'Pera', 'johnbosnitch1@gmail.com', 'dzon1-dzon1', false, 'The Chetnik immigration 133 Block 14/33', 'Toronto', 'Canada', '+1/250-5550199','1','2.5','2008-01-01 02:00:01','2008-01-01 08:00:01');
insert into farmaceut (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,apoteka_id,prosecna_ocena,radno_vreme_pocetak,radno_vreme_kraj) values (nextval('korisnik_seq'), 'dzon12', 'dzon12', 'Nikola', 'Pera', 'johnbosnitch2@gmail.com', 'dzon12-dzon12', false, 'The Chetnik immigration 133 Block 14/33', 'Toronto', 'Canada', '+1/250-5550199','2','2.5','2008-01-01 02:00:01','2008-01-01 08:00:01');
insert into farmaceut (id, username, password, ime, prezime, email_adresa, token, first_login, adresa, grad, drzava, broj_telefona,apoteka_id,prosecna_ocena,radno_vreme_pocetak,radno_vreme_kraj) values (nextval('korisnik_seq'), 'mitar', 'mitrovic', 'Mitar', 'Mitrovic', 'johnbosnitch1@gmail.com', 'mitar-mitrovic', false, 'The Chetnik immigration 133 Block 14/33', 'Toronto', 'Canada', '+1/250-5550199','1','4.5','2008-01-01 02:00:01','2008-01-01 08:00:01');
--alergije
insert into pacijent_alergije(pacijent_id, alergije_id) values (8, 3);

--pregledi
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Bude ga noci sajgonske.', '2021-04-13T12:00:01.0', true, 600000, '2021-04-13T11:45:01.0', '1', '1', '2', 440);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'), '1', '1', 1, 4);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'1', '2', 2, 13);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Pacijent ima vijetnamski sindrom.', '2021-04-13T13:00:01.0', true, 660000, '2021-04-13T12:45:01.0', '3', '1', '8', 1200);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'2', '3', 2, 1);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'2', '2', 1, 5);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'2', '4', 1, 6);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Bude ga noci sajgonske.', '2021-04-13T12:00:01.0', true, 600000, '2021-04-15T11:45:01.0', '1', '1', '6', 500);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'3', '1', 1, 3);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'3', '2',1, 4);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Pacijent ima vijetnamski sindrom.', '2021-04-14T13:00:01.0', true, 660000, '2021-04-14T12:45:01.0', '3', '1', '7', 440);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'4', '3', 2, 7);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'4', '2', 3, 24);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'4', '4', 1, 33);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Bude ga noci sajgonske.', '2021-04-14T15:00:01.0', true, 600000, '2021-04-14T14:45:01.0', '1', '1', '9', 660);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'5', '1', 4, 5);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'5', '2', 1, 1);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Pacijent ima vijetnamski sindrom.', '2021-04-19T13:00:01.0', true, 660000, '2021-04-19T12:45:01.0', '3', '1', '5', 780);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'6', '3', 1, 6);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'6', '2', 1, 5);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'6', '4', 2, 17);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Bude ga noci sajgonske.', '2021-04-21T12:00:01.0', true, 600000, '2021-04-21T11:45:01.0', '2', '1', '9', 920);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'7', '1', 1, 9);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'7', '2', 1, 10);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, 'Pacijent ima vijetnamski sindrom.', '2021-04-27T13:00:01.0', true, 660000, '2021-04-27T12:45:01.0', '2', '1', '8', 1030);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'8', '3', 1, 12);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'8', '2', 1, 10);
insert into pregled_lek (id, pregled_id, lek_id, kolicina, trajanje_terapije) values (nextval('pregled_lek_seq'),'8', '4', 1, 5);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), false, '', '2021-05-12T13:00:01.0', false, 0, '2021-05-12T12:45:01.0', '4', '1', '7', 300);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, '', '2021-05-15T13:15:01.0', false, 0, '2021-05-15T12:45:01.0', '4', '1', '8', 250);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, '', '2021-05-16T13:00:01.0', false, 0, '2021-05-16T11:45:01.0', '1', '1', '9', 550);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, '', '2021-05-15T13:15:01.0', false, 0, '2021-05-15T12:45:01.0', '4', '4', '8', 250);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, '', '2021-05-16T13:00:01.0', false, 0, '2021-05-16T11:45:01.0', '1', '4', '9', 550);






insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, cena) values (nextval('pregled_seq'), false, '', '2021-04-13T13:00:01.0', false, 600000, '2021-04-13T11:45:01.0', '1', '1', 440);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, cena) values (nextval('pregled_seq'), false, '', '2021-05-15T13:45:01.0', false, 600000, '2021-05-15T11:45:01.0', '1', '1', 1440);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, cena) values (nextval('pregled_seq'), false, '', '2021-06-15T13:45:01.0', false, 600000, '2021-06-15T11:45:01.0', '1', '1', 1440);
insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, cena) values (nextval('pregled_seq'), false, '', '2021-04-15T12:35:01.0', false, 600000, '2021-04-18T11:45:01.0', '2', '1', 1440);

insert into pregled (id, pregled_zakazan, dijagnoza, kraj, pregled_obavljen, trajanje, vreme, apoteka_id, zdravstveni_radnik_id, pacijent_id, cena) values (nextval('pregled_seq'), true, '', '2021-05-16T13:00:01.0', true, 0, '2021-05-16T11:45:01.0', '1', '1', '2', 550);




insert into rezervacija (id, preuzeto, isteklo, rok_ponude, apoteka_id, pacijent_id) values (nextval('rezervacija_seq'), 'false', 'false', '2021-05-15 02:00:00', '1', '2');
insert into rezervacija (id, preuzeto, isteklo, rok_ponude, apoteka_id, pacijent_id) values (nextval('rezervacija_seq'), 'true', 'false', '2021-05-20 02:00:00', '1', '2');
insert into rezervacija (id, preuzeto, isteklo, rok_ponude, apoteka_id, pacijent_id) values (nextval('rezervacija_seq'), 'true', 'false', '2021-04-15 02:00:00', '1', '2');

insert into rezervacija_lek (id, kolicina, lek_id, rezervacija_id) values (nextval('rezervacija_lek_seq'), '2', '2', '1');
insert into rezervacija_lek (id, kolicina, lek_id, rezervacija_id) values (nextval('rezervacija_lek_seq'), '5', '1', '1');
insert into rezervacija_lek (id, kolicina, lek_id, rezervacija_id) values (nextval('rezervacija_lek_seq'), '1', '3', '2');
insert into rezervacija_lek (id, kolicina, lek_id, rezervacija_id) values (nextval('rezervacija_lek_seq'), '2', '1', '3');

--narudzbine
insert into narudzbenica(id,preuzet,rok_ponude,apoteka_id) values (nextval('narudzbenica_seq'),'true','2021-05-16 02:00:00','1');
insert into narudzbenica(id,preuzet,rok_ponude,apoteka_id) values (nextval('narudzbenica_seq'),'true','2021-05-17 03:00:00','1');
insert into narudzbenica(id,preuzet,rok_ponude,apoteka_id) values (nextval('narudzbenica_seq'),'false','2021-05-15 03:00:00','1');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),1,'1','1');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),10,'2','1');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),10,'2','1');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),10,'3','1');

insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),1,'1','2');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),120,'2','2');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),20,'2','2');
insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),20,'3','2');

insert into narudzbenica_lek(id,kolicina,lek_id,narudzbenica_id) values (nextval('narudzbenica_lek_seq'),10,'3','3');

--pretplate

insert into pretplata(id,pacijent_id,apoteka_id) values (nextval('pretplata_seq'),'5','1');
insert into pretplata(id,pacijent_id,apoteka_id) values (nextval('pretplata_seq'),'6','1');