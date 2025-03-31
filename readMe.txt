# Caveat:

- I added the following constraint: initialCredit should be >= ZERO
  The goal is to show the usage of ProblemDetail with validation messages in case of ValidationException(s).

# What's in the box:

- The code: controllers, services, repos, model
- Usage of ProblemDetail. See ProblemDetailExceptionHandler class
- Usage of OpenApi (swagger)

- Unit tests for services
- Integration tests for controllers
- Performance tests using gatling

# Build (runs unit & integration tests):

  mvn clean install

# Run:

  java -jar target/pboc-demo-1.0.0.jar

  browse url : http://localhost:8080/swagger-ui/index.html#/

# Run performance tests (see results in target/gatling):

  java -jar target/pboc-demo-1.0.0.jar
  mvn gatling:test
