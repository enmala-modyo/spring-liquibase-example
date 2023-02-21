--liquibase formatted sql
--changeset enmala:3
update country set name = 'Argentina' where code = 'AR';
update country set name = 'Chile' where code = 'CL';
