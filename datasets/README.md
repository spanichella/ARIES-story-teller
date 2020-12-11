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



# Creation of Datasets

## Recording Dataset
The requirements dataset was generated completely from scratch by recording an elicitation meeting, transcribing the test by applying a voice-to-text transformation, and finally manually labeling the data. In order to ensure anonymity, the company name as well as the project name have been anonymized and do not represent real names.

### Meeting Context and Objective
- **Meeting Environment:** The meeting was held in a remote fashion using [Microsoft Teams](https://www.microsoft.com/de-ch/microsoft-365/microsoft-teams/group-chat-software) as a conferencing tool.
- **Meeting Setup:** The meeting included three participants: a UX designer, a software engineer, and the project manager.
- **Meeting Topic:** The topic of the meeting was set around the _ABC Software_ project.
  The _ABC Software_ (SaaS) is a web based survey tool that supports team managers and project managers in creating short surveys (Pulse Surveys) with little effort and an intuitive and an easy to understand interface.
  A Pulse Survey typically takes around 2 - 3 minutes for a survey participant to fill out.
  Pulse Surveys allow managers to "feel the pulse" of their team, may it be on the topic of "work satisfaction", "motivation", or "engagement".
  The project is still in a very early stage, thus the meeting was planned in the course of a series of kick-off meetings where the manager needed UX and technical inputs on a set of different functionalities which the ABC software should include.
  There are currently 5 software developers, 2 designers, 2 slicers and 1 UX designer as well as management (consisting of 2 people) working on this project, and it is set to launch in Summer 2020 with the initial phase 1.0 requirements satisfied.
  It is worth mentioning that the ABC Company acts as a customer in this project and has developed a paper prototype of how the _ABC Software_ should work.
  The actual development is done by an outsourcing software development company located in Russia.
  Thus, the meeting was purely aimed at eliciting requirements of the software in order to discuss them later on with the outsourcing company.
- **ABC Company Description:** The ABC Software Project was launched by the ABC Company, an innovative market research company and leader in employee surveys, 360° leadership feedback and supervisor evaluation throughout Switzerland.
  The company consists of 12 Employees, was founded in 2002 and has over 20 years of expertise in the field in market and company analysis.
  Based on well-founded study concepts as well as quick, user-friendly survey and meaningful reporting, the company offers a broad variety of products in the field of employee satisfaction, customer satisfaction, and leadership feedback.

### Dataset Information
- The dataset can be found in the repository and is named [truth_set_recording-ReqSpec.txt](./recording/truth_set_recording-ReqSpec.txt)
- The dataset was labeled using the following coding conventions:
    - **F**: Functional Requirement
    - **A**: Non-Functional Requirement (quality requirement)
    - **NULL**: Neither functional nor non-functional
- The dataset contains 665 lines of labeled lines of tab-separated values, including line number, text content, and classification label
- Strong semantic errors in the transcription due to misspelling or mis-identification of words have been manually fixed

### Dataset Creation
- We collected the software specifications of the project and derived some project requirements
- We recreated an elicitation session with three participants as described above
- Then we converted the recording to text using a dedicated speech-to-text conversion tool.
  Specifically, we used [rev.com](https://www.rev.com/), an online tool to convert speech to text
- Finally, we received a raw text file from the online tool for which we then had to label each sentence according to the labels described above


## User Stories Dataset
The user story dataset was created using a [collection of datasets](https://data.mendeley.com/datasets/7zbk8zsd8y/1) of 50+ requirements each - expressed as user stories - and combining them with randomly selected `NULL` lines from the existing dataset in the original study.

### Dataset Information
- The dataset can be found in the corresponding dataset folder as [truth_set_stories-ReqSpec.txt](./stories/truth_set_stories-ReqSpec.txt)
- The dataset contains 1215 lines of labeled lines of tab-separated values, including line number, text content, and classification label

### Dataset Creation
- From existing datasets (mentioned above) containing different files of user stories we collected 615 lines of requirements (files g02-04, g08, g10-14; contained in the [sources folder](./_sources/datasets))
- We manually labeled the dataset as Functional Requirement (`F`) or Non-Functional Requirement (`A`)
- In order to balance the dataset we added 600 `NULL` lines by randomly selecting matching lines from the dataset [truth_set_study-ReqSpec.txt](./study/truth_set_study-ReqSpec.txt) of the previous work.
  We used [Microsoft Excel](https://www.microsoft.com/en-ww/microsoft-365/excel) for this by sorting the dataset according to the classifications and shuffling the lines using [random sort in Excel](https://www.ablebits.com/office-addins-blog/2018/01/24/excel-randomize-list-random-sort/).
  We then combined the findings with our gathered user stories and shuffled the dataset again for its final version


## Generation Script
In this folder you can also find a Python script we used to generate some necessary files that are being used internally during the analysis.
To be more precise, we first convert the given truth set (a `.txt` file) of each dataset into a `.csv` file.
We then extract a training and test set (`.csv` files) out of the truth set by randomly selecting lines according to the partitioning 80:20 and 50:50 (training:test).

Using the ML pipeline of the previous work, we generated the term-by-document matrix files ("tdm", `.csv` files) of each corresponding truth set and copied them into their dedicated folders.
These tdm files are then used to create tdm pendants of the training and test sets by picking the relevant lines contained in the `.csv` files out of the truth set tdm file and writing them to a new file (two files per partitioning per dataset, `.csv` files).
The resulting tdm files can ultimately be used as input to the WEKA classifier.



# Study Results and Discussion<a name="study-results"></a>
- **Precision** is a metric which quantifies the number of correct positive predictions made and is calculated as the ratio of correctly predicted positive examples (out of the total retrieved) divided by the total number of retrieved examples. [3]
- **Recall** is a metric which quantifies the number of correct positive predictions made out of all positive predictions there are. Recall provides an indication of missed positive predictions (contrary to precision).[3]
- **F-measure** is a measure of a test's accuracy and is defined as the weighted harmonic mean of the precision and recall of the test. 


## Discussing Precision and Recall of ML Pipeline
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
- [3] A. Fernandez, S. Garcia, M. Galar, R. C. Prati, B. Krawczyk, F. Herrera, 2018. Learning from Imbalanced Data Sets, 1st ed. 2018 Edition
