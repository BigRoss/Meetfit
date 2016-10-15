# MeetFit
## Application Information
MeetFit is a web platform that facilitates social exercising, by allowing people to publish exercise events online for their friends to join. With MeetFit, we can get fit together.

## Technology
MeetFit is built with Spring Boot, Hibernate, MySQL, Bootstrap, and AngularJS. It is scaffolded with JHipster, a Yeoman generator. Maven is used for building, testing, and running the application.

## Dependencies
The application requires Java and MySQL to run in production. In development mode, it uses the H2 database engine.

## Operation
**Development**

Install and configure Node.js on your machine.

After installing Node.js, you will be able to run the following command to install development tools. You will only need to run this command when dependencies change in package.json.

```
npm install
```

Install the Gulp command-line tool globally with:

```
npm install -g gulp
```

Bower is used to manage CSS and JavaScript dependencies. You can upgrade dependencies by specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.

**Run**

In separate terminals:
```
./mvnw
```
```
gulp
```

This will auto-refresh the browser as files are changed on the hard drive.

**Building for production**

To optimize MeetFit for production, run:

```
./mvnw -Pprod clean package
```

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references these new files.

To ensure everything worked, run:

```
java -jar target/*.war
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Scripts
### `pull.sh`
This script pulls changes from the remote repository.

### `push.sh`
This script commits and pushes all changes to the remote repository. It can be run with an argument as a commit message (`bash push.sh "Commit Message"`).

### `reset.sh`
This script clears all changes since the last commit.
