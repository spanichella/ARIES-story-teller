package org.zhaw.ch.manager.pipeline;

import org.zhaw.ch.R_Tm_package.TermByDocumentCreation;
import org.zhaw.ch.configFile.XMLInitializer;
import org.zhaw.ch.ml.WekaClassifier;
import org.zhaw.ch.oracle.OracleRequirementSpecificationsAnalyzer;
import org.zhaw.ch.oracle.OracleUserReviewsAnalyzer;

/**
 * 
 * @author panc
 *
 * 	This class extends the Main Program class by supporting the execution of  
 * 	the main steps of the ML- and DL-based classification pipelines
 *  concerning Requirement Specifications (extracted from audio recordings).
 * 	
 */
public class MainRequirementSpecificationsPipeline extends MainProgram {

	// PART 1. - ORACLE PARAMETERS
	private String docs_location;
	//local path to the R script "MainScript.r"
	 private String pathRScript ;
			// here are located the "documents" folder and the  "utilities.R script"
	 private String baseFolder ;
			// path oracle 
	 private String oracle_path ;
			// path threshold 
	 private double threshold ;
	 
		//local path to the R script "MainScript.r"
	 private String pathTbDRScript ;
	
		// locations of training and test sets
	 private String documentsTrainingSet ;
	 private String documentsTestSet ;
		// path oracle
	 private String simplifiedOracle_path ;
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//RS, UR
		String mainPath = MainRequirementSpecificationsPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/","");
		if(OS.contains("win")){
			mainPath=mainPath.substring(1);
		}
		//Fetch path of thi

		//Set Path for R-script folder
		String scripts_location = mainPath + "Resources/R-scripts/";
		//Set Path for ReqSpec folder, where truth set is located
		String docs_location = mainPath + "Resources/ReqSpec/";

		// TODO Auto-generated method stub
	    // PART 1. - ORACLE  - SOME PARAMETERS ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)
		System.out.println("PART 1. - ORACLE Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)\n");
			String pathRScriptOracle = scripts_location+"Script-to-create-test-dataset-Req-Specifications.r";
			String baseFolder = docs_location;
		// path truth-set
			String oracle_path = docs_location+"truth_set_ReqSpecification.txt";
		// path threshold TODO: this is the split of training/testset i think - marc
			double threshold = 0.5;
				
		// Type of the data to classify
		String dataType = "Requirement Specifications";
		// Name of the column "ID" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
		String nameOfAttributeID = "id"; 
		// Name of the column "review" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
		String nameOfAttributeText = "req_specification"; 
		// Name of the column "class" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
		String nameOfAttributeClass = "class"; 
		
