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

