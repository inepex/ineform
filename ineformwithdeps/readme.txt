this project is used to build one jar with all dependencies for ineform

Steps of use:
1. install actual versions for all ineXY project with executing mvn install in ineform root
2. update version in ineformwithdeps pom.xml (also the ineform dependency's version)
3. execute mvn assembly:single