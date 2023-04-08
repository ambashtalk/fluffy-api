CREATE TABLE refresh_token (
  id BIGINT AUTO_INCREMENT NOT NULL,
   person_id BIGINT NULL,
   token VARCHAR(255) NOT NULL,
   expiry_date datetime NOT NULL,
   CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

ALTER TABLE refresh_token ADD CONSTRAINT uc_refresh_token_token UNIQUE (token);

ALTER TABLE refresh_token ADD CONSTRAINT FK_REFRESH_TOKEN_ON_PERSON FOREIGN KEY (person_id) REFERENCES person (id);