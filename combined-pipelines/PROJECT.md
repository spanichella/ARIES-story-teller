# Storyteller: The Combined Project

## Overview
1. [Introduction](#section1)
2. [Program Description](#section2)
3. [Change Description](#section3)
4. [Future Work](#section4)

## 1. Introduction <a name="section1"></a>
_StoryTeller_ is our combined integration of the [ML-Component](https://github.com/spanichella/Requirement-Collector-ML-Component) and the [DL-Component](https://github.com/lmruizcar/Requirements-Collector-DL-Component) used to automatically analyze User Stories.
During the project, we merged the two pipelines step by step with focus to make them user-friendly to people with no computer science background.

## 2. Program Description <a name="section2"></a>
The goal of this part is to give a guide for setup and a brief description of how our code works.

### 2.1 Setup Information

#### Pre-Requisites
- Java 15
- The [R language](https://cran.r-project.org/) (version 4.0.3) must be installed and operational
- Cores: 4
- Memory: >6GB (add '-Xmx6G' to the VM options of the run configuration)
- Download [glove txt file](https://www.kaggle.com/watts2/glove6b50dtxt) and put it into the DL folder located at Combined Project -> Resources
- For usage on Windows, add the RScript bin folder path (e.g., C:\Program Files\R\R-4.0.3\bin) to the PATH variable (in environment variables)

Hint: It can potentially help to install [RStudio](https://rstudio.com/products/rstudio/download/), where you can manually open and execute the [MainScript.r](resources/R-scripts/MainScript.r) step by step as to install and load the necessary R packages.


#### Supported Operating Systems
- Mac OS
- Ubuntu 18.04 or above
- Windows 10


### 2.2 Pipeline Execution

#### Video Guide
[![IMAGE ALT TEXT HERE](images/ThumbnailSWM.jpg)](https://www.youtube.com/watch?v=ZXxYfPH8J0E)

#### Pipeline Description
When launching the program, the simple graphical user interface (GUI) will launch.
There the user must select the location of the dataset and the type of its content.
Depending on what he selected he can then specify attributes regarding the execution of the chosen pipeline.
If everything is selected and specified correctly, he can finally click on execute to run the analysis.

On execution, the code will first create an XML-File containing local paths used for reading the existing, or generating the new files.
After this, the code will start with the generation of the training- and test-set, depending on the selections made previously in the GUI.
These files are created by the R-scripts located in the [resources](resources) folder.

When this is done, the analysis part begins.
Again, depending on the selections before, our code will execute the ML or DL pipeline.
Especially the DL pipeline takes some time to be executed (up to 40min in our case).
As soon as the results and generated models are available, the user can find them in the [results](/results) and [models](/models) folders.

## 3. Change Description <a name="section3"></a>
This part is to explain the implementation and changes we made.

### 3.1 Refactoring the ML-Component
When first launching the ML-Component at the beginning of the project, we needed to specify all local paths manually in four different XML files.
To run the code we had to copy, edit, and then paste two commands to a console, specific to the data type and in the right order.
While this would not be a problem for experienced users, it certainly would for less experienced.
Additionally, the code would only generate results on macOS and Linux.

We began refactoring the code step by step in a top-down approach.
First, we integrated automatic identification of the local paths except for the initial dataset.
The automatic XML generation alone already makes the set-up process a lot faster and less complex.

The next step was to remove the console commands.
Since they called just two different functions, we are now just executing them after each other.
This brings the burden, that the entire file generation gets executed everytime, for example when a user only wants to re-run his selection with a different ML strategy or with the DL pipeline.

Finally, we cleaned out a lot of unnecessary code by refactoring, added some comments, and changed some variable and function names to improve readability and maintainability.
As a last point, we changed a single line in an R-Script to make the pipeline work on Windows machines too.

### 3.2 Adding a GUI
By removing the command line interaction, we needed a new and easier way to enter information needed.
We solved this by implementing a simple GUI.
When the program gets launched, it shows the interface, and the user can specify desired options for running the pipelines.
He can choose between ML and DL for Requirement Specifications, and just ML for the User Stories.
When selecting ML, he can further specify the method and strategy (either 10-fold or a percentage split for training- and test-set, respectively).
When selecting DL, he currently cannot specify further options.

The selected options then get used to create the XML file, and will alter the behavior of the execution of the pipelines.

### 3.3 Integrating the DL-Component
Lastly, we integrated the DL-Component.
We succeeded to use the same structure of the files that are also generated for the ML Pipeline, so we could skip adding a special script.
In the end we only had to modify some hard coded values and did not need to touch the logic at all.
An example would be that we no longer had to get the paths from the XML file, but instead we added the new DL dependencies to our `pom.xml` file.

## 4. Future Work <a name="section4"></a>
While making a lot of progress, there remain a lot of features which would potentially improve the project.
Here are some of them, including some additional enhancement possibilities:

- add possibility to easily add new analyzing algorithms
- add option to skip file generation and execute ML/DL pipeline directly
- add option to run both pipelines in one execution
- add more options to the GUI for the DL pipeline (Nr. of Epochs/Batches)
- add option to run multiple different ML-pipelines in one execution
- add option to run a specific pipeline _x_- times and take average of the results
- add multi-threading for speed increase
