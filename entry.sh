#!/bin/sh

set +x
mvn clean gatling:test -Dduration=180 -Dscenario=RollingUpdate -Dgatling.simulation.name=Simulation1 -Dbaseurl=https://app-delayed.shayan-ps1.qax.rtf.cloudhub.io/delayed

for dir in target/gatling/*/
do
  echo "dir = ${dir}"
  aws s3 cp ${dir}simulation.log s3://rtf-test-bucket/simulationlogs/`hostname`-simulation.log
done
