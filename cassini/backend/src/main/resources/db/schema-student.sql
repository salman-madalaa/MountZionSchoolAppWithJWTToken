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
  DATE_OF_ADDMISSION      DATE,
  CLASS_TO_JOIN           VARCHAR(256),
  GENDER                  VARCHAR(256),
  DATE_OF_BIRTH           DATE,
  MARKS_OF_IDENTIFICATION VARCHAR(256),
  RELIGION                VARCHAR(256),
  CASTE                   VARCHAR(256),
  CASTEID                 VARCHAR(256),
  AADHAR_NUMBER           VARCHAR(256),
  BANK_ACCOUNT_NUMBER     BIGINT,
  IFSC_CODE               VARCHAR(256),
  CHILD_HANDICAPPED       BOOLEAN,
  FATHER_MOTHER_EXPIRED   BOOLEAN,
  SIBLINGS                BOOLEAN,
  RTE_STUDENT             BOOLEAN,
  SIBLINGINFORMATION      INTEGER REFERENCES SIBLING_INFORMATION (ID)
);

CREATE TABLE SIBLING_INFORMATION (
  ID   INTEGER      NOT NULL PRIMARY KEY,
  NAME VARCHAR(256) NOT NULL,
  AGE  INTEGER      NOT NULL
);
