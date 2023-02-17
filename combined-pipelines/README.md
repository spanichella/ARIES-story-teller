# ARIES StoryTeller

![Pipeline Status](https://github.com/spanichella/ARIES-story-teller/actions/workflows/java_ci.yml/badge.svg)

StoryTeller is a tool to automatically analyze User Stories and Requirements Specifications.
Read more about the Project in [PROJECT.md](./PROJECT.md)

## Installation

* [Install Java 15](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html) and R
* In a terminal change into the folder `combined-pipelines`
* Verify your installation with:
  * On Linux/Mac OS: `./gradlew verifyInstallation`
  * On Windows: `.\gradlew.bat verifyInstallation`
* Follow the instructions until it runs successful
  * If not done already it will require you to manually download the file [https://www.kaggle.com/danielwillgeorge/glove6b100dtxt](https://www.kaggle.com/danielwillgeorge/glove6b100dtxt) to the shown path.
* Run the application with:
  * On Linux/Mac OS: `./gradlew run`
  * On Windows: `.\gradlew.bat run`
* As an example the file `datasets/stories/Stories_Dataset.tsv` can be used with `ML`, `J48` and `10-fold` as the options.

## Development

Use [IntelliJ IDEA](https://www.jetbrains.com/idea/) and open the `combined-pipelines` directory as a project.
The necessary gradle commands are provided as run configurations.
