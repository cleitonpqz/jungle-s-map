# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table arvore (
  id                        bigint not null,
  parcela_id                bigint,
  num_arvore                bigint,
  qtd_biomassa_obs          float,
  qtd_carbono_obs           float,
  qtd_carbono_est           float,
  qtd_volume_obs            float,
  qtd_volume_est            float,
  qtd_biomassa_est          float,
  constraint pk_arvore primary key (id))
;

create table arvore_ajuste (
  id                        bigint not null,
  num_arvore                bigint,
  equacao_id                bigint,
  qtd_biomassa_obs          float,
  qtd_carbono_obs           float,
  qtd_carbono_est           float,
  qtd_volume_obs            float,
  qtd_volume_est            float,
  qtd_biomassa_est          float,
  local_id                  bigint,
  constraint pk_arvore_ajuste primary key (id))
;

create table arvore_ajuste_variavel (
  id                        bigint not null,
  valor                     float,
  arvore_ajuste_id          bigint,
  variavel_id               bigint,
  constraint pk_arvore_ajuste_variavel primary key (id))
;

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
  expressao_modelo          varchar(255),
  qtd_coeficientes          integer,
  autor_modelo_id           bigint,
  variavel_interesse_id     bigint,
  constraint pk_equacao primary key (id))
;

create table equacao_variavel (
  id                        float not null,
  valor                     float,
  equacao_id                bigint,
  variavel_id               bigint,
  constraint pk_equacao_variavel primary key (id))
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
  area_parcela              float,
  qtde_biomassa             float,
  qtde_carbono              float,
  qtde_volume               float,
  tipo_estimativa_id        bigint,
  trabalho_cientifico_id    bigint,
  formacao_id               bigint,
  espacamento_id            bigint,
  constraint pk_local primary key (id))
;

create table local_detalhe_biomassa (
  id                        bigint not null,
  local_id                  bigint not null,
  num_local                 bigint,
  qtde_biomassa_min         float,
  qtde_biomassa_med         float,
  qtde_biomassa_max         float,
  media_parcela             float,
  variancia                 float,
  desvio_padrao             float,
  variancia_media           float,
  erro_padrao               float,
  coeficiente_variacao      float,
  erro_absoluto             float,
  erro_relativo             float,
  intervalo_confianca_min_parcela float,
  intervalo_confianca_max_parcela float,
  constraint pk_local_detalhe_biomassa primary key (id))
;

create table local_detalhe_carbono (
  id                        bigint not null,
  local_id                  bigint not null,
  num_local                 bigint,
  qtde_carbono_min          float,
  qtde_carbono_med          float,
  qtde_carbono_max          float,
  media_parcela             float,
  variancia                 float,
  desvio_padrao             float,
  variancia_media           float,
  erro_padrao               float,
  coeficiente_variacao      float,
  erro_absoluto             float,
  erro_relativo             float,
  intervalo_confianca_min_parcela float,
  intervalo_confianca_max_parcela float,
  constraint pk_local_detalhe_carbono primary key (id))
;

create table local_detalhe_volume (
  id                        bigint not null,
  local_id                  bigint not null,
  num_local                 bigint,
  qtde_volume_min           float,
  qtde_volume_med           float,
  qtde_volume_max           float,
  media_parcela             float,
  variancia                 float,
  desvio_padrao             float,
  variancia_media           float,
  erro_padrao               float,
  coeficiente_variacao      float,
  erro_absoluto             float,
  erro_relativo             float,
  intervalo_confianca_min_parcela float,
  intervalo_confianca_max_parcela float,
  constraint pk_local_detalhe_volume primary key (id))
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

