CREATE TABLE CUSTOMER
(
        ID                  VARCHAR(36)         NOT NULL
    ,   FIRST_NAME          VARCHAR(255)        NOT NULL
    ,   LAST_NAME           VARCHAR(255)        NOT NULL
    ,   PASSPORT_ID         VARCHAR(255)        NOT NULL
    ,   CONSTRAINT CUSTOMER_PK PRIMARY KEY (ID)
);
