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
3. [Our Contribution](#section3)
4. [Summary of Results](#section4)

## 1. Summary of the Work<a name="section1"></a>
Addressing user requests in the form of user reviews as well as efficiently and correctly identifying requirements from user stories are essential development tasks to ensure the success of any software organization. User requests and user stories tend to widely differ in their quality, structure, completeness levels, and textual representation. As result, developers are forced to spend a considerable amount of time collecting and analyzing them.
Researchers have proposed tools automating the analysis of user reviews and user stories for supporting software evolution activities. However, these previous studies did not investigate the practical usage (i.e., the scalability), accuracy, and usability of \textit{both user reviews and user stories analysis tools} in industrial settings. 
To fill this gap, in this paper, we investigate the accuracy and practical usability of _StoryTeller_, a tool designed to analyze both user reviews and user stories, through an industry evaluation involving developers and requirement engineers of a company having more than 20 years of experience in market analysis.
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

## 3. Our Contribution<a name="section3"></a>
Our study is an extension of the aforementioned original [study](#section2) and evolves around the following research question:

### 3 Datasets
Related studies pointed out that the lack of data from requirement elicitation sessions is an obstacle in this type of classification approach.
Thus, we focused on extending the requirements and user story [dataset of the former study](datasets/study) in contemplation of further validating the efficiency and precision of the _Requirements-Collector_ tool.

## References
- [1] S. Panichella and M. Ruiz Requirements-Collector: Automating Requirements Specification from Elicitation Sessions and User Feedback .   IEEE International Requirements Engineering Conference (RE’20).