create table parcela (
  id                        bigint not null,
  local_id                  bigint,
  num_parcela               bigint,
  area                      float,
  biomassa                  float,
  qtd_biomassa_min          float,
  qtd_biomassa_med          float,
  qtd_biomassa_max          float,
  carbono                   float,
  qtd_carbono_min           float,
  qtd_carbono_med           float,
  qtd_arbono_max            float,
  volume                    float,
  qtd_volume_min            float,
  qtd_volume_med            float,
  qtd_volume_max            float,
  r2                        float,
  r2_ajust                  float,
  ia                        float,
  syx                       float,
  syx_perc                  float,
  fm                        float,
  syx_fm                    float,
  syx_fm_perc               float,
  constraint pk_parcela primary key (id))
;

create table termo (
  id                        float not null,
  expressao                 varchar(255),
  ordem                     integer,
  equacao_id                bigint,
  constraint pk_termo primary key (id))
;

create table tipo_estimativa (
  id                        bigint not null,
  descricao                 varchar(255),
  constraint pk_tipo_estimativa primary key (id))
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

create table trabalho_cientifico_equacao (
  id                        bigint not null,
  trabalho_cientifico_id    bigint,
  equacao_id                bigint,
  constraint pk_trabalho_cientifico_equacao primary key (id))
;

create table variavel (
  id                        bigint not null,
  sigla                     varchar(255),
  nome                      varchar(255),
  constraint pk_variavel primary key (id))
;

create table variavel_arvore (
  id                        bigint not null,
  arvore_id                 bigint,
  variavel_id               bigint,
  valor                     float,
  constraint pk_variavel_arvore primary key (id))
;

create table variavel_interesse (
  id                        bigint not null,
  sigla                     varchar(255),
  nome                      varchar(255),
  constraint pk_variavel_interesse primary key (id))
;

create sequence arvore_seq;

create sequence arvore_ajuste_seq;

create sequence arvore_ajuste_variavel_seq;

create sequence autor_seq;

create sequence autor_modelo_seq;

create sequence bioma_seq;

create sequence coordenada_seq;

create sequence disponibilidade_seq;

create sequence equacao_seq;

create sequence equacao_variavel_seq;

create sequence espacamento_seq;

create sequence estado_seq;

create sequence formacao_seq;

create sequence local_seq;

create sequence local_detalhe_biomassa_seq;

create sequence local_detalhe_carbono_seq;

create sequence local_detalhe_volume_seq;

create sequence metodo_quantificacao_biomassa_seq;

create sequence metodo_quantificacao_carbono_seq;

create sequence municipio_seq;

create sequence municipio_local_seq;

create sequence pais_seq;

create sequence parcela_seq;

create sequence termo_seq;

create sequence tipo_estimativa_seq;

create sequence trabalho_cientifico_seq;

create sequence trabalho_cientifico_equacao_seq;

create sequence variavel_seq;

create sequence variavel_arvore_seq;

create sequence variavel_interesse_seq;

