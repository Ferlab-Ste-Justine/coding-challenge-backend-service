-- Users schema

-- !Ups

CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    usertype varchar(10) NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE User;