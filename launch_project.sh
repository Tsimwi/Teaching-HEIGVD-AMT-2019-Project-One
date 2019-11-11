#!/bin/bash

cd topology/topology-amt-testing
docker-compose up --build 2>/dev/null &
cd ../..
sleep 7
mvn clean install
cd topology/topology-amt-testing
docker-compose down
cd ../../scripts
./populateDB.sh
cd ../
cp -r target/*.war images/payara
cd topology/topology-amt-prod
docker-compose down
docker-compose up --build
