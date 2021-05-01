#Docker
In order to run this monitor on docker, follow the steps explained below.

The source is available in github [here](https://github.com/fredblockchain/ubix-node-monitor)

For detailed info regarding the use of the app, please consult this repository.
## Requirements

* Docker and Docker compose are required.
* It is NOT required to run it in the same server as the node.

## Pre steps
If you choose to generate the app from the source, please follow the following pre steps.

1. Generate the jar of the app with Maven
2. Copy the generated jar in this directory

## Install and run on Docker
3. Copy the application.properties file in a directory which path is ../config from docker-compose path, and set the correct values within this file
4. Edit the configuration in the docker-compose.yml file. Please refer to the main readme file of the github repo,
5. Run docker compose on this directory to generate the docker image, create the container and run it.
   The app will start synching automatically

In case the docker file or app's jar have changed, remember to run "docker-compose build" to force rebuilding the docker image.

