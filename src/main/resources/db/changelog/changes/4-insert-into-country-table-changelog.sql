--liquibase formatted sql
--changeset enmala:4
insert into country (code, name, iso3) values ('EC', 'Ecuador', 'ECU');
