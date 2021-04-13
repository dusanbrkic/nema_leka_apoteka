insert into dermatolog (id, username, password, ime, prezime, email_adresa) values (nextval('korisnik_seq'), 'dusan', 'dusan', 'Dusan', 'Brkic', 'dusanbrk@gmail.com')

insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Brufen','bazinga 2mg','1','Piti jednom dnevno posle obroka')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Strepsils','lozenga 1.2mg+o.6mg, blister 2X8kom','2','Max 3 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Gynaflor','vaginalna tableta; 1x 10exp8 CFU+0.03mg','3','Koristiti 2 puta')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Aspirin','oblozena tableta; 500mg','4','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Andol','tableta 75mg','5','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Viralex','tableta: 200mg','6','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Humira','rastvor za injekciju u napunjenom penu 50mg','7','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Aspirin','oblozena tableta; 1500mg','8','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Vaminolast','rastvor za infuziju','9','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Alpha D3','tableta za alfe','10','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Helex','oblozena tableta; 500mg','11','Max 2 puta dnevno')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'Viagra','oblozena tableta; 40mg; 10kom','12','Za dobrog stojka')

insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Marka Markovica 22, Subotica','Apoteka Markovic','opis apoteke',3.6)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Lazara Obilica 15, Beograd','Apoteka Djincic','opis apoteke',4.5)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Cara Lazara 103, Novi Sad','Apoteka Lazarevic','opis apoteke',2.2)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Zmaj Jovina 45, Sid','Apoteka Zmaj','opis apoteke',4.8)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Matije Grupca 35, Novi Sad','Apoteka Antic','opis apoteke',3.2)

insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni) values (nextval('korisnik_seq'),'token','10.3.1999','mojEmail@gmail.com','dule','dule','dudulic','dule',0,10)

insert into admin_apoteke (id,username,password,ime,prezime,email_adresa,first_login, apoteka_id) values (nextval('korisnik_seq'),'nikola','nikola','Nikola','Petrovic','nikola@gmail.com','true',1)

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'100','10','50','1','1')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'500','33','450','1','2')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1000','4','800','1','3')

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'800','140','450','2','4')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1800','43','1450','2','5')

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'80','200','45','3','6')

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'1200','58','1200','4','7')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'600','15','670','4','8')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'700','23','770','4','9')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'300','74','340','4','10')

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'320','5','300','5','11')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'540','23','500','5','12')

--insert into dermatolog_apoteka(id,cena,radno_vreme_kraj,radno_vreme_pocetak,apoteka_id,dermatolog_id) values (1,100.0,'2008-01-01 00:00:01','2008-01-01 00:00:01',1,1)