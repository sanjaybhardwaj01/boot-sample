--<ScriptOptions statementTerminator=";"/>

CREATE TABLE region (
		id INT8 NOT NULL,
		region_code VARCHAR(255),
		region_name VARCHAR(255)
	);

CREATE TABLE continent (
		id INT8 NOT NULL,
		continent_name VARCHAR(255)
	);

CREATE TABLE v_user_authority (
		user_id INT8 NOT NULL,
		authority_id INT8 NOT NULL
	);

CREATE TABLE country (
		id INT8 NOT NULL,
		country_code VARCHAR(255),
		country_name VARCHAR(255),
		region_id INT8
	);

CREATE TABLE v_user (
		id INT8 NOT NULL,
		username VARCHAR(50) NOT NULL,
		password VARCHAR(60),
		firstname VARCHAR(50),
		lastname VARCHAR(50),
		email VARCHAR(100),
		enabled BOOL NOT NULL,
		lastpasswordresetdate TIMESTAMP
	);

CREATE TABLE v_authority (
		id INT8 NOT NULL,
		name VARCHAR(50) NOT NULL
	);

CREATE UNIQUE INDEX pk_country ON country (id ASC);

CREATE UNIQUE INDEX pk_user ON v_user (id ASC);

CREATE UNIQUE INDEX pk_continent ON continent (id ASC);

CREATE UNIQUE INDEX user_authority_pkey ON v_user_authority (user_id ASC, authority_id ASC);

CREATE UNIQUE INDEX pk_authority ON v_authority (id ASC);

CREATE UNIQUE INDEX pk_region ON region (id ASC);

ALTER TABLE v_user ADD CONSTRAINT pk_user PRIMARY KEY (id);

ALTER TABLE v_authority ADD CONSTRAINT pk_authority PRIMARY KEY (id);

ALTER TABLE v_user_authority ADD CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_id);

ALTER TABLE continent ADD CONSTRAINT pk_continent PRIMARY KEY (id);

ALTER TABLE region ADD CONSTRAINT pk_region PRIMARY KEY (id);

ALTER TABLE country ADD CONSTRAINT pk_country PRIMARY KEY (id);

ALTER TABLE v_user_authority ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id)
	REFERENCES v_user (id);

ALTER TABLE v_user_authority ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_id)
	REFERENCES v_authority (id);

ALTER TABLE country ADD CONSTRAINT fk_country_region_id FOREIGN KEY (region_id)
	REFERENCES region (id);
