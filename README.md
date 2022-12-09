# Reto Backend

Spring Boot project to manage appointments to exams.

## Instructions

Before running the applications, execute this script to initialize the database:

```sql
DROP TABLE appointments CASCADE CONSTRAINTS;
DROP TABLE affiliates CASCADE CONSTRAINTS;
DROP TABLE tests CASCADE CONSTRAINTS;

CREATE TABLE affiliates (
    id NUMBER(19,0) GENERATED AS IDENTITY PRIMARY KEY, 
    age NUMBER(10,0), mail varchar2(255 char) NOT NULL, 
    name VARCHAR2(255 char) NOT NULL
);

CREATE TABLE tests (
    id NUMBER(19,0) GENERATED AS IDENTITY PRIMARY KEY, 
    description VARCHAR2(255 char), 
    name VARCHAR2(255 char)
);

CREATE TABLE appointments (
    id NUMBER(19,0) GENERATED AS IDENTITY PRIMARY KEY, 
    date_ DATE NOT NULL, 
    hour_ DATE NOT NULL, 
    id_affiliate NUMBER(19,0) NOT NULL REFERENCES affiliates (id) ON DELETE CASCADE, 
    id_test NUMBER(19,0) NOT NULL REFERENCES tests (id) ON DELETE CASCADE
);
```
