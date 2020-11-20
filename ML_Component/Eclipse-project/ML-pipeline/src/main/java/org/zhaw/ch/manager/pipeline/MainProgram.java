package org.zhaw.ch.manager.pipeline;

/**
 * 
 * @author panc
 *
 * 	This abstract class is for running the main steps of the ML- and DL-based classification pipelines
 *  concerning User reviews (extracted from App stores) and Requirements Specifications (automatically 
 *  deducted from development audio recordings).
 * 
 *  The main class take as input:
 *  
 *  1. Basic arguments to analyse and pre-process the oracle information (labels from the dataset), which support
 *     - 1.a Specific user reviews file format (see testing examples).
 *     - 1.b Specific Requirement Specification file format (see testing examples).
 *     
 *  2.  Basic arguments to generate features for the ML classification step (labels from the dataset are used as variable 
 *  	for the prediction). 
 *      This step supports the analysis of:
 *     - 2.a Specific user reviews file format (see testing examples).
 *     - 2.b Specific Requirement Specification file format (see testing examples).
 *
 *  3.  Basic arguments to use features for training multiple binary classifier models based on ML- and DL-based pipelines. 
 *      This step supports the classification of:
 *     - 3.a Specific user reviews file format (see testing examples).
 *     - 3.b Specific Requirement Specification file format (see testing examples).     
 *      
 */
public class MainProgram {
	
	protected String dataType;
	protected String classifierToolChain;
	protected String machineLearningModelName;
	protected String nameOfAttributeID;
	protected String nameOfAttributeText;
	protected String nameOfAttributeClass;
	

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getClassifierToolChain() {
		return classifierToolChain;
	}

	public void setClassifierToolChain(String classifierToolChain) {
		this.classifierToolChain = classifierToolChain;
	}

	public String getMachineLearningModelName() {
		return machineLearningModelName;
	}

	public void setMachineLearningModelName(String machineLearningModelName) {
		this.machineLearningModelName = machineLearningModelName;
	}

	public String getNameOfAttributeID() {
		return nameOfAttributeID;
	}

	public void setNameOfAttributeID(String nameOfAttributeID) {
		this.nameOfAttributeID = nameOfAttributeID;
	}

	public String getNameOfAttributeText() {
		return nameOfAttributeText;
	}

	public void setNameOfAttributeText(String nameOfAttributeText) {
		this.nameOfAttributeText = nameOfAttributeText;
	}

	public String getNameOfAttributeClass() {
		return nameOfAttributeClass;
	}

	public void setNameOfAttributeClass(String nameOfAttributeClass) {
		this.nameOfAttributeClass = nameOfAttributeClass;
	}
	
	

}
