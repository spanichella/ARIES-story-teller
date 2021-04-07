# Requirements-Collector Enhancement: Combining ML and DL Pipelines and Extending the Dataset
Requirements-Collector follow-up study: Automating Requirements Specification from Elicitation Sessions and User Feedback\
Tool - Version 1.1

**Authors:** [Christian Aeberhard](https://github.com/niddhog), [Marc Kramer](https://github.com/Makram95), [Janik Lüchinger](https://github.com/jluech), [Tanzil K Mohammed](https://github.com/tanzilkm)



## Overview
1. [Our Work](#section1)
2. [Requirement Collector Tool Description](#section2)
3. [Our Contribution](#section3)
4. [Summary of Results](#section4)



## 1. Our Work<a name="section1"></a>
In order to speed up the time-to-market of high-quality software products, the authors developed the _Requirements-Collector_ tool.
This tool supports an automatic analysis and classification of requirements specification and user feedback by leveraging the power of machine and deep learning strategies
(for a full description of the ML and DL component, please follow the links mentioned in the [Tool Description](#section2) section).
Related studies pointed out that the lack of data from requirement elicitation sessions is an obstacle in this type of investigation.
Thus, this study focuses on extending the requirements and user story dataset of the former study in contemplation of further validating the efficiency and precision of the _Requirements-Collector_ tool.
Additionally, to further increase the usability of the tool and to allow a broader audience to interact with it, we developed a more flexible environment: _StoryTeller_, including a dedicated graphical user interface.

_StoryTeller_ acts as a wrapper and combines both the ML and DL component inside a single executable instance.
The newly created and labeled data sets have been applied to both the ML and DL Pipeline in order to further train the algorithms and evaluate their resulting precision and accuracy values.

This Work has been conducted in the course of the [Software Evolution and Maintenance Module](https://www.ifi.uzh.ch/en/seal/teaching/courses/sme.html) HS2020 at [UZH Department of Informatics](https://www.ifi.uzh.ch/en.html).

[![IMAGE ALT TEXT HERE](combined-pipelines/images/ThumbnailSWM.jpg)](https://www.youtube.com/watch?v=ZXxYfPH8J0E)



## 2. Requirement-Collector Tool Description<a name="section2"></a>
The **Machine Learning (ML)** and **Deep Learning (DL)** components used in this study are based on the _Requirements-Collector_ tool [1] as proposed by [Sebastiano Panichella](https://spanichella.github.io/index.html) and [Marcela Ruiz](https://www.marcelaruiz.eu/).
The corresponding installation guide and source code of both ML and DL component can be found in the following links to their respective GitHub repositories:

- [ML-Component](https://github.com/spanichella/Requirement-Collector-ML-Component)
- [DL-Component](https://github.com/lmruizcar/Requirements-Collector-DL-Component)

**Requirements-Collector Context:**
![](combined-pipelines/images/requirements-collector_context.png)



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

