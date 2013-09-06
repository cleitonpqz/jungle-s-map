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

create table formacao (
  id                        bigint not null,
  nome                      varchar(255),
  bioma_id                  bigint,
  constraint pk_formacao primary key (id))
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

create sequence disponibilidade_seq;

create sequence espacamento_seq;

create sequence formacao_seq;

create sequence metodo_quantificacao_biomassa_seq;

create sequence metodo_quantificacao_carbono_seq;

create sequence trabalho_cientifico_seq;

alter table formacao add constraint fk_formacao_bioma_1 foreign key (bioma_id) references bioma (id);
create index ix_formacao_bioma_1 on formacao (bioma_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_autor_2 foreign key (autor_id) references autor (id);
create index ix_trabalho_cientifico_autor_2 on trabalho_cientifico (autor_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_disponi_3 foreign key (disponibilidade_id) references disponibilidade (id);
create index ix_trabalho_cientifico_disponi_3 on trabalho_cientifico (disponibilidade_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo__4 foreign key (metodo_quantificacao_biomassa_id) references metodo_quantificacao_biomassa (id);
create index ix_trabalho_cientifico_metodo__4 on trabalho_cientifico (metodo_quantificacao_biomassa_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo__5 foreign key (metodo_quantificacao_carbono_id) references metodo_quantificacao_carbono (id);
create index ix_trabalho_cientifico_metodo__5 on trabalho_cientifico (metodo_quantificacao_carbono_id);



# --- !Downs

drop table if exists autor cascade;

drop table if exists bioma cascade;

drop table if exists disponibilidade cascade;

drop table if exists espacamento cascade;

drop table if exists formacao cascade;

drop table if exists metodo_quantificacao_biomassa cascade;

drop table if exists metodo_quantificacao_carbono cascade;

drop table if exists trabalho_cientifico cascade;

drop sequence if exists autor_seq;

drop sequence if exists bioma_seq;

drop sequence if exists disponibilidade_seq;

drop sequence if exists espacamento_seq;

drop sequence if exists formacao_seq;

drop sequence if exists metodo_quantificacao_biomassa_seq;

drop sequence if exists metodo_quantificacao_carbono_seq;

drop sequence if exists trabalho_cientifico_seq;

