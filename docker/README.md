#Docker
In order to run this monitor on docker, follow the following steps:

1. Generate the jar of the app with maven
2. Copy the generated jar in this directory
3. Edit the configuration in the docker-compose.yml file. Please refer to the main readme file of this repo
4. Run docker compose on this directory to generate the docker image, create the container and run it
5. In case the docker file or app's jar have changed, remember to run "docker-compose build" to force rebuilding the docker image.

###TODOs
Perform steps 1 and 2 automatically with Maven
