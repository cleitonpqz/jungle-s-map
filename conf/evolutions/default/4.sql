begin;
insert into variavel_interesse (id, sigla, nome) values (1,'B','Biomassa');
insert into variavel_interesse (id, sigla, nome) values (2,'C','Carbono');
insert into variavel_interesse (id, sigla, nome) values (3,'V','Volume');
commit;

delete from variavel_interesse;