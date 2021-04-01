insert into dermatolog (id, username, password, ime, prezime, email_adresa) values (nextval('korisnik_seq'), 'dusan', 'dusan', 'Dusan', 'Brkic', 'dusanbrk@gmail.com')

insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'temp','temp','123','temp')
insert into lek (id,naziv,sastav,sifra,uputstvo) values (nextval('lek_seq'),'temp1','temp1','1234','temp1')
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Marka Markovica 22, Subotica','Apoteka Markovic','opis apoteke',3.6)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Lazara Obilica 15, Beograd','Apoteka Djincic','opis apoteke',4.5)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Cara Lazara 103, Novi Sad','Apoteka Lazarevic','opis apoteke',2.2)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Zmaj Jovina 45, Sid','Apoteka Zmaj','opis apoteke',4.8)
insert into apoteka (id,adresa,naziv,opis,prosecna_ocena) values (nextval('apoteka_seq'),'Matije Grupca 35, Novi Sad','Apoteka Antic','opis apoteke',3.2)

insert into pacijent (id,token,datum_rodjenja,email_adresa,ime,password,prezime,username,br_penala,poeni) values (nextval('korisnik_seq'),'token','10.3.1999','mojEmail@gmail.com','dule','dule','dudulic','dule',0,10)

insert into admin_apoteke (id,username,password,ime,prezime,email_adresa,apoteka_id) values (nextval('korisnik_seq'),'nikola','nikola','Nikola','Petrovic','nikola@gmail.com',1)

insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'100','10','50','1','1')
insert into apoteka_lek (id,cena,kolicina,stara_cena,apoteka_id,lek_id) values (nextval('apoteka_lek_seq'),'100','10','50','1','2')
--insert into dermatolog_apoteka(id,cena,radno_vreme_kraj,radno_vreme_pocetak,apoteka_id,dermatolog_id) values (1,100.0,'2008-01-01 00:00:01','2008-01-01 00:00:01',1,1)