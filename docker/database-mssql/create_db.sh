#!/bin/bash
set -e

echo "
CREATE TABLE dbp.dbo.test_table (
	id int NOT NULL PRIMARY KEY,
	first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL
);
GO" >> setup.sql