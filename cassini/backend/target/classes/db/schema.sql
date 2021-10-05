/* Wrap everything in a transaction */
BEGIN TRANSACTION;

/* Begin schema setup */
--  \set schemaName '$schemaName'
--  \set PASSWORD '$hashPwd'
--  DROP SCHEMA IF EXISTS :schemaName CASCADE;
--  CREATE SCHEMA :schemaName;
--  SET SEARCH_PATH TO :schemaName;
/* End schema setup */

 /* Begin other schema files */

--  ./schema-student.sql
--  call schema-user.sql
--  call schema-sequences.sql

CREATE TABLE SIBLING_INFORMATION (
  ID   INTEGER      NOT NULL PRIMARY KEY,
  NAME VARCHAR(256) NOT NULL,
  AGE  INTEGER      NOT NULL
);

CREATE TABLE STUDENT (
  ID                      BIGINT       NOT NULL PRIMARY KEY,
  REGISTRATIONID          VARCHAR(256) NOT NULL,
  FIRST_NAME              VARCHAR(256) NOT NULL,
  LAST_NAME               VARCHAR(256) NOT NULL,
  FATHER_NAME             VARCHAR(256),
  MOTHER_NAME             VARCHAR(256),
  MOBILE_NUMBER           INT,
  PRESENT_ADDRESS         VARCHAR(256),
  PERMANENT_ADDRES        VARCHAR(256),
  SAMAGRAID               VARCHAR(256),
  DATE_OF_ADMISSION      DATE,
  CLASS_TO_JOIN           VARCHAR(256),
  GENDER                  VARCHAR(256),
  DATE_OF_BIRTH           DATE,
  MARKS_OF_IDENTIFICATION VARCHAR(256),
  RELIGION                VARCHAR(256),
  CASTE                   VARCHAR(256),
  CASTE_ID                 VARCHAR(256),
  AADHAR_NUMBER           VARCHAR(256),
  BANK_ACCOUNT_NUMBER     BIGINT,
  IFSC_CODE               VARCHAR(256),
  CHILD_HANDICAPPED       BOOLEAN,
  FATHER_MOTHER_EXPIRED   BOOLEAN,
  SIBLINGS                BOOLEAN,
  RTE_STUDENT             BOOLEAN,
  SIBLINGINFORMATION      INTEGER REFERENCES SIBLING_INFORMATION (ID) ON DELETE CASCADE
);


CREATE TABLE ROLES (
  ROLE_ID          INTEGER      NOT NULL PRIMARY KEY,
  NAME        VARCHAR(256) NOT NULL,
  DESCRIPTION VARCHAR(256)
);

CREATE TABLE USER (
  USER_ID           INTEGER      NOT NULL PRIMARY KEY,
  FIRSTNAME    VARCHAR(256) NOT NULL,
  LASTNAME     VARCHAR(256) NOT NULL,
  PHONE_NUMBER BIGINT,
  IMAGE        VARCHAR(256),
  NAME         VARCHAR(256) NOT NULL,
  USER_NAME    VARCHAR(256) NOT NULL,
  EMAIL        VARCHAR(256) NOT NULL,
  PASSWORD     VARCHAR(256) NOT NULL,
  ROLES        INTEGER REFERENCES ROLES (ROLE_ID)
);


CREATE TABLE USERROLE (
  ROWID                       INTEGER             NOT NULL PRIMARY KEY,
  PERSON                      INTEGER             NOT NULL REFERENCES USER (USER_ID) ON DELETE CASCADE,
  ROLE                        INTEGER             NOT NULL REFERENCES ROLE (ROLE_ID) ON DELETE CASCADE
);





COMMIT TRANSACTION;




