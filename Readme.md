# Minimal Game

This is a minimal game and game engine based on jwgl.

## Start the app from eclipse

- run a `mvn clean install` on the lazyEngine project to have it in your local maven repo
- run `mvn clean package` on this project to generate the native files for jwgl. 
- Then, set up the launch configuration of the class `Game` with the following VM argument : `-Djava.library.path=target/natives` to load the native lib. 
- Finally, you can run the project.
