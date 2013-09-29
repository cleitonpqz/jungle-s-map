# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table autor (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_autor primary key (id))
;

create table bioma (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_bioma primary key (id))
;

create table compartimento (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_compartimento primary key (id))
;

create table compartimento_local (
  id                        bigint not null,
  qtd_biomassa              float,
  qtd_carbono               float,
  compartimento_id          bigint,
  local_id                  bigint,
  constraint pk_compartimento_local primary key (id))
;

create table coordenada (
  id                        bigint not null,
  latitude                  float,
  longitude                 float,
  local_id                  bigint,
  constraint pk_coordenada primary key (id))
;

create table disponibilidade (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_disponibilidade primary key (id))
;

create table espacamento (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_espacamento primary key (id))
;

create table estado (
  ibge                      bigint not null,
  sigla                     varchar(2),
  nome                      varchar(255),
  area                      numeric(15,5),
  constraint pk_estado primary key (ibge))
;

create table formacao (
  id                        bigint not null,
  nome                      varchar(255),
  bioma_id                  bigint,
  constraint pk_formacao primary key (id))
;

create table local (
  id                        bigint not null,
  descricao                 varchar(255),
  area_total                float,
  trabalho_cientifico_id    bigint,
  formacao_id               bigint,
  espacamento_id            bigint,
  constraint pk_local primary key (id))
;

create table metodo_quantificacao_biomassa (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_metodo_quantificacao_biomassa primary key (id))
;

create table metodo_quantificacao_carbono (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_metodo_quantificacao_carbono primary key (id))
;

create table municipio (
  ibge                      bigint not null,
  nome                      varchar(255),
  uf                        bigint,
  area                      numeric(15,5),
  constraint pk_municipio primary key (ibge))
;

create table municipio_local (
  id                        bigint not null,
  principal                 boolean,
  municipio_ibge            bigint,
  local_id                  bigint,
  constraint pk_municipio_local primary key (id))
;

create table pais (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_pais primary key (id))
;

create table trabalho_cientifico (
  id                        bigint not null,
  autor_id                  bigint,
  disponibilidade_id        bigint,
  metodo_quantificacao_biomassa_id bigint,
  metodo_quantificacao_carbono_id bigint,
  constraint pk_trabalho_cientifico primary key (id))
;

create sequence autor_seq;

create sequence bioma_seq;

create sequence compartimento_seq;

create sequence compartimento_local_seq;

create sequence coordenada_seq;

create sequence disponibilidade_seq;

create sequence espacamento_seq;

create sequence estado_seq;

create sequence formacao_seq;

create sequence local_seq;

create sequence metodo_quantificacao_biomassa_seq;

create sequence metodo_quantificacao_carbono_seq;

create sequence municipio_seq;

create sequence municipio_local_seq;

create sequence pais_seq;

create sequence trabalho_cientifico_seq;

alter table compartimento_local add constraint fk_compartimento_local_compart_1 foreign key (compartimento_id) references compartimento (id);
create index ix_compartimento_local_compart_1 on compartimento_local (compartimento_id);
alter table compartimento_local add constraint fk_compartimento_local_local_2 foreign key (local_id) references local (id);
create index ix_compartimento_local_local_2 on compartimento_local (local_id);
alter table coordenada add constraint fk_coordenada_local_3 foreign key (local_id) references local (id);
create index ix_coordenada_local_3 on coordenada (local_id);
alter table formacao add constraint fk_formacao_bioma_4 foreign key (bioma_id) references bioma (id);
create index ix_formacao_bioma_4 on formacao (bioma_id);
alter table local add constraint fk_local_trabalho_cientifico_5 foreign key (trabalho_cientifico_id) references trabalho_cientifico (id);
create index ix_local_trabalho_cientifico_5 on local (trabalho_cientifico_id);
alter table local add constraint fk_local_formacao_6 foreign key (formacao_id) references formacao (id);
create index ix_local_formacao_6 on local (formacao_id);
alter table local add constraint fk_local_espacamento_7 foreign key (espacamento_id) references espacamento (id);
create index ix_local_espacamento_7 on local (espacamento_id);
alter table municipio add constraint fk_municipio_uf_8 foreign key (uf) references estado (ibge);
create index ix_municipio_uf_8 on municipio (uf);
alter table municipio_local add constraint fk_municipio_local_municipio_9 foreign key (municipio_ibge) references municipio (ibge);
create index ix_municipio_local_municipio_9 on municipio_local (municipio_ibge);
alter table municipio_local add constraint fk_municipio_local_local_10 foreign key (local_id) references local (id);
create index ix_municipio_local_local_10 on municipio_local (local_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_autor_11 foreign key (autor_id) references autor (id);
create index ix_trabalho_cientifico_autor_11 on trabalho_cientifico (autor_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_dispon_12 foreign key (disponibilidade_id) references disponibilidade (id);
create index ix_trabalho_cientifico_dispon_12 on trabalho_cientifico (disponibilidade_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_13 foreign key (metodo_quantificacao_biomassa_id) references metodo_quantificacao_biomassa (id);
create index ix_trabalho_cientifico_metodo_13 on trabalho_cientifico (metodo_quantificacao_biomassa_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_14 foreign key (metodo_quantificacao_carbono_id) references metodo_quantificacao_carbono (id);
create index ix_trabalho_cientifico_metodo_14 on trabalho_cientifico (metodo_quantificacao_carbono_id);



# --- !Downs

drop table if exists autor cascade;

drop table if exists bioma cascade;

drop table if exists compartimento cascade;

drop table if exists compartimento_local cascade;

drop table if exists coordenada cascade;

drop table if exists disponibilidade cascade;

drop table if exists espacamento cascade;

drop table if exists estado cascade;

drop table if exists formacao cascade;

drop table if exists local cascade;

drop table if exists metodo_quantificacao_biomassa cascade;

drop table if exists metodo_quantificacao_carbono cascade;

drop table if exists municipio cascade;

drop table if exists municipio_local cascade;

drop table if exists pais cascade;

drop table if exists trabalho_cientifico cascade;

drop sequence if exists autor_seq;

drop sequence if exists bioma_seq;

drop sequence if exists compartimento_seq;

drop sequence if exists compartimento_local_seq;

drop sequence if exists coordenada_seq;

drop sequence if exists disponibilidade_seq;

drop sequence if exists espacamento_seq;

drop sequence if exists estado_seq;

drop sequence if exists formacao_seq;

drop sequence if exists local_seq;

drop sequence if exists metodo_quantificacao_biomassa_seq;

drop sequence if exists metodo_quantificacao_carbono_seq;

drop sequence if exists municipio_seq;

drop sequence if exists municipio_local_seq;

drop sequence if exists pais_seq;

drop sequence if exists trabalho_cientifico_seq;

