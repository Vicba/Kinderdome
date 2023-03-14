alter table if exists CHILD
    drop constraint if exists FK0;
alter table if exists FAMILY
    drop constraint if exists FK1;
alter table if exists FAMILY
    drop constraint if exists FK2;
alter table if exists CARETAKER
    drop constraint if exists FK3;
alter table if exists EVENT
    drop constraint if exists FK4;
alter table if exists EVENT
    drop constraint if exists FK5;
alter table if exists EATING
    drop constraint if exists FK6;
alter table if exists SLEEPING
    drop constraint if exists FK7;
alter table if exists SPOKENWORD
    drop constraint if exists FK8;
alter table if exists SPOKENWORD
    drop constraint if exists FK9;
alter table if exists EVENT
    drop constraint if exists FK10;
alter table if exists EMERGENCIES
    drop constraint if exists FK11;

drop table if exists EATING;
drop table if exists SPOKENWORD;
drop table if exists WORD;
drop table if exists EMERGENCIES;
drop table if exists EMERGENCYSTATUS;
drop table if exists EVENT;
drop table if exists SLEEPING;
drop table if exists FAMILY;
drop table if exists PARENT;
drop table if exists CARETAKER;
drop table if exists CHILDCARECENTER;
drop table if exists EVENTTYPES;
drop table if exists CHILD;

CREATE TABLE CHILD
(
    `childID`           integer PRIMARY KEY NOT NULL,
    `name`              varchar(255)        NOT NULL,
    `dob`               datetime            NOT NULL,
    `childcareCenterID` int
);
CREATE TABLE CHILDCARECENTER
(
    `childcareCenterID`   integer PRIMARY KEY,
    `childcareCenterName` varchar(255) NOT NULL,
    `childLimit`          integer      NOT NULL
);

CREATE TABLE PARENT
(
    `parentID` integer PRIMARY KEY,
    `name`     varchar(255) NOT NULL,
    `year`     int          NOT NULL
);

CREATE TABLE FAMILY
(
    `childID`  int,
    `parentID` int
);

CREATE TABLE CARETAKER
(
    `caretakerID`       integer PRIMARY KEY,
    `name`              varchar(255),
    `salary`            integer,
    `childcareCenterID` int
);

CREATE TABLE EVENTTYPES
(
    `typeID` int PRIMARY KEY,
    `name`   varchar(255)
);

CREATE TABLE EVENT
(
    `eventID`     integer PRIMARY KEY,
    `dateAndTime` datetime,
    `childID`     int,
    `longitude`   float,
    `latitude`    float,
    `bodytemp`    float,
    `hearthrate`  int,
    `typeID`      int,
    `description` varchar(255)
);

CREATE TABLE EATING
(
    `eventID`          int,
    `nutritionalValue` int
);

CREATE TABLE SLEEPING
(
    `eventID` int,
    `depth`   int
);

CREATE TABLE SPOKENWORD
(
    `eventID` int,
    `wordId`  int
);

CREATE TABLE WORD
(
    `wordID` int PRIMARY KEY,
    `word`   varchar(255)
);

CREATE TABLE EMERGENCIES
(
    `eventID`  integer,
    `statusID` integer
);

CREATE TABLE EMERGENCYSTATUS
(
    `statusID`   integer PRIMARY KEY,
    `statusName` varchar(255)
);

CREATE UNIQUE INDEX `event_index_0` ON EVENT (`dateAndTime`, `childID`);

ALTER TABLE CHILD
    ADD CONSTRAINT FK0
        FOREIGN KEY (`childcareCenterID`) REFERENCES CHILDCARECENTER (`childcareCenterID`);

ALTER TABLE FAMILY
    ADD CONSTRAINT FK1
        FOREIGN KEY (`childID`) REFERENCES CHILD (`childID`);

ALTER TABLE FAMILY
    ADD CONSTRAINT FK2
        FOREIGN KEY (`parentID`) REFERENCES PARENT (`parentID`);

ALTER TABLE CARETAKER
    ADD CONSTRAINT FK3
        FOREIGN KEY (`childcareCenterID`) REFERENCES CHILDCARECENTER (`childcareCenterID`);

ALTER TABLE EVENT
    ADD CONSTRAINT FK4
        FOREIGN KEY (`childID`) REFERENCES CHILD (`childID`);

ALTER TABLE EVENT
    ADD CONSTRAINT FK5
        FOREIGN KEY (`typeID`) REFERENCES EVENTTYPES (`typeID`);

ALTER TABLE EATING
    ADD CONSTRAINT FK6
        FOREIGN KEY (`eventID`) REFERENCES EVENT (`eventID`);

ALTER TABLE SLEEPING
    ADD CONSTRAINT FK7
        FOREIGN KEY (`eventID`) REFERENCES EVENT (`eventID`);

ALTER TABLE SPOKENWORD
    ADD CONSTRAINT FK8
        FOREIGN KEY (`eventID`) REFERENCES EVENT (`eventID`);

ALTER TABLE SPOKENWORD
    ADD CONSTRAINT FK9 FOREIGN KEY (`wordId`) REFERENCES WORD (`wordID`);

ALTER TABLE EMERGENCIES
    ADD CONSTRAINT FK10
        FOREIGN KEY (`eventID`) REFERENCES EVENT (`eventID`);

ALTER TABLE EMERGENCIES
    ADD CONSTRAINT FK11
        FOREIGN KEY (`statusID`) REFERENCES EMERGENCYSTATUS (`statusID`);

