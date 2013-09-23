# Insert estado
 
# --- !Ups
begin;
insert into estado (ibge, sigla, nome, area) values (11,'RO','Rondônia',237576.16700);
insert into estado (ibge, sigla, nome, area) values (12,'AC','Acre',152581.38800);
insert into estado (ibge, sigla, nome, area) values (13,'AM','Amazonas',1570745.68000);
insert into estado (ibge, sigla, nome, area) values (14,'RR','Roraima',224298.98000);
insert into estado (ibge, sigla, nome, area) values (16,'AP','Amapá',142814.58500);
insert into estado (ibge, sigla, nome, area) values (17,'TO','Tocantins',277620.91400);
insert into estado (ibge, sigla, nome, area) values (22,'PI','Piauí',251529.19400);
insert into estado (ibge, sigla, nome, area) values (23,'CE','Ceará',148825.60200);
insert into estado (ibge, sigla, nome, area) values (24,'RN','Rio Grande Do Norte',52796.80200);
insert into estado (ibge, sigla, nome, area) values (25,'PB','Paraíba',56439.83800);
insert into estado (ibge, sigla, nome, area) values (27,'AL','Alagoas',27767.65500);
insert into estado (ibge, sigla, nome, area) values (28,'SE','Sergipe',21910.34800);
insert into estado (ibge, sigla, nome, area) values (29,'BA','Bahia',564692.65800);
insert into estado (ibge, sigla, nome, area) values (31,'MG','Minas Gerais',586528.29300);
insert into estado (ibge, sigla, nome, area) values (41,'PR','Paraná',199314.85000);
insert into estado (ibge, sigla, nome, area) values (43,'RS','Rio Grande Do Sul',281748.15500);
insert into estado (ibge, sigla, nome, area) values (50,'MS','Mato Grosso Do Sul',357124.96200);
insert into estado (ibge, sigla, nome, area) values (51,'MT','Mato Grosso',903357.71500);
insert into estado (ibge, sigla, nome, area) values (52,'GO','Goiás',340086.70300);
insert into estado (ibge, sigla, nome, area) values (53,'DF','Distrito Federal',5801.93700);
insert into estado (ibge, sigla, nome, area) values (15,'PA','Pará',1247689.51500);
insert into estado (ibge, sigla, nome, area) values (21,'MA','Maranhão',331983.29300);
insert into estado (ibge, sigla, nome, area) values (33,'RJ','Rio de Janeiro',43696.05600);
insert into estado (ibge, sigla, nome, area) values (35,'SP','São Paulo',248209.42700);
insert into estado (ibge, sigla, nome, area) values (42,'SC','Santa Catarina',95346.18100);
insert into estado (ibge, sigla, nome, area) values (26,'PE','Pernambuco',98311.61600);
insert into estado (ibge, sigla, nome, area) values (32,'ES','Espírito Santo',46077.52200);
commit;
 
# --- !Downs
delete from estado;