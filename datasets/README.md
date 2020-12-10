# Datasets

## Complete <a name="CompleteDS"></a>
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

## Study <a name="study-dataset"></a>
This dataset represents the data used in the previous work [2] and has not been adjusted in any way.


# Generation Script
In this folder you can also find a Python script we used to generate some necessary files that are being used internally during the analysis.
To be more precise, we first convert the given truth set (a `.txt` file) of each dataset into a `.csv` file.
We then extract a training and test set (`.csv` files) out of the truth set by randomly selecting lines according to the partitioning 80:20 and 50:50 (training:test).

Using the ML pipeline of the previous work, we generated the term-by-document matrix files ("tdm", `.csv` files) of each corresponding truth set and copied them into their dedicated folders.
These tdm files are then used to create tdm pendants of the training and test sets by picking the relevant lines contained in the `.csv` files out of the truth set tdm file and writing them to a new file (two files per partitioning per dataset, `.csv` files).
The resulting tdm files can ultimately be used as input to the WEKA classifier.


# Study Results and Discussion
- **Precision** is a metric which quantifies the number of correct positive predictions made and is calculated as the ratio of correctly predicted positive examples (out of the total retrieved) divided by the total number of retrieved examples.
- **Recall** is a metric which quantifies the number of correct positive predictions made out of all positive predictions there are. Recall provides an indication of missed positive predictions (contrary to precision).
- **F-measure** is a measure of a test's accuracy and is defined as the weighted harmonic mean of the precision and recall of the test. 


## Discussion of Precision and Recall From ML Pipeline

**Original Study Dataset:**\
![](../combined-pipelines/images/Original_Study_Result_Graph.png)\
The above graph depicts the results of the ML model for the original "Study" dataset with a 50% split of the dataset (test and training set).
The graph shows that classification of `NULL` labelled instances resulted in a precision of 80%, recall of 93%, and F-measure of 98%.
Classifying `F` labelled instances resulted in a precision of 45%, recall of 32%, and F-measure of 38%,
while classification of `A` labelled instances performed poorly in the original study with a precision, recall, and F-measure of 0%.
This poor performance of `A` labelled instances affected the performance of the weighted average in the original dataset, as can be seen in the graph.

**New Complete Dataset:**\
![](../combined-pipelines/images/New_Dataset_Result_Graph.png)\
The above graph depicts the results of the ML model for the "Complete" dataset with a 50% split of the dataset (test and training set).
The graph shows that classification of `NULL` labelled instances resulted in a precision of 94%, recall of 99%, and F-measure of 96%.
Classifying `F` labelled instances resulted in a precision of 84%, recall of 72%, and F-measure of 78%,
while `A` labelled instances performed better in comparison to the original "Study" dataset with a precision of 92%, recall of 42%, and F-measure of 57%.

**Original Study Dataset vs. New Complete Dataset:**\
![](../combined-pipelines/images/Compare_Both_Dataset_Result_Graph.png)\
In the above graph we compare the performance of the original "Study" dataset with the "Complete" dataset.
As we can see, the "Complete" dataset performed significantly better compared to the original "Study" dataset (using the ML pipeline).
More specifically, the `A` labelled lines in the "Complete" dataset have shown clearly visible and competitive values,
where they were at 0% in the original "Study" dataset.\
On average, the original "Study" dataset had a precision of 68%, recall of 75%, and F-measure of 71%.
The "Complete" dataset - on average - had a precision of 93%, recall of 93%, and F-measure of 92%.


# Sources
- [1] F. Dalpiaz, “Requirements data sets (user stories)”, Mendeley Data, V1, 2018, doi: 10.17632/7zbk8zsd8y.1
- [2] S. Panichella and M. Ruiz, "Requirements-Collector: Automating Requirements Specification from Elicitation Sessions and User Feedback," 2020 IEEE 28th International Requirements Engineering Conference (RE), Zurich, Switzerland, 2020, pp. 404-407, doi: 10.1109/RE48521.2020.00057
