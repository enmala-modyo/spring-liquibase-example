-- in this file you update database objects or create new ones not created by JPA
-- This script is executed after JPA ends creating/updating entities

-- Create not JPA tables
CREATE TABLE IF NOT EXISTS `test-table` (
    id BIGINT auto_increment NOT NULL,
    data1 varchar(100) NOT NULL,
    data2 varchar(100) NULL,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS `test-table2` (
    id2 BIGINT auto_increment NOT NULL,
    data1 varchar(100) NOT NULL,
    data2 varchar(100) NULL,
    primary key (id2)
);

-- maybe you need to rename the table PAIS to COUNTRY
-- If you rename it only on the JPA entity, you will have 2 tables in the database
-- Then you can delete the old one (PAIS)
-- (you need to take care about referential integrity problems)
drop table if exists pais;
