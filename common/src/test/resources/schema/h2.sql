CREATE SCHEMA IF NOT EXISTS CRD;

create table IF NOT EXISTS CRD.CAR_INVENTORY
(
    ID                  INT auto_increment,
    CAR_NAME            VARCHAR2,
    CAR_TYPE            int,
    uuid                VARCHAR2,
    CONFIRMATION_NUMBER VARCHAR2,
    dateC               DATETIME,
    dateM               DATETIME
);

create unique index IF NOT EXISTS CRD.CAR_INVENTORY_ID_UINDEX
    on CRD.CAR_INVENTORY (ID);

alter table CRD.CAR_INVENTORY
    add constraint IF NOT EXISTS CAR_INVENTORY_PK
        primary key (ID);

INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('Toyota', 0, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('Toyota', 1, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('Toyota', 2, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('GMC', 0, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('GMC', 1, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('GMC', 2, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('SUBARU', 0, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('SUBARU', 1, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('SUBARU', 2, now(), now());
INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, dateM, dateC)
VALUES ('SUBARU', 1, now(), now());


INSERT INTO CRD.CAR_INVENTORY (CAR_NAME, CAR_TYPE, CONFIRMATION_NUMBER, dateM, dateC)
VALUES ('Toyota', 1, 'XX', now(), now());


create table IF NOT EXISTS CRD.CAR_RESERVATION
(
    ID                     INT auto_increment,
    CAR_INVENTORY_ID       INT,
    CONFIRMATION_NUMBER    VARCHAR2,
    uuid                   VARCHAR2,
    DATE_RESERVATION_START DATETIME,
    DATE_RESERVATION_END   DATETIME,
    DATE_ACTUAL_START      DATETIME,
    DATE_ACTUAL_END        DATETIME,
    dateC                  DATETIME,
    dateM                  DATETIME
);
