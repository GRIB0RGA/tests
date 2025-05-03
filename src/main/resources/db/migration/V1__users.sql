DROP SEQUENCE IF EXISTS employee_seq;

CREATE TABLE employee
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255),
    salary DOUBLE,
    age  INT
);
