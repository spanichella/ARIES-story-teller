# Datasets

## Complete
This dataset is a collection of all the datasets merged into one single dataset.
Specifically, it includes the following datasets: Recording, Stories, Study.

## Combined
This dataset makes up our contribution of collected data to the project.
Specifically, it combines the following two datasets: Recording, Stories.

## Recording
This dataset has been crafted from a custom requirement elicitation session transcript.
We recorded such a session, transcribed it to text using dedicated tools, and manually formatted and labeled the data.

## Stories
This dataset is made up of user stories (taken from an openly accessible, already established dataset [1]) for valid requirements and rubbish data to fill for classification (randomly chosen `NULL` lines from existing dataset [2]).

## Study
This dataset represents the data used in the previous work [2] and has not been adjusted in any way.



# Generation Script
In this folder you can also find a Python script we used to generate some necessary files that are being used internally during the analysis.
To be more precise, we first convert the given truth set (a `.txt` file) of each dataset into a `.csv` file.
We then extract a training and test set (`.csv` files) out of the truth set by randomly selecting lines according to the partitioning 80:20 and 50:50 (training:test).


Using the ML pipeline of the previous work, we generated the term-by-document matrix files ("tdm", `.csv` files) of each corresponding truth set and copied them into their dedicated folders.
These tdm files are then used to create tdm pendants of the training and test sets by picking the relevant lines contained in the `.csv` files out of the truth set tdm file and writing them to a new file (two files per partitioning per dataset, `.csv` files).
The resulting tdm files can ultimately be used as input to the WEKA classifier.



# Sources
- [1] F. Dalpiaz, “Requirements data sets (user stories)”, Mendeley Data, V1, 2018, doi: 10.17632/7zbk8zsd8y.1
- [2] S. Panichella and M. Ruiz, "Requirements-Collector: Automating Requirements Specification from Elicitation Sessions and User Feedback," 2020 IEEE 28th International Requirements Engineering Conference (RE), Zurich, Switzerland, 2020, pp. 404-407, doi: 10.1109/RE48521.2020.00057
