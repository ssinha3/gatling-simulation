#!/bin/sh

rm -r target/gatling/*
aws s3 cp s3://rtf-test-bucket/simulationlogs/ target/gatling/logs --recursive
mvn gatling:test -DgenerateReport=true
aws s3 cp target/gatling/ s3://rtf-test-bucket/results/"simulation-$(date +"%Y-%m-%d_%H:%M:%S")" --recursive