alter table arvore add constraint fk_arvore_parcela_1 foreign key (parcela_id) references parcela (id);
create index ix_arvore_parcela_1 on arvore (parcela_id);
alter table arvore_ajuste add constraint fk_arvore_ajuste_local_2 foreign key (local_id) references local (id);
create index ix_arvore_ajuste_local_2 on arvore_ajuste (local_id);
alter table arvore_ajuste_variavel add constraint fk_arvore_ajuste_variavel_arvo_3 foreign key (arvore_ajuste_id) references arvore_ajuste (id);
create index ix_arvore_ajuste_variavel_arvo_3 on arvore_ajuste_variavel (arvore_ajuste_id);
alter table arvore_ajuste_variavel add constraint fk_arvore_ajuste_variavel_vari_4 foreign key (variavel_id) references variavel (id);
create index ix_arvore_ajuste_variavel_vari_4 on arvore_ajuste_variavel (variavel_id);
alter table coordenada add constraint fk_coordenada_local_5 foreign key (local_id) references local (id);
create index ix_coordenada_local_5 on coordenada (local_id);
alter table equacao add constraint fk_equacao_autor_modelo_6 foreign key (autor_modelo_id) references autor_modelo (id);
create index ix_equacao_autor_modelo_6 on equacao (autor_modelo_id);
alter table equacao add constraint fk_equacao_variavel_interesse_7 foreign key (variavel_interesse_id) references variavel_interesse (id);
create index ix_equacao_variavel_interesse_7 on equacao (variavel_interesse_id);
alter table equacao_variavel add constraint fk_equacao_variavel_equacao_8 foreign key (equacao_id) references equacao (id);
create index ix_equacao_variavel_equacao_8 on equacao_variavel (equacao_id);
alter table equacao_variavel add constraint fk_equacao_variavel_variavel_9 foreign key (variavel_id) references variavel (id);
create index ix_equacao_variavel_variavel_9 on equacao_variavel (variavel_id);
alter table formacao add constraint fk_formacao_bioma_10 foreign key (bioma_id) references bioma (id);
create index ix_formacao_bioma_10 on formacao (bioma_id);
alter table local add constraint fk_local_tipo_estimativa_11 foreign key (tipo_estimativa_id) references tipo_estimativa (id);
create index ix_local_tipo_estimativa_11 on local (tipo_estimativa_id);
alter table local add constraint fk_local_trabalho_cientifico_12 foreign key (trabalho_cientifico_id) references trabalho_cientifico (id);
create index ix_local_trabalho_cientifico_12 on local (trabalho_cientifico_id);
alter table local add constraint fk_local_formacao_13 foreign key (formacao_id) references formacao (id);
create index ix_local_formacao_13 on local (formacao_id);
alter table local add constraint fk_local_espacamento_14 foreign key (espacamento_id) references espacamento (id);
create index ix_local_espacamento_14 on local (espacamento_id);
alter table local_detalhe_biomassa add constraint fk_local_detalhe_biomassa_loc_15 foreign key (local_id) references local (id);
create index ix_local_detalhe_biomassa_loc_15 on local_detalhe_biomassa (local_id);
alter table local_detalhe_carbono add constraint fk_local_detalhe_carbono_loca_16 foreign key (local_id) references local (id);
create index ix_local_detalhe_carbono_loca_16 on local_detalhe_carbono (local_id);
alter table local_detalhe_volume add constraint fk_local_detalhe_volume_local_17 foreign key (local_id) references local (id);
create index ix_local_detalhe_volume_local_17 on local_detalhe_volume (local_id);
alter table municipio add constraint fk_municipio_uf_18 foreign key (uf) references estado (ibge);
create index ix_municipio_uf_18 on municipio (uf);
alter table municipio_local add constraint fk_municipio_local_municipio_19 foreign key (municipio_ibge) references municipio (ibge);
create index ix_municipio_local_municipio_19 on municipio_local (municipio_ibge);
alter table municipio_local add constraint fk_municipio_local_local_20 foreign key (local_id) references local (id);
create index ix_municipio_local_local_20 on municipio_local (local_id);
alter table parcela add constraint fk_parcela_local_21 foreign key (local_id) references local (id);
create index ix_parcela_local_21 on parcela (local_id);
alter table termo add constraint fk_termo_equacao_22 foreign key (equacao_id) references equacao (id);
create index ix_termo_equacao_22 on termo (equacao_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_autor_23 foreign key (autor_id) references autor (id);
create index ix_trabalho_cientifico_autor_23 on trabalho_cientifico (autor_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_dispon_24 foreign key (disponibilidade_id) references disponibilidade (id);
create index ix_trabalho_cientifico_dispon_24 on trabalho_cientifico (disponibilidade_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_25 foreign key (metodo_quantificacao_biomassa_id) references metodo_quantificacao_biomassa (id);
create index ix_trabalho_cientifico_metodo_25 on trabalho_cientifico (metodo_quantificacao_biomassa_id);
alter table trabalho_cientifico add constraint fk_trabalho_cientifico_metodo_26 foreign key (metodo_quantificacao_carbono_id) references metodo_quantificacao_carbono (id);
create index ix_trabalho_cientifico_metodo_26 on trabalho_cientifico (metodo_quantificacao_carbono_id);
alter table trabalho_cientifico_equacao add constraint fk_trabalho_cientifico_equaca_27 foreign key (trabalho_cientifico_id) references trabalho_cientifico (id);
create index ix_trabalho_cientifico_equaca_27 on trabalho_cientifico_equacao (trabalho_cientifico_id);
alter table trabalho_cientifico_equacao add constraint fk_trabalho_cientifico_equaca_28 foreign key (equacao_id) references equacao (id);
create index ix_trabalho_cientifico_equaca_28 on trabalho_cientifico_equacao (equacao_id);
alter table variavel_arvore add constraint fk_variavel_arvore_arvore_29 foreign key (arvore_id) references arvore (id);
create index ix_variavel_arvore_arvore_29 on variavel_arvore (arvore_id);
alter table variavel_arvore add constraint fk_variavel_arvore_variavel_30 foreign key (variavel_id) references variavel (id);
create index ix_variavel_arvore_variavel_30 on variavel_arvore (variavel_id);



# --- !Downs

drop table if exists arvore cascade;

drop table if exists arvore_ajuste cascade;

drop table if exists arvore_ajuste_variavel cascade;

drop table if exists autor cascade;

drop table if exists autor_modelo cascade;

drop table if exists bioma cascade;

drop table if exists coordenada cascade;

drop table if exists disponibilidade cascade;

drop table if exists equacao cascade;

drop table if exists equacao_variavel cascade;

drop table if exists espacamento cascade;

drop table if exists estado cascade;

drop table if exists formacao cascade;

drop table if exists local cascade;

drop table if exists local_detalhe_biomassa cascade;

drop table if exists local_detalhe_carbono cascade;

drop table if exists local_detalhe_volume cascade;

drop table if exists metodo_quantificacao_biomassa cascade;

drop table if exists metodo_quantificacao_carbono cascade;

drop table if exists municipio cascade;

drop table if exists municipio_local cascade;

drop table if exists pais cascade;

drop table if exists parcela cascade;

drop table if exists termo cascade;

drop table if exists tipo_estimativa cascade;

drop table if exists trabalho_cientifico cascade;

drop table if exists trabalho_cientifico_equacao cascade;

drop table if exists variavel cascade;

drop table if exists variavel_arvore cascade;

drop table if exists variavel_interesse cascade;

drop sequence if exists arvore_seq;

drop sequence if exists arvore_ajuste_seq;

drop sequence if exists arvore_ajuste_variavel_seq;

drop sequence if exists autor_seq;

drop sequence if exists autor_modelo_seq;

drop sequence if exists bioma_seq;

drop sequence if exists coordenada_seq;

drop sequence if exists disponibilidade_seq;

drop sequence if exists equacao_seq;

drop sequence if exists equacao_variavel_seq;

drop sequence if exists espacamento_seq;

drop sequence if exists estado_seq;

drop sequence if exists formacao_seq;

drop sequence if exists local_seq;

drop sequence if exists local_detalhe_biomassa_seq;

drop sequence if exists local_detalhe_carbono_seq;

drop sequence if exists local_detalhe_volume_seq;

drop sequence if exists metodo_quantificacao_biomassa_seq;

drop sequence if exists metodo_quantificacao_carbono_seq;

drop sequence if exists municipio_seq;

drop sequence if exists municipio_local_seq;

drop sequence if exists pais_seq;

drop sequence if exists parcela_seq;

drop sequence if exists termo_seq;

drop sequence if exists tipo_estimativa_seq;

drop sequence if exists trabalho_cientifico_seq;

drop sequence if exists trabalho_cientifico_equacao_seq;

drop sequence if exists variavel_seq;

drop sequence if exists variavel_arvore_seq;

drop sequence if exists variavel_interesse_seq;

