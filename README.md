# MeetFit
## Application Information
MeetFit is a web platform that facilitates social exercising, by allowing people to publish exercise events online for their friends to join. With MeetFit, we can get fit together.

## Technology
MeetFit is built with Spring Boot, Hibernate, MySQL, Bootstrap, and AngularJS. It is scaffolded with JHipster, a Yeoman generator. Maven is used for building, testing, and running the application.

## Dependencies
The application requires Java and MySQL to run in production. In development mode, it uses the H2 database engine.

## Operation
**Run**
```
./mvnw
```

## Scripts
### `pull.sh`
This script pulls changes from the remote repository.

### `push.sh`
This script commits and pushes all changes to the remote repository. It can be run with an argument as a commit message (`bash push.sh "Commit Message"`).