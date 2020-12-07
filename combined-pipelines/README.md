# Storyteller: The combined Project

## Overview
1. [Introduction](#section1)
2. [Program Description](#section2)
3. [Change Description](#section3)
4. [Future Work](#section4)

## 1. Introduction <a name="section1"></a>
Storyteller is our combined integration of the [ML-Component](https://github.com/spanichella/Requirement-Collector-ML-Component) and the [DL-Component](https://github.com/lmruizcar/Requirements-Collector-DL-Component) used to automatically analyze User Stories and Requirements Specifications. During the project, we merged the two pipelines step by step with focus to make them userfriendly to people with no computer-science background.

## 2. Program Description <a name="section2"></a>
The goal of this part is to give a brief description of how our code works.

### 2.1 Setup Information
For information about setup and use, please refer to the instruction provided [here](https://github.com/Makram95/SWME_G2_HS20/blob/main/README.md).

### 2.2 Pipeline description
When launching the program, the user gets presented with a simple graphical user interface (GUI). There he must select the location of the dataset and the type of its content. He then can further select the desired pipeline, for User Stories only the ML-pipeline is available, for Requirement Specifications both the ML and DL pipeline are available. Depending on what he selected he can further specify some attributes regarding the execution of the chosen pipeline. If everything is selected and specified correctly, he can finaly click on execute to run the analysis.

On execution, the code will first create an XML-File containing local paths used for reading the existing, or generating the new files. After this, the code will start with the generation of the training- and test-set, depending on the selections made before in the GUI. These files are created by the R-scripts located in the Ressources folder. There is one script for each type of data (req. spec. or user review).

When this is done the analysis part begins. Again, depending on the selections before, our code will execute the ML or DL pipeline. Especially the DL pipeline takes some time to be executed (upto 40min in our case). As soon as the results and generated models are availeble, the user can find them in the Results and Models folder.

## 3. Change Description <a name="section3"></a>
This part is to explain the  implementation and the changes we made during it.

### 3.1 Refactoring the ML-Component
When first launching the ML-Component at the beginning of the project, we needed to specify all local paths manually in 4 different XML files. To then run the code we had to copy, edit and then paste two commands, specific to the data type and in the right order, into a console. While this would not be a problem for experienced users, it certainly would for less experienced. Additionaly the code would only generate results on macOS and Linux.

We begann refactoring the code step by step in a top-down approach. First we integrated automatic identification of the local paths, except for the one of the initial dataset. The automatic XML generation alone, already takes out a lot of complexity and makes the set-up process a lot faster.

The next step was to remove the console commands. Since they called just two different functions, we are now just executing them after each other. This brings the burden, that all the file generation gets executed everytime, as well when a user only wants to re-run his selection with a different ML strategy or with the DL pipeline.

Then we cleaned out a lot of unecessary code by refactoring, added some comments and changed some variable and function names to impove readability and maintainability. As a last point we changed a single line in an R-Script to make the pipeline work on Windows machines too.

### 3.2 Adding a GUI
By removing the commandline interaction, we needed a new and easier way to enter information needed. We solved this by implementing a simple GUI. When the programm gets launched, it shows up and the user can specify some options for running the pipelines. He can choose between ML and DL for Requirement Specifications, and just ML for the User Stories. When selecting ML he can further specify the Strategy, and 10-fold or a the splitpercentage for training- and test set. When selecting DL he currently can't specify further options.

The selected options then get used to create the XML file, and then will change the behavior of the execution of the pipelines.

### 3.3 Integrating the DL-Component
As a last step we integrated the DL-Component. We succeeded to use the same structure of the files that are also generated for the ML Pipeline, so we could skip adding a special script. In the end we had to only modify some hard coded value and did not need to touch the logic at all. These were for example to get the paths from the XML too instead and we added the new DL dependencies to our pom.xml file.

## 4. Future Work <a name="section4"></a>
While making a lot of progress, there are still a lot features, which would improve the Project, to be implemented. 
Here are some of them, plus some further possible enhancements:

- add possibility to easly add new analyzing algorithms
- add option to skip file generation and execute ML/DL pipeline directly
- add option to run both pipelines in one execution
- add more options to the GUI for the DL pipeline (Nr. of Epochs/Batches)
- add option to run multiple different ML-pipelines in one execution
- add option to run a specific pipeline _x_- times and take average of the results
- add multi-threading for speed increase
