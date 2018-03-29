
CREATE SEQUENCE ENDUSER_SEQ START WITH 100 INCREMENT BY 1;
CREATE TABLE ENDUSER (
    id bigint not null,
    username varchar(100),
    password varchar(100),
    primary key (id)
);

CREATE SEQUENCE DEVICE_SEQ START WITH 100 INCREMENT BY 1;
CREATE TABLE DEVICE (
    id bigint not null,
    idenduser bigint not null,
    name varchar(200),
    primary key (id)
);

ALTER TABLE DEVICE ADD FOREIGN KEY ( idenduser ) REFERENCES enduser ( id ) ;

