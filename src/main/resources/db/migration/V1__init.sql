-- SEQUENCE: equipes_id_seq

-- DROP SEQUENCE equipes_id_seq;

CREATE SEQUENCE equipes_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: equipes

-- DROP TABLE equipes;

CREATE TABLE equipes
(
    id bigint NOT NULL DEFAULT nextval('equipes_id_seq'::regclass),
    descricao character varying(255) COLLATE pg_catalog."default",
    nome character varying(60) COLLATE pg_catalog."default" NOT NULL,
    path_foto character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT equipes_pkey PRIMARY KEY (id),
    CONSTRAINT uk_equipes_nome UNIQUE (nome)
)

TABLESPACE pg_default;

-- SEQUENCE: pessoas_id_seq

-- DROP SEQUENCE pessoas_id_seq;

CREATE SEQUENCE pessoas_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: pessoas

-- DROP TABLE pessoas;

CREATE TABLE pessoas
(
    id bigint NOT NULL DEFAULT nextval('pessoas_id_seq'::regclass),
    email character varying(70) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(40) COLLATE pg_catalog."default" NOT NULL,
    path_foto character varying(255) COLLATE pg_catalog."default",
    senha character varying(255) COLLATE pg_catalog."default" NOT NULL,
    equipe_id bigint,
    CONSTRAINT pessoas_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pessoas_nome UNIQUE (nome),
    CONSTRAINT uk_pessoas_email UNIQUE (email),
    CONSTRAINT fk_pessoas_equipe FOREIGN KEY (equipe_id)
        REFERENCES equipes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

-- Table: perfis

-- DROP TABLE perfis;

CREATE TABLE perfis
(
    pessoa_id bigint NOT NULL,
    perfis integer,
    CONSTRAINT fk_perfis_pessoa FOREIGN KEY (pessoa_id)
        REFERENCES pessoas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

-- SEQUENCE: forgor_password_id_seq

-- DROP SEQUENCE forgor_password_id_seq;

CREATE SEQUENCE forgor_password_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: forgor_password

-- DROP TABLE forgor_password;

CREATE TABLE forgor_password
(
    id bigint NOT NULL DEFAULT nextval('forgor_password_id_seq'::regclass),
    data_geracao timestamp without time zone NOT NULL,
    hash character varying(70) COLLATE pg_catalog."default" NOT NULL,
    used boolean NOT NULL,
    pessoa_id bigint,
    CONSTRAINT forgor_password_pkey PRIMARY KEY (id),
    CONSTRAINT uk_fgt_pass_hash UNIQUE (hash),
    CONSTRAINT fk_fgt_pass_pessoa FOREIGN KEY (pessoa_id)
        REFERENCES pessoas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

-- SEQUENCE: transacoes_id_seq

-- DROP SEQUENCE transacoes_id_seq;

CREATE SEQUENCE transacoes_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: transacoes

-- DROP TABLE transacoes;

CREATE TABLE transacoes
(
    id bigint NOT NULL DEFAULT nextval('transacoes_id_seq'::regclass),
    data_operacao timestamp without time zone NOT NULL,
    mensagem character varying(80) COLLATE pg_catalog."default",
    valor numeric(5,2) NOT NULL DEFAULT 0.00,
    destinatario_id bigint,
    remetente_id bigint,
    CONSTRAINT transacoes_pkey PRIMARY KEY (id),
    CONSTRAINT fk_transacoes_pessoa_remet FOREIGN KEY (remetente_id)
        REFERENCES pessoas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_transacoes_pessoa_dest FOREIGN KEY (destinatario_id)
        REFERENCES pessoas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;