--liquibase formatted sql
--changeset Mohammed:1 failOnError=true
CREATE TABLE TEST (
    NAME varchar(20) 
)