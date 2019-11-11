#!/bin/bash
cp ../images/postgres/database.sql ../images/postgres/databaseProd.sql
python insert_characters.py --number 1000000 --file ../images/postgres/databaseProd.sql
python insert_memberships.py --number 1000000 --file ../images/postgres/databaseProd.sql
