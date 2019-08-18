-- Users schema

-- !Ups

CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE UserInputs (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    message varchar(255) NOT NULL,
    type boolean DEFAULT false,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE User;
DROP TABLE UserInputs;