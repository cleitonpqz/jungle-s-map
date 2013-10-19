# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table autor (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_autor primary key (id))
;

create table autor_modelo (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_autor_modelo primary key (id))
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

create table equacao (
  id                        bigint not null,
  expressao                 varchar(255),
  variavel_a                varchar(255),
  valor_variavel_a          float,
  variavel_b                varchar(255),
  valor_variavel_b          float,
  variavel_c                varchar(255),
  valor_variavel_c          float,
  variavel_d                varchar(255),
  valor_variavel_d          float,
  variavel_e                varchar(255),
  valor_variavel_e          float,
  variavel_f                varchar(255),
  valor_variavel_f          float,
  variavel_g                varchar(255),
  valor_variavel_g          float,
  variavel_h                varchar(255),
  valor_variavel_h          float,
  variavel_i                varchar(255),
  valor_variavel_i          float,
  variavel_j                varchar(255),
  valor_variavel_j          float,
  variavel_k                varchar(255),
  valor_variavel_k          float,
  variavel_l                varchar(255),
  valor_variavel_l          float,
  variavel_m                varchar(255),
  valor_variavel_m          float,
  variavel_n                varchar(255),
  valor_variavel_n          float,
  variavel_o                varchar(255),
  valor_variavel_o          float,
  variavel_p                varchar(255),
  valor_variavel_p          float,
  variavel_q                varchar(255),
  valor_variavel_q          float,
  variavel_r                varchar(255),
  valor_variavel_r          float,
  variavel_s                varchar(255),
  valor_variavel_s          float,
  variavel_t                varchar(255),
  valor_variavel_t          float,
  variavel_u                varchar(255),
  valor_variavel_u          float,
  variavel_v                varchar(255),
  valor_variavel_v          float,
  variavel_w                varchar(255),
  valor_variavel_w          float,
  variavel_x                varchar(255),
  valor_variavel_x          float,
  variavel_y                varchar(255),
  valor_variavel_y          float,
  variavel_z                varchar(255),
  valor_variavel_z          float,
  trabalho_variavel_interesse_id bigint,
  constraint pk_equacao primary key (id))
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

create table modelo (
  id                        bigint not null,
  expressao                 varchar(255),
  ano                       integer,
  autor_modelo_id           bigint,
  trabalho_variavel_interesse_id bigint,
  constraint pk_modelo primary key (id))
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
  ano                       integer,
  constraint pk_trabalho_cientifico primary key (id))
;

create table trabalho_variavel_interesse (
  id                        bigint not null,
  trabalho_cientifico_id    bigint,
  variavel_interesse_id     bigint,
  constraint pk_trabalho_variavel_interesse primary key (id))
;

create table variavel (
  id                        bigint not null,
  sigla                     varchar(255),
  nome                      varchar(255),
  constraint pk_variavel primary key (id))
;

create table variavel_interesse (
  id                        bigint not null,
  sigla                     varchar(255),
  nome                      varchar(255),
  constraint pk_variavel_interesse primary key (id))
;

create sequence autor_seq;

create sequence autor_modelo_seq;

create sequence bioma_seq;

create sequence compartimento_seq;

create sequence compartimento_local_seq;

create sequence coordenada_seq;

create sequence disponibilidade_seq;

create sequence equacao_seq;

create sequence espacamento_seq;

create sequence estado_seq;

create sequence formacao_seq;

create sequence local_seq;

create sequence metodo_quantificacao_biomassa_seq;

create sequence metodo_quantificacao_carbono_seq;

create sequence modelo_seq;

create sequence municipio_seq;

create sequence municipio_local_seq;

create sequence pais_seq;

create sequence trabalho_cientifico_seq;

create sequence trabalho_variavel_interesse_seq;

create sequence variavel_seq;

create sequence variavel_interesse_seq;