		//we update parameters - used later to run oracle analysis
		MainRequirementSpecificationsPipeline mainRequirementSpecificationsPipeline= new MainRequirementSpecificationsPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);      
		mainRequirementSpecificationsPipeline.setPathRScript(pathRScriptOracle);
		mainRequirementSpecificationsPipeline.setBaseFolder(baseFolder);
		mainRequirementSpecificationsPipeline.setOracle_path(oracle_path);
		mainRequirementSpecificationsPipeline.setThreshold(threshold);

		// We  run the ORACLE analysis
		OracleRequirementSpecificationsAnalyzer oracleRequirementSpecificationsAnalyzer = mainRequirementSpecificationsPipeline.runOracleAnalysis();
		// END of 1. PART OF ORACLE 
		System.out.println("\nEND of PART 1. - ORACLE Analysis\n\n ");
		/**/
	    // PART 2. - TbD Analysis - SOME OF THEM ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)
		System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)\n");
		//local path to the R script "MainScript.r"
		String pathTbDRScript = scripts_location+"MainScript.r";
		// locations of training and test sets
		String documentsTrainingSet = docs_location+"training-set-Req-Specifications";
		String documentsTestSet = docs_location+"test-set-Req-Specifications";
		// path oracle
		String simplifiedOracle_path = docs_location+"truth_set-simplified-Req-Specifications.csv";
		
		//we update parameters - used later to run TbD analysis
		mainRequirementSpecificationsPipeline.setDocs_location(scripts_location);
		mainRequirementSpecificationsPipeline.setPathTbDRScript(pathTbDRScript);
		mainRequirementSpecificationsPipeline.setDocumentsTrainingSet(documentsTrainingSet);
		mainRequirementSpecificationsPipeline.setDocumentsTestSet(documentsTestSet);
		mainRequirementSpecificationsPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
		// We finally run the TbD analysis
		mainRequirementSpecificationsPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass );
		// END of 2. PART OF TbD Analysis
		System.out.println("\n END of PART 2. - TbD Analysis \n\n ");
		// PART 3. - ML prediction 
		System.out.println("PART 3. - ML prediction ");
		String pathTrainingSet = docs_location+"documents-preprocessed-req_specification/tdm_full_trainingSet_with_oracle_info.csv";
		//path test set
		String pathTestSet = docs_location+"documents-preprocessed-req_specification/tdm_full_testSet_with_oracle_info.csv";
		//path output model
		String pathModel = docs_location+"documents-preprocessed-req_specification/ML-method.model";
		//path output model
		String pathWholeDataset = docs_location+"documents-preprocessed-req_specification/tdm_full_with_oracle_info_F.csv";
		String machineLearningModel = "J48";
		//TODO: output location
		String pathResultsPrediction = docs_location+"resultsPrediction-req_specification.txt";
		WekaClassifier wekaClassifier = new WekaClassifier(pathTrainingSet, pathTestSet, pathModel);
		wekaClassifier.runSpecifiedMachineLearningModel("NaiveBayes",pathResultsPrediction);//default behaviour it does prediction with given training and test sets with J48
		//wekaClassifier.runSpecifiedModelWith10FoldStrategy(pathWholeDataset, pathModel,machineLearningModel,pathResultsPrediction);
		System.out.println("\n END of PART 3. - ML prediction\n\n");
		/**/
	}
	
	
	// loading arguments for various pipeline steps
	public MainRequirementSpecificationsPipeline(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass) {
		
		//loading data concerning the oracle
		this.dataType = dataType;
		this.nameOfAttributeID = nameOfAttributeID;
		this.nameOfAttributeText = nameOfAttributeText;
		this.nameOfAttributeClass = nameOfAttributeClass;
		
	}
	
	public OracleRequirementSpecificationsAnalyzer runOracleAnalysis() {
		
		OracleRequirementSpecificationsAnalyzer oracleRequirementSpecificationsAnalyzer = new OracleRequirementSpecificationsAnalyzer(this.dataType,  this.nameOfAttributeID,  this.nameOfAttributeText,  this.nameOfAttributeClass, this.pathRScript,  this.baseFolder,  this.oracle_path, this.threshold);
		
		return oracleRequirementSpecificationsAnalyzer;
	}

	
	public void runTbDAnalysis(String nameOfAttributeID, String nameOfAttributeClass ) {
		String[] tbdArgs = new String [9];
		tbdArgs[0] = this.pathTbDRScript;  
		tbdArgs[1] = this.docs_location;  
		tbdArgs[2] = this.documentsTrainingSet;  
		tbdArgs[3] = this.documentsTestSet; //we pass this as String argument, it will be converted later
		tbdArgs[4] = this.simplifiedOracle_path; 
		tbdArgs[5] = this.nameOfAttributeID; 
		tbdArgs[6] = this.nameOfAttributeClass; 
		tbdArgs[7] = this.nameOfAttributeText; 
		tbdArgs[8] = this.baseFolder; 
		TermByDocumentCreation.main(tbdArgs);
		
	}
	

	public String getDocs_location() {
		return docs_location;
	}

	public void setDocs_location(String docs_location) {
		this.docs_location = docs_location;
	}

	public String getPathRScript() {
		return pathRScript;
	}

	public void setPathRScript(String pathRScript) {
		this.pathRScript = pathRScript;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	public String getOracle_path() {
		return oracle_path;
	}

	public void setOracle_path(String oracle_path) {
		this.oracle_path = oracle_path;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public String getPathTbDRScript() {
		return pathTbDRScript;
	}

	public void setPathTbDRScript(String pathTbDRScript) {
		this.pathTbDRScript = pathTbDRScript;
	}

	public String getDocumentsTrainingSet() {
		return documentsTrainingSet;
	}

	public void setDocumentsTrainingSet(String documentsTrainingSet) {
		this.documentsTrainingSet = documentsTrainingSet;
	}

	public String getDocumentsTestSet() {
		return documentsTestSet;
	}

	public void setDocumentsTestSet(String documentsTestSet) {
		this.documentsTestSet = documentsTestSet;
	}

	public String getSimplifiedOracle_path() {
		return simplifiedOracle_path;
	}

	public void setSimplifiedOracle_path(String simplifiedOracle_path) {
		this.simplifiedOracle_path = simplifiedOracle_path;
	}
	
	

}
