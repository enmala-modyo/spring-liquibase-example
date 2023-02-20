-- In this script you load data to previously created tables (JPA o schema.sql)
-- This script runs all the times, You need to control duplicates by your own.

-- Fill COUNTRY table
INSERT INTO country (code, name, iso3)
VALUES ('CL', 'Chile', 'CHL');
INSERT INTO country (code, name, iso3)
VALUES ('CO', 'Colombia', 'COL');
INSERT INTO country (code, name, iso3)
VALUES ('PE', 'Perú', 'PER');
INSERT INTO country (code, name, iso3)
VALUES ('AR', 'Argenttina', 'ARG');

-- Fix Argentina Row
REPLACE
INTO country (code,name, iso3)
VALUES ('AR','Argentina', 'ARG');