alter table compartimento_local add constraint fk_compartimento_local_compart_1 foreign key (compartimento_id) references compartimento (id);
create index ix_compartimento_local_compart_1 on compartimento_local (compartimento_id);
alter table compartimento_local add constraint fk_compartimento_local_local_2 foreign key (local_id) references local (id);
create index ix_compartimento_local_local_2 on compartimento_local (local_id);
alter table coordenada add constraint fk_coordenada_local_3 foreign key (local_id) references local (id);
create index ix_coordenada_local_3 on coordenada (local_id);
alter table equacao add constraint fk_equacao_trabalho_variavel_i_4 foreign key (trabalho_variavel_interesse_id) references trabalho_variavel_interesse (id);
create index ix_equacao_trabalho_variavel_i_4 on equacao (trabalho_variavel_interesse_id);
alter table formacao add constraint fk_formacao_bioma_5 foreign key (bioma_id) references bioma (id);
create index ix_formacao_bioma_5 on formacao (bioma_id);
alter table local add constraint fk_local_trabalho_cientifico_6 foreign key (trabalho_cientifico_id) references trabalho_cientifico (id);
create index ix_local_trabalho_cientifico_6 on local (trabalho_cientifico_id);
alter table local add constraint fk_local_formacao_7 foreign key (formacao_id) references formacao (id);
create index ix_local_formacao_7 on local (formacao_id);
alter table local add constraint fk_local_espacamento_8 foreign key (espacamento_id) references espacamento (id);
create index ix_local_espacamento_8 on local (espacamento_id);
alter table modelo add constraint fk_modelo_autor_modelo_9 foreign key (autor_modelo_id) references autor_modelo (id);
create index ix_modelo_autor_modelo_9 on modelo (autor_modelo_id);
alter table modelo add constraint fk_modelo_trabalho_variavel_i_10 foreign key (trabalho_variavel_interesse_id) references trabalho_variavel_interesse (id);
create index ix_modelo_trabalho_variavel_i_10 on modelo (trabalho_variavel_interesse_id);
alter table municipio add constraint fk_municipio_uf_11 foreign key (uf) references estado (ibge);
create index ix_municipio_uf_11 on municipio (uf);
alter table municipio_local add constraint fk_municipio_local_municipio_12 foreign key (municipio_ibge) references municipio (ibge);
create index ix_municipio_local_municipio_12 on municipio_local (municipio_ibge);
alter table municipio_local add constraint fk_municipio_local_local_13 foreign key (local_id) references local (id);
create index ix_municipio_local_local_13 on municipio_local (local_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_autor_14 foreign key (autor_id) references autor (id);
create index ix_trabalho_cientifico_autor_14 on trabalho_cientifico (autor_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_dispon_15 foreign key (disponibilidade_id) references disponibilidade (id);
create index ix_trabalho_cientifico_dispon_15 on trabalho_cientifico (disponibilidade_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_16 foreign key (metodo_quantificacao_biomassa_id) references metodo_quantificacao_biomassa (id);
create index ix_trabalho_cientifico_metodo_16 on trabalho_cientifico (metodo_quantificacao_biomassa_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_17 foreign key (metodo_quantificacao_carbono_id) references metodo_quantificacao_carbono (id);
create index ix_trabalho_cientifico_metodo_17 on trabalho_cientifico (metodo_quantificacao_carbono_id);
alter table trabalho_variavel_interesse add constraint fk_trabalho_variavel_interess_18 foreign key (trabalho_cientifico_id) references trabalho_cientifico (id);
create index ix_trabalho_variavel_interess_18 on trabalho_variavel_interesse (trabalho_cientifico_id);
alter table trabalho_variavel_interesse add constraint fk_trabalho_variavel_interess_19 foreign key (variavel_interesse_id) references variavel_interesse (id);
create index ix_trabalho_variavel_interess_19 on trabalho_variavel_interesse (variavel_interesse_id);



# --- !Downs

drop table if exists autor cascade;

drop table if exists autor_modelo cascade;

drop table if exists bioma cascade;

drop table if exists compartimento cascade;

drop table if exists compartimento_local cascade;

drop table if exists coordenada cascade;

drop table if exists disponibilidade cascade;

drop table if exists equacao cascade;

drop table if exists espacamento cascade;

drop table if exists estado cascade;

drop table if exists formacao cascade;

drop table if exists local cascade;

drop table if exists metodo_quantificacao_biomassa cascade;

drop table if exists metodo_quantificacao_carbono cascade;

drop table if exists modelo cascade;

drop table if exists municipio cascade;

drop table if exists municipio_local cascade;

drop table if exists pais cascade;

drop table if exists trabalho_cientifico cascade;

drop table if exists trabalho_variavel_interesse cascade;

drop table if exists variavel cascade;

drop table if exists variavel_interesse cascade;

drop sequence if exists autor_seq;

drop sequence if exists autor_modelo_seq;

drop sequence if exists bioma_seq;

drop sequence if exists compartimento_seq;

drop sequence if exists compartimento_local_seq;

drop sequence if exists coordenada_seq;

drop sequence if exists disponibilidade_seq;

drop sequence if exists equacao_seq;

drop sequence if exists espacamento_seq;

drop sequence if exists estado_seq;

drop sequence if exists formacao_seq;

drop sequence if exists local_seq;

drop sequence if exists metodo_quantificacao_biomassa_seq;

drop sequence if exists metodo_quantificacao_carbono_seq;

drop sequence if exists modelo_seq;

drop sequence if exists municipio_seq;

drop sequence if exists municipio_local_seq;

drop sequence if exists pais_seq;

drop sequence if exists trabalho_cientifico_seq;

drop sequence if exists trabalho_variavel_interesse_seq;

drop sequence if exists variavel_seq;

drop sequence if exists variavel_interesse_seq;

