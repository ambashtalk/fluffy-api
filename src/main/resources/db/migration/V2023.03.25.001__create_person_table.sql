-- Create person table
CREATE TABLE person (
  id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(255) NULL,
   last_name VARCHAR(255) NULL,
   username VARCHAR(255) NOT NULL,
   default_person_email_id BIGINT NULL,
   password VARCHAR(255) NOT NULL,
   enabled BIT(1) DEFAULT 1 NOT NULL,
   last_login datetime NULL,
   created_at datetime DEFAULT NOW() NOT NULL,
   updated_at datetime DEFAULT NOW() on update NOW() NOT NULL,
   CONSTRAINT pk_person PRIMARY KEY (id)
);

ALTER TABLE person ADD CONSTRAINT uc_person_username UNIQUE (username);

CREATE TABLE person_email (
  id BIGINT AUTO_INCREMENT NOT NULL,
   person_id BIGINT NOT NULL,
   email VARCHAR(255) NOT NULL,
   CONSTRAINT pk_person_email PRIMARY KEY (id)
);

ALTER TABLE person_email ADD CONSTRAINT uc_person_email_email UNIQUE (email);

ALTER TABLE person ADD CONSTRAINT FK_PERSON_ON_DEFAULT_PERSON_EMAIL FOREIGN KEY (default_person_email_id) REFERENCES person_email (id);

ALTER TABLE person_email ADD CONSTRAINT FK_PERSON_EMAIL_ON_PERSON FOREIGN KEY (person_id) REFERENCES person (id);

CREATE TABLE `role` (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(20) NOT NULL,
   CONSTRAINT pk_role PRIMARY KEY (id)
);

ALTER TABLE `role` ADD CONSTRAINT uc_role_name UNIQUE (name);

CREATE TABLE person_roles (
  person_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   CONSTRAINT pk_person_roles PRIMARY KEY (person_id, role_id)
);

ALTER TABLE person_roles ADD CONSTRAINT fk_perrol_on_person FOREIGN KEY (person_id) REFERENCES person (id);

ALTER TABLE person_roles ADD CONSTRAINT fk_perrol_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);