-- Run this script in PostgreSQL to initialize the database
CREATE DATABASE passworddb;

\c passworddb

CREATE TABLE IF NOT EXISTS passwords (
    id SERIAL PRIMARY KEY,
    website VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
