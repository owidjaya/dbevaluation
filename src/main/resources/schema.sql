CREATE SCHEMA IF NOT ExISTS tutorial;

CREATE TABLE IF NOT EXISTS tutorial.person(
   id SERIAL PRIMARY KEY,
   firstname TEXT,
   lastname TEXT,
   processed BOOLEAN NOT NULL DEFAULT FALSE
);