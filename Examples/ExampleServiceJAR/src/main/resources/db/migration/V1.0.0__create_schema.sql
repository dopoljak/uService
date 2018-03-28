
CREATE SEQUENCE ENDUSER_SEQ START WITH 100 INCREMENT BY 1;
CREATE TABLE ENDUSER (
    id bigint not null,
    username varchar(100),
    password varchar(100),
    primary key (id)
);


