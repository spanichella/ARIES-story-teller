# StoryTeller: Exploiting and Analyzing User Reviews and User Stories for Supporting Software Evolution in Industry

![](combined-pipelines/images/swmlogo2.jpg)

**Authors:** [Nicolas Ganz](https://www.zhaw.ch/en/about-us/person/gann/), [Christian Aeberhard](https://github.com/niddhog), [Marc Kramer](https://github.com/Makram95), [Janik Lüchinger](https://github.com/jluech), [Tanzil K Mohammed](https://github.com/tanzilkm), [Marcela Ruiz](https://www.zhaw.ch/en/about-us/person/ruiz/), [Lukas Ballo](https://www.linkedin.com/in/lballo/?originalSubdomain=ch), [Sebastiano Panichella](https://spanichella.github.io/)

### License

_StoryTeller_ is licensed under the GNU Affero General Public License. Every file should include a license header, if not, the following applies:

```
_StoryTeller_ is a a tool designed to analyze both user reviews and user stories.
Copyright (C) 2018-2021  Rafael Kallis

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>. 
```

Carefully read the [full license agreement](https://www.gnu.org/licenses/agpl-3.0.en.html).

> "... [The AGPL-3.0 license] requires the operator of a network server to provide the source code of the modified version running there to the users of that server."

## Overview
1. [Summary of the Work](#section1)
2. [_StoryTeller_ Tool Description](#section2)
3. [Our Contributions](#section3)
4. [Evaluation Context](#section4)
5. [Datasets](#section5)

## 1. Summary of the Work<a name="section1"></a>
Addressing user requests in the form of user reviews as well as efficiently and correctly identifying requirements from user stories are essential development tasks to ensure the success of any software organization. User requests and user stories tend to widely differ in their quality, structure, completeness levels, and textual representation. As result, developers are forced to spend a considerable amount of time collecting and analyzing them.
Researchers have proposed tools automating the analysis of user reviews and user stories for supporting software evolution activities. However, these previous studies did not investigate the practical usage (i.e., the scalability), accuracy, and usability of \textit{both user reviews and user stories analysis tools} in industrial settings. 
To fill this gap, we investigate the accuracy and practical usability of _StoryTeller_, a tool designed to analyze both user reviews and user stories, through an industry evaluation involving developers and requirement engineers of a company having more than 20 years of experience in market analysis.
Our industrial evaluation has shown a high classification accuracy of _StoryTeller_. However, its low scalability and usability level required us to refactor, update its dependencies, and design an ad-hoc user interface to make _StoryTeller_ usable in an industrial setting.
We share in this paper our experience, insights, and challenges in sharing, adapting, and evaluating StoryTeller in an industrial setting.

## 2. _StoryTeller_ Tool Description<a name="section2"></a>

**_StoryTeller_** StoryTeller classify user stories and user reviews content by performing the following steps: (1) processing and splitting the text of
user reviews and user stories in sentences; (2) producing a vectorial representation of sentences in user reviews and user stories; (3)
automated classification of user reviews and user stories based on machine learning (ML) or Deep learning (DL) strategies. 
*_StoryTeller_* acts as a wrapper and combines both a ML and DL component inside a single executable instance.
The set of labeled data sets used to apply to both the ML and DL Pipelines and to evaluate their resulting precision and accuracy values are provide in this repository.

**Instructions on how to install _StoryTeller_:** see [combined-pipelines/README.md](./combined-pipelines/README.md)

![Pipeline Status](https://github.com/spanichella/ARIES-story-teller/actions/workflows/java_ci.yml/badge.svg)

## 3. Our Contributions<a name="section3"></a>
 Research tools have been proposed to analyze user stories or user reviews to support software evolution activities. However, very few studies investigated the usability, scalability, and accuracy of a tool enabling the analysis of **_both user reviews and user stories in industrial settings_**. To fill this gap, we investigate the usability, scalability, and accuracy of StoryTeller, a tool we designed to analyze both user reviews and user stories, through an industry evaluation involving developers and requirement engineers of a company called SM-company (detailed in Section \ref{sec:evaluation}), a leader in employee survey evaluations in Switzerland. 

### 4 Evaluation Context<a name="section4"></a>

**SM-company**. For confidentiality reasons, we refer to the  company involved in our industrial evaluation with the Swiss-Marketing Company (*SM-company*) and with the SM Software Project (*SM-Project*), the project that has been considered for the evaluation of StoryTeller.
The *SM-Project* was launched by the \textit{SM-company}, an innovative market research company in Switzerland, a leader in employee surveys, leadership feedback, and supervisor evaluation throughout Switzerland. The company was founded in 2002 and has over 20 years of expertise in the field of market and company analysis. Based on well-founded study concepts as well as quick, user-friendly surveys and meaningful reporting, the company offers a broad variety of products in the field of employee satisfaction, customer satisfaction, and leadership feedback.

### 5 Datasets <a name="section5"></a>
Related studies pointed out that the lack of data from requirement elicitation sessions is an obstacle in this type of classification approach. 
Thus, we provided [datasets](datasets) for validating the accuracy and usability of the _StoryTeller_ tool. 

To assess the classification accuracy achieved by the ML pipeline of StoryTeller, we conducted an empirical evaluation involving a dataset of user stories provided by the *SM-company*. three participants of the *SM-company* such as a developer, a software architect, and a requirement engineer,  shared with us a dataset concerning an internal RE meeting concerning the *SM-Project*, containing the corresponding anonymized textual transcripts, derived from the transcribed audio recording of the RE meeting by the participants. Thus, we asked the participants to manually (and collaboratively) label the data in a format compatible with StoryTeller. As result, we obtained a [dataset](datasets)  (available in our repository) having in total 664 statements that have been manually labeled as *functional* (150) or *non-functional* (45) requirements, or as irrelevant (the remaining 469) statements by the participants.

### ACKNOWLEDGMENTS & Credit Author Statement
We gratefully acknowledge the Innosuisse support for the project *ARIES* (Exploiting User Journeys for Supporting Mobility as a Service Platforms),
Project No.45548.1. 

As follow, the Credit Author Statement for each of the authors. 
- Nicolas Ganz: Data curation, Software, Writing - review & editing.
- Christian Aeberhard, Marc Kramer, Janik Lüchinger, Tanzil Mohammed: Software,  Validation.
- Lukas Ballo: Review.
- Marcela Ruiz: Review.
- Sebastiano Panichella: Data curation, Conceptualization, Software, Methodology, Investigation, Validation, Writing - original draft & editing, Project administration.


