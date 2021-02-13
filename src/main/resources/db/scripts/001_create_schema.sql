-- TRUNCATE TABLE databasechangeloglock CASCADE;
-- TRUNCATE TABLE databasechangelog CASCADE;
-- --
-- DROP TABLE IF EXISTS accidents CASCADE;
-- DROP TABLE IF EXISTS accidents_types CASCADE;
-- DROP TABLE IF EXISTS rules CASCADE;

CREATE TABLE accidents_types (
  id serial primary key,
  type_id integer,
  name varchar(2000)
);

CREATE TABLE accidents (
  id serial primary key,
  name  varchar(2000),
  text  varchar(2000),
  address  varchar(2000),
  type_id integer references accidents_types(id)
);

CREATE TABLE rules (
  id serial primary key,
  rule_id integer,
  accident_id integer references accidents(id),
  name varchar(2000)
);