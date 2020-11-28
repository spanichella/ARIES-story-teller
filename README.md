# Requirements-Collector Enhancement: Combining the ML and DL Pipeline and new Truth Dataset
Requirements-Collector follow up study: (Automating Requirements Specification from Elicitation Sessions and User Feedback)\
Tool - Version 1.1 

**Authors: [Christian Aeberhard](https://github.com/niddhog), [Marc Kramer](https://github.com/Makram95), [Janik Lüchinger](https://github.com/jluech), [Tanzil Kombarabettu Mohammed](https://github.com/tanzilkm)**

## Overview
[Project Description](#section1)\
[Requirement Collector Tool Description](#section2)\
[Installation Guide](#section3)\
[Dataset Description](#section4)\
[Wrapper Component Description](#section5)

## Project Description: <a name="section1"></a>
*In order to speed up the time-to-market of high-quality software products, the requirements-collector tool is being developed. This tool supports an automatic analysis and classification of requirements specification and user feedback by leveraging the power of machine- and deeplearning strategies (for a full description, follow the links mentioned in the [Tool Description](#section2) section). Related studies pointed out that the lack of data from requirement elicitation sessions is an obstacle in this type of investigation. Thus, this study is concerned about extending the requirements and user story dataset of the former study in contemplation of further validating the efficiency and precision of the requirements-collector tool. Additionally, to further increase the usability of the tool and allow a broader audience to interact with it, a flexible environment (StoryTeller GUI) has been developed. The StoryTeller GUI acts as a wrapper and combines both the ML and DL component inside a single executable instance.*

## Requirement Collector Tool Description: <a name="section2"></a>
The **Machine Learning (ML)** and **Deep Learning (DL)** components used in this study are based on the Requirements-Collector Tools as suggeset by [Sebastiano Panichella](https://spanichella.github.io/index.html) and [Marcela Ruiz](https://www.marcelaruiz.eu/). The installation guide and source code of both ML and DL component can be found in the following links:

- [ML-Component](https://github.com/spanichella/Requirement-Collector-ML-Component)\
- [DL-Component](https://github.com/lmruizcar/Requirements-Collector-DL-Component)

## Installation Guide: <a name="section3"></a>
Todo Tanzil (Just as an info to you, Tanzil: this section refers to the point 1.5 in our google docs --> R-Script/Java installation stuff)

## Dataset Description: <a name="section4"></a>
For evaluation of the DL and ML component, a new Dataset based on a Requirements Elecitation Session was created and used to extend the already existing dataset of the original study.

### Requirements Dataset:
The requirements dataset was completely generated from scratch by audio recording a remote Meeting, performing a voice to text transformation and manually labeling the text.

#### Meeting Context and Objective
- **Meeting Environment:** The meeting was performed in a remote fashion using [Zoom](https://zoom.us/) as a conferencing tool.\
- **Meeting Setup:** The meeting included three Participants. Specifically, a **UX Designer**, a **Software Programmer** and the **Project Manager**.\
- **Meeting Topic:** The topic of the meeting was set around the ABC Software Project. The ABC Software (SaaS) is a web based survey tool that supports Team Managers and Project Managers in creating short surveys (Pulse Surveys) with little effort and an intuitive and easy to undestand interface. A Pulse Survey typically takes around 2 - 3 minutes for a survey participant to fill out. Pulse Surveys allow managers to "feel the Pulse" of their team, may it be on the topic of "work satisfaction", "motivation" or "engagement". The project is still in a very early stage, thus the meeting was planned in the course of a series of kick-off meeting where the manager needed UX and technical inputs on a set of different functionalities which the ABC software should include. There are currently 5 software developers, 2 Designers, 2 Slicers and 1 UX Designer as well as Management (consisting of 2 people) working on this project and it is set to launch in Summer 2020 with the initial Phase 1.0 requirements satisfied. It is worth mentioning that the ABC Company acts as a customer in this project and has developped a paper prototype of how the ABC Software should work. The actuall development is done by an outsourcing software development company located in Russia. Thus, the meeting was purely aimed at eliciting requirements of the software in order to discuss them later on with the outsourcing company.
- **ABC Company Description:** The ABC Software Project was launched by the ABC Company, an innovative market research company and leader in employee surveys, 360° leadership feedback and supervisor evaluation throughout Switzerland. The company consists of 12 Employees, was found in 2002 and has over 20 years of expertise in the field in market and company analysis. Based on well-founded study concpets as well as quick, user-friendly survey and meaningful reporting, the company offer a broad variety of products in the field of employee satisfaction, customer satisfaction and Leadership Feedback.

#### Dataset Information
  - The Dataset can be found in the repository and is named **"RE_Transcript_RunTimeTerror.txt"**
  - The Dataset was labeled, using the following coding convention:
    - **F**: Functional Requirement
    - **A**: Non-Functional Requirement (quality requirement)
    - **NULL**: Neither functional nor non-functional
  - The Dataset contains 665 Lines of labeled text and strong semantical errors where manually fixed
  - The LOC is 100%, meaning the data has not been trimmed in any way
  
  ### User Story Dataset:
  The user story dataset was created using a [collection of 22 datasets](https://data.mendeley.com/datasets/7zbk8zsd8y/1) of 50+ requirements each, expressed as user stories. The data was labeled manually and extended by random sampling null lines from the existing dataset in the original study.
  
  #### Dataset Information
  - Todo Tanzil and Marc
  
  ## Wrapper Component Description: <a name="section5"></a>
Todo Marc
![](ML_Component/CombinedProject/Images/swmlogo2.jpg) **funzt erst wenn branch gemerged ist
       
       
       
