# StoryTeller: Exploiting and Analyzing User Reviews and User Stories for Supporting Software Evolution in Industry

![Pipeline Status](https://github.com/spanichella/ARIES-story-teller/actions/workflows/java_ci.yml/badge.svg)

**Authors:** [Nicolas Ganz](https://www.zhaw.ch/en/about-us/person/gann/), [Christian Aeberhard](https://github.com/niddhog), [Marc Kramer](https://github.com/Makram95), [Janik Lüchinger](https://github.com/jluech), [Tanzil K Mohammed](https://github.com/tanzilkm), [Marcela Ruiz](https://www.zhaw.ch/en/about-us/person/ruiz/), [Lukas Ballo](https://www.linkedin.com/in/lballo/?originalSubdomain=ch), [Sebastiano Panichella](https://spanichella.github.io/)

**Instructions:** see [combined-pipelines/README.md](./combined-pipelines/README.md)

## Overview
1. [Abstract](#section1)
2. [_StoryTeller_ Tool Description](#section2)
3. [Our Contribution](#section3)
4. [Summary of Results](#section4)



## 1. Abstract<a name="section1"></a>
Addressing user requests in the form of user reviews as well as efficiently and correctly identifying requirements from user stories are essential development tasks to ensure the success of any software organization. User requests and user stories tend to widely differ in their quality, structure, completeness levels, and textual representation. As result, developers are forced to spend a considerable amount of time collecting and analyzing them.
Researchers have proposed tools automating the analysis of user reviews and user stories for supporting software evolution activities. However, these previous studies did not investigate the practical usage (i.e., the scalability), accuracy, and usability of \textit{both user reviews and user stories analysis tools} in industrial settings. 
To fill this gap, in this paper, we investigate the accuracy and practical usability of _StoryTeller_, a tool designed to analyze both user reviews and user stories, through an industry evaluation involving developers and requirement engineers of a company having more than 20 years of experience in market analysis.
Our industrial evaluation has shown a high classification accuracy of _StoryTeller_. However, its low scalability and usability level required us to refactor, update its dependencies, and design an ad-hoc user interface to make _StoryTeller_ usable in an industrial setting.
We share in this paper our experience, insights, and challenges in sharing, adapting, and evaluating StoryTeller in an industrial setting.


**_StoryTeller_** acts as a wrapper and combines both the ML and DL component inside a single executable instance.
The set of labeled data sets used to apply to both the ML and DL Pipelines and to evaluate their resulting precision and accuracy values are provide in this repository.

## 2. _StoryTeller_ Tool Description<a name="section2"></a>
The **Machine Learning (ML)** and **Deep Learning (DL)** components used in this study are based on the _Requirements-Collector_ tool [1] as proposed by [Sebastiano Panichella](https://spanichella.github.io/index.html) and [Marcela Ruiz](https://www.marcelaruiz.eu/).
The corresponding installation guide and source code of both ML and DL component can be found in the following links to their respective GitHub repositories:

- [ML-Component](https://github.com/spanichella/Requirement-Collector-ML-Component)
- [DL-Component](https://github.com/lmruizcar/Requirements-Collector-DL-Component)

## 3. Our Contribution<a name="section3"></a>
Our study is an extension of the aforementioned original [study](#section2) and evolves around the following research question:

**"Does running the ML and DL algorithm using an extended dataset lead to better precision and recall values?"**

We addressed this question by implementing the following:\
For evaluation of the DL and ML component, we created a new dataset based on a recreated requirement elicitation session and used the audio recording to extend the already existing dataset of the original study.
The resulting complete dataset (see [datasets folder](./datasets)) achieved better performance, as is discussed in section [Study Results](datasets/README.md#study-results).

Furthermore, in order to facilitate usage and make our tool more accessible to a broader audience, we combined the separate ML and DL Pipeline into a combined wrapper component and created an intuitive, easy to use graphical user interface (GUI).

Finally, we evaluated the ML and DL component in terms of precision, recall and F-measure values, using our newly created dataset and comparing the results to the ones achieved in the former study.
These findings allowed us to address and reflect upon our research question.

### 3.1 Dataset Extension
Related studies pointed out that the lack of data from requirement elicitation sessions is an obstacle in this type of classification approach.
Thus, we focused on extending the requirements and user story [dataset of the former study](datasets/study) in contemplation of further validating the efficiency and precision of the _Requirements-Collector_ tool.

### 3.2 Pipeline Combination
During our project, we wrapped the two pipelines into one executable program.
To make it more user-friendly, we changed the setup and execution procedure from ground up:
We removed the command-line interaction as well as any manual specifications of local paths, and replaced them with a simple graphical user interface instead.
While merging the pipelines we also refactored, cleaned up, and fixed the code, such that it now also works on Windows.
A more in-depth description of the changes and the code in general, as well as possible future improvements can be found in the specific code README [here](combined-pipelines/README.md).

![](combined-pipelines/images/swmlogo2.jpg)



## 4. Summary of Results<a name="section4"></a>
We used the original "Study" dataset as well as our new "Complete" dataset to experiment with an ML (supervised) pipeline in order to evaluate the pipeline's accuracy.
Our results with the ML model of the "Complete" dataset have shown better performance compared to the original "Study" dataset.
While `A` labelled instances in the "Study" dataset have not reached usable values above 0%, in the "Complete" dataset we reached values for precision, recall, and F-measure of at least 42%.

The original "Study" dataset reached an average accuracy of a precision of 68%, recall of 75%, and F-measure of 71%.
On the contrary, our "Complete" dataset performed noticeably better with a precision of 93%, recall of 93%, and F-measure of 92%.

Extending the Dataset has shown significantly better performance and Accuracy in ML pipeline.
As future work we just suggest extending the dataset for better performance and accuracy.



## Project Team
<table>
    <tr style="border: 0; text-align: center">
        <td style="border: 0">
            <p style="font-weight: bold">Christian Aeberhard</p>
            <a href="https://github.com/niddhog"><img alt="Christian Aeberhard" src="combined-pipelines/images/Chris_Bubble.png"></a>
        </td>
        <td style="border: 0">
            <p style="font-weight: bold">Marc Kramer</p>
            <a href="https://github.com/Makram95"><img alt="Marc Kramer" src="combined-pipelines/images/Marc_Bubble.png"></a>
        </td>
        <td style="border: 0">
            <p style="font-weight: bold">Janik Lüchinger</p>
            <a href="https://github.com/jluech"><img alt="Janik Lüchinger" src="combined-pipelines/images/Janik_Bubble.png"></a>
        </td>
        <td style="border: 0">
            <p style="font-weight: bold">Tanzil Mohammed</p>
            <a href="https://github.com/tanzilkm"><img src="combined-pipelines/images/Tanzil_Bubble.png" alt="Tanzil Mohammed"></a>
        </td>
    </tr>
</table>



## References
- [1] S. Panichella and M. Ruiz Requirements-Collector: Automating Requirements Specification from Elicitation Sessions and User Feedback .   IEEE International Requirements Engineering Conference (RE’20).

