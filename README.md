# db1-merit-money-backend

# Technologies

* Java 8 (intension to run with java 11)
* Spring Boot 2.1.6

# Definitions

## Idioms 

English (en-US)
Portuguese (pt-BR)

Allowed 
* Code: en-US
* Business Documentations: pt-BR, en-US
* Technical Documentations: en-US
* Issues: en-US, pt-BR
* Commits: en-US, pt-BR
* Application language: en-US, pt-BR

Defaults
* Code: en-US
* Business Documentations: pt-BR
* Technical Documentations: en-US
* Issues: pt-BR
* Commits: en-US
* Application language: pt-BR

## Version

We're using [Semantic Versioning](https://semver.org/) model

## Official and Default IDE

JetBrain IDE
* https://www.jetbrains.com/idea/

# Branchs Workflow

We are following the "gitflow".
You can use this [usefull tool](https://github.com/nvie/gitflow) to implement. 

## Examples

In your project directory, start the flow:

    git flow init 
    
Follow default args. Just the tag prefix needs to be "tag_"

### Create Hotfix:

     git flow hotfix start <name> [<base>]

For hotfix branches, the <base> arg must be a commit on master.

To push/pull a feature branch to the remote repository, use:

    git flow hotfix publish <name>

Do your code and don't finish and merge automatically.
Use the github Pull Request to the "master" branch.
Also use the github Pull Request to the "develop" branch.

### Create Feature:

If you're working on a new feature you must to follow:

    git flow feature start <name> [<base>]

For feature branches, the <base> arg must be a commit on develop.

Do your code and don't finish and merge automatically.
Use the github Pull Request to the "develop" branch.
 
### Create Release:

When we want to release a new version, it's needed to:

    git flow release start <name> [<base>]

For feature branches, the <base> arg must be a commit on develop.

Do your code and don't finish and merge automatically.
Use the github Pull Request to the "master" branch.

# Commands
   
## Project setup
```
./mvnw clean install -skipTests
```

### Run your tests
```
./mvnw test
```

# Technical details

## Status check

To validate access:

    http://[IP]:[PORT]/actuator/health

## Docker

To use it in [Docker see here](others/docs/docker.md)

### PostgreSQL

[PostreSQL](https://hub.docker.com/_/postgres) its used in development mode and its the default database

You can find a compose file [here](others/docker/postgres)
It's possible to access the pg-admin in: http://localhost:16543
It's nedeed to create a server pointing to you local docker PG instance

## Spring

### Profiles

## Flyway

Flyway is Version control for your database

### Maven commands

Validate your scripts

    ./mvnw flyway:validate

Manually run your scripts

    ./mvnw flyway:migrate
  
Manually repair your history 

    ./mvnw flyway:repair
    
Repairs the Flyway schema history table. This will perform the following actions:
* Remove any failed migrations on databases without DDL transactions
(User objects left behind must still be cleaned up manually)
* Realign the checksums, descriptions and types of the applied migrations with the ones of the available migrations
        
### Defined patterns

See it [here](others/docs/flyway/flyway.md)

### Application and Spring integration

System it's configured to up with Flyway and execute all migrations automatically

## Dev

### Tests 

To execute build

    ./mvnw clean install -DskipTests
    
To execute tests

    ./mvnw test

### Sonar Quality

To run Sonar:

    ./mvnw verify test sonar:sonar

# Release History

* 0.0.1
    * Work in progress 