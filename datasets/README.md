# Datasets Description

## SM-company Industrial Dataset - (Folder "Recording")
This dataset has been crafted from a custom requirement elicitation session transcript.
We recorded such a session, transcribed it to text using dedicated tools, and manually formatted and labeled the data.

## SM-company Industrial Dataset combined with other Open-Source Datasets - (Folder "Combined)
This dataset makes up our contribution of collected data to the project.
Specifically, it combines the following two datasets: Recording, Stories.

## Folder "Stories"
This dataset is made up of user stories (taken from an openly accessible, already established dataset [1]) for valid requirements and rubbish data to fill for classification (randomly chosen `NULL` lines from existing dataset [2]).

## Folder "Complete"
This dataset is a collection of all the datasets merged into one single dataset.
Specifically, it includes the following datasets: Recording and Stories.

# Datasets Creation

## SM-company Industrial Dataset

To assess the classification accuracy achieved by the ML pipeline of StoryTeller, we conducted an empirical evaluation involving a dataset of user stories provided by the *SM-company*. Three participants of the *SM-company* such as a developer, a software architect, and a requirement engineer,  shared with us a dataset concerning an internal RE meeting concerning the *SM-Project*, containing the corresponding anonymized textual transcripts, derived from the transcribed audio recording of the RE meeting by the participants. Thus, we asked the participants to manually (and collaboratively) label the data in a format compatible with StoryTeller. As result, we obtained a [dataset](datasets)  (available in our repository) having in total 664 statements that have been manually labeled as *functional* (150) or *non-functional* (45) requirements, or as irrelevant (the remaining 469) statements by the participants.

This requirements dataset was anonymized and do not represent real names. 

### Meeting Context and Objective
- **Meeting Environment:** The meeting was held in a remote fashion using [Microsoft Teams](https://www.microsoft.com/de-ch/microsoft-365/microsoft-teams/group-chat-software) as a conferencing tool.
- **Meeting Setup:** The meeting included three participants: a UX designer, a software engineer, and the project manager.
- **Meeting Topic:** The topic of the meeting was set around the *SM-Project*.
  The _SM-Project Software_ (SaaS) is a web based survey tool that supports team managers and project managers in creating short surveys (Pulse Surveys) with little effort and an intuitive and an easy to understand interface.
  A Pulse Survey typically takes around 2 - 3 minutes for a survey participant to fill out.
  Pulse Surveys allow managers to "feel the pulse" of their team, may it be on the topic of "work satisfaction", "motivation", or "engagement".
  The project is still in a very early stage, thus the meeting was planned in the course of a series of kick-off meetings where the manager needed UX and technical inputs on a set of different functionalities which the ABC software should include.
  There are currently 5 software developers, 2 designers, 2 slicers and 1 UX designer as well as management (consisting of 2 people) working on this project, and it was set to launch in Summer 2020 with the initial phase 1.0 requirements satisfied.
  It is worth mentioning that the SM-Company acts as a customer in this project and has developed a paper prototype of how the _SM-Project Software_ should work.
  The actual development is done by an outsourcing software development company located in Russia.
  Thus, the meeting was purely aimed at eliciting requirements of the software in order to discuss them later on with the outsourcing company.
- **SM-company**. For confidentiality reasons, we refer to the  company involved in our industrial evaluation with the Swiss-Marketing Company (*SM-company*) and with the SM Software Project (*SM-Project*), the project that has been considered for the evaluation of StoryTeller.
The *SM-Project* was launched by the \textit{SM-company}, an innovative market research company in Switzerland, a leader in employee surveys, leadership feedback, and supervisor evaluation throughout Switzerland. The company was founded in 2002 and has over 20 years of expertise in the field of market and company analysis. Based on well-founded study concepts as well as quick, user-friendly surveys and meaningful reporting, the company offers a broad variety of products in the field of employee satisfaction, customer satisfaction, and leadership feedback.

### Datasets Information
- The datasets are labeled using the following coding conventions:
    - **F**: Functional Requirement
    - **A**: Non-Functional Requirement (quality requirement)
    - **NULL**: Neither functional nor non-functional
- The dataset contains labeled lines of tab-separated values, including line number, text content, and classification label
- Semantic errors in the transcription due to misspelling or mis-identification of words have been manually fixed

# Sources
- [1] F. Dalpiaz, “Requirements data sets (user stories)”, Mendeley Data, V1, 2018, doi: 10.17632/7zbk8zsd8y.1
- [2] S. Panichella and M. Ruiz, "Requirements-Collector: Automating Requirements Specification from Elicitation Sessions and User Feedback," 2020 IEEE 28th International Requirements Engineering Conference (RE), Zurich, Switzerland, 2020, pp. 404-407, doi: 10.1109/RE48521.2020.00057
- [3] A. Fernandez, S. Garcia, M. Galar, R. C. Prati, B. Krawczyk, F. Herrera, 2018. Learning from Imbalanced Data Sets, 1st ed. 2018 Edition
