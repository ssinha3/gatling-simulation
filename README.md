## Prerequisites
1. mvn
2. Create s3 bucket named "rtf-test-bucket", where results are sent.
## Run locally 
1. mvn clean gatling:test -Dduration=60 -Dscenario=RollingUpdate -Dgatling.simulation.name=Simulation1 -Dbaseurl=curl http://54.188.186.175:30350
## Run in docker
1. docker build -t rtf-test .
2. docker run --rm -v ${HOME}/.aws/credentials:/root/.aws/credentials:ro rtf-test:latest
## Run in ESC Fargate
1. Lauch cluster <br> ecs-cli configure --cluster rtfTestCluster --region us-west-1 --default-launch-type FARGATE --config-name rtfTestClusterConfiguration
2. Bring up the test cluster <br>ecs-cli up
3. Update vpn and security group in ecs_config.yml. 
4. Create ecsTaskExecutionRole in aws console and update in ecs_config.yml. 
5. Create task role with s3 write privileges in aws console and update in ecs_config.yml.
6. Time to run ecs-cli compose for ecs to run simulations as ECS Fargate tasks.<br> ecs-cli compose --ecs-params ecs_params.yml scale 10
7. View task logs as <br> ecs-cli logs --task-id 0775b0e9c6a24faa870fe2ff80a65d07
## Generate consolidated report
1. ./aggregate_simulations.sh
2. Make simulation html package public in s3 and share.
