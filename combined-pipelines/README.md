# ARIES StoryTeller

![Pipeline Status](https://github.com/spanichella/ARIES-story-teller/actions/workflows/java_ci.yml/badge.svg)

StoryTeller is a tool to automatically analyze User Stories and Requirements Specifications.
Read more about the Project in [PROJECT.md](./PROJECT.md)

## Installation

* [Install Java 15](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html) and R
* In a command line change into the folder `combined-pipelines`
* Verify your installation with:
  * On Linux/Mac OS: `./gradlew verifyInstallation`
    * In the case of Mac OS it could require to download manually/locally the file [https://www.kaggle.com/danielwillgeorge/glove6b100dtxt](https://www.kaggle.com/danielwillgeorge/glove6b100dtxt)
  * On Windows: `.\gradlew.bat verifyInstallation`
* Follow the instructions until it runs successful
* Run the application with:
  * On Linux/Mac OS: `./gradlew run`
  * On Windows: `.\gradlew.bat run`
* As an example the file `datasets/combined/truth_set_combined-ReqSpec.txt` can be used with `Requirement-Specifications` and `DL` as the options.

## Development

Use [IntelliJ IDEA](https://www.jetbrains.com/idea/) and open the `combined-pipelines` directory as a project.
