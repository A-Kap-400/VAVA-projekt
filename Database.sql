CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE IF NOT EXISTS library.testTable
(
    id   SERIAL      NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    age  BIGINT      NOT NULL
);

INSERT INTO testTable
VALUES (1, 'hello', 10),
       (2, 'world', 20);
