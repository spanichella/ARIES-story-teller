package org.zhaw.ch.manager.pipeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.zhaw.ch.R_Tm_package.TermByDocumentCreation;
import org.zhaw.ch.configFile.ConfigFileReader;
import org.zhaw.ch.ml.WekaClassifier;
import org.zhaw.ch.oracle.OracleRequirementSpecificationsAnalyzer;
import org.zhaw.ch.oracle.OracleUserReviewsAnalyzer;

/**
 * 
 * @author panc
 *
 * 	This class extends the Main Program class by supporting the execution of  
 * 	the main steps of the ML- and DL-based classification pipelines
 *  concerning User reviews (extracted from App stores).
 * 	
 */
public class MainPipeline extends MainProgram {

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
			
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {		
		// PART 0. - we load the CONFIG FILE
		// possible values "ORACLE_AND_TbD_ANALYSIS", "ML_ANALYSIS"
		//String mode = "ORACLE_AND_TbD_ANALYSIS"; 
		//String pathXMLConfigFile = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/Resources/Reviews-Dataset/ORACLE_AND_TbD_ANALYSIS-REVIEWS.xml";
		//String pathXMLConfigFile = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/ORACLE_AND_TbD_ANALYSIS-REQ-SPECIFICATIONS.xml";
		
		//String mode = "ML_ANALYSIS"; 
		String mode = args[0]; 
		
        System.out.println("ERROR: Something went wrong.");
        if (!mode.equals("ORACLE_AND_TbD_ANALYSIS") && !mode.equals("ML_ANALYSIS") )
        {
        	System.out.println(" Error message concerning the Option \"mode\" (it must to be \"ML_ANALYSIS\" or \"ORACLE_AND_TbD_ANALYSIS\") -> "+mode );
        }
		
		//String pathXMLConfigFile = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/ML_ANALYSIS-REVIEWS.xml";
		//String pathXMLConfigFile = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/ML_ANALYSIS-REQ-SPECIFICATIONS.xml";
		
        //String pathXMLConfigFile = "/Users/panc/Downloads/Requirement-Collector-ML-Component/Requirement-Collector-ML-Component/Resources/Reviews-Dataset/ML_ANALYSIS-REVIEWS-demo.xml";
		String pathXMLConfigFile = args[1];
		
		ConfigFileReader configFileReader = null;
		
		if(mode.equals("ORACLE_AND_TbD_ANALYSIS" ))
		{
			configFileReader = new ConfigFileReader(pathXMLConfigFile, mode);
			// here are located the "documents" folder and the  "utilities.R script"
			//String docs_location = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/";
			String docs_location = configFileReader.getDocs_location();
			
			//local path to the R script "MainScript.r"
				//String pathRScriptOracle = docs_location+"Script-to-create-test-dataset.r";
			String pathRScriptOracle = configFileReader.getPathRScriptOracle();
			// here are located the "documents" folder and the  "utilities.R script" 
				//String baseFolder = docs_location+"documents2";
				String baseFolder = configFileReader.getBaseFolder();
			// path oracle 
				//String oracle_path = docs_location+"documents2/truth_set_ICSME2015.csv";
				String oracle_path = configFileReader.getOracle_path();
			// path threshold 
				double threshold = 0.5;
					
			// Type of the data to classify
			//String dataType = "User Reviews";
				String dataType = configFileReader.getDataType();
			// Name of the column "ID" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
			//String nameOfAttributeID = "id"; 
			String nameOfAttributeID =  configFileReader.getNameOfAttributeID(); 
			
			// Name of the column "review" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
			String nameOfAttributeText = configFileReader.getNameOfAttributeText(); 
			// Name of the column "class" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
			String nameOfAttributeClass = configFileReader.getNameOfAttributeClass(); 
		// PART 1. - ORACLE  - SOME PARAMETERS ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)

		if(dataType!=null)
		{
		System.out.println("PART 1. - ORACLE Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");
			
		//we update parameters - used later to run oracle analysis
		MainPipeline mainPipeline= null;
		MainRequirementSpecificationsPipeline mainRequirementSpecificationsPipeline =  null;
		if(dataType.equals("User Reviews"))
		{
			mainPipeline = new MainPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);      
			mainPipeline.setPathRScript(pathRScriptOracle); 
			mainPipeline.setBaseFolder(baseFolder);
			mainPipeline.setOracle_path(oracle_path);
			mainPipeline.setThreshold(threshold);
		}
		if(dataType.equals("Requirement Specifications"))
		{
			mainRequirementSpecificationsPipeline= new MainRequirementSpecificationsPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);      
			mainRequirementSpecificationsPipeline.setPathRScript(pathRScriptOracle);
			mainRequirementSpecificationsPipeline.setBaseFolder(baseFolder);
			mainRequirementSpecificationsPipeline.setOracle_path(oracle_path);
			mainRequirementSpecificationsPipeline.setThreshold(threshold);
		}		
		
		// We finally run the ORACLE analysis
		if(dataType.equals("User Reviews"))
		{
		OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = mainPipeline.runOracleAnalysis();
		}
		if(dataType.equals("Requirement Specifications"))
		{
			OracleRequirementSpecificationsAnalyzer oracleRequirementSpecificationsAnalyzer = mainRequirementSpecificationsPipeline.runOracleAnalysis();
		}
		// END of 1. PART OF ORACLE 
		System.out.println("\n END of PART 1. - ORACLE Analysis\n\n ");
		
	    // PART 2. - TbD Analysis - SOME OF THEM ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)
		System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");
		//local path to the R script "MainScript.r"
		//String pathTbDRScript = docs_location+"MainScript.r";
		String pathTbDRScript = configFileReader.getPathTbDRScript();
		
		// locations of training and test sets
		//String documentsTrainingSet = docs_location+"documents2/training-set";
		String documentsTrainingSet = configFileReader.getDocumentsTrainingSet();
		//String documentsTestSet = docs_location+"documents2/test-set";
		String documentsTestSet = configFileReader.getDocumentsTestSet();
		// path oracle
		//String simplifiedOracle_path = docs_location+"documents2/truth_set-simplified.csv";
		String simplifiedOracle_path = configFileReader.getSimplifiedOracle_path();
		
		//we update parameters - used later to run TbD analysis
		if(dataType.equals("User Reviews"))
		{
		mainPipeline.setDocs_location(docs_location);
		mainPipeline.setPathTbDRScript(pathTbDRScript);
		mainPipeline.setDocumentsTrainingSet(documentsTrainingSet);
		mainPipeline.setDocumentsTestSet(documentsTestSet);
		mainPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
		// We finally run the TbD analysis
		mainPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass );
		}
		if(dataType.equals("Requirement Specifications"))
		{
			mainRequirementSpecificationsPipeline.setDocs_location(docs_location);
			mainRequirementSpecificationsPipeline.setPathTbDRScript(pathTbDRScript);
			mainRequirementSpecificationsPipeline.setDocumentsTrainingSet(documentsTrainingSet);
			mainRequirementSpecificationsPipeline.setDocumentsTestSet(documentsTestSet);
			mainRequirementSpecificationsPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
			// We finally run the TbD analysis
			mainRequirementSpecificationsPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass );			
		}
		// END of 2. PART OF TbD Analysis
		System.out.println("\n END of PART 2. - TbD Analysis \n\n ");
		 
		 }

		}
		/**/
		// PART 3. - ML prediction 
		if(mode.equals("ML_ANALYSIS")) 
		{
			configFileReader = new ConfigFileReader(pathXMLConfigFile, mode);
		//path output model
		//String pathModel = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed-review/ML-method.model";
		String pathModel = configFileReader.getPathModel();
		
		//String machineLearningModel = "SMO";
		String machineLearningModel = configFileReader.getMachineLearningModel();
		String strategy = configFileReader.getStrategy();
		String pathResultsPrediction = configFileReader.getPathResultsPrediction();
		WekaClassifier wekaClassifier = new WekaClassifier();
		if(strategy!=null)
		{	
			System.out.println("PART 3. - ML prediction ");
		if(strategy.equals("Training_and_test_set"))
		{
			//String pathTrainingSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed-review/tdm_full_trainingSet_with_oracle_info.csv";
			String pathTrainingSet = configFileReader.getPathTrainingSet();
			//path test set
			//String pathTestSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed-review/tdm_full_testSet_with_oracle_info.csv";
			String pathTestSet = configFileReader.getPathTestSet();
			wekaClassifier = new WekaClassifier(pathTrainingSet, pathTestSet, pathModel);
			if(checkWehetherTestSetIsLabeled(pathTestSet)== true)
			{
				wekaClassifier.runSpecifiedMachineLearningModel(machineLearningModel, pathResultsPrediction); //default behaviour it does prediction with given training and test sets with J48
			}
			else
			{
				wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(machineLearningModel, pathResultsPrediction); //default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
			}
		}
		if(strategy.equals("10-fold"))
		{
			//path pathWholeDataset
			//String pathWholeDataset = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed-review/tdm_full_with_oracle_info_information giving.csv";
			String pathWholeDataset = configFileReader.getPathWholeDataset();
			wekaClassifier.runSpecifiedModelWith10FoldStrategy(pathWholeDataset, pathModel,machineLearningModel, pathResultsPrediction);
			}
		System.out.println("\n END of PART 3. - ML prediction\n\n");
		/**/
		}
		}
		
	}
	
	
	// loading arguments for various pipeline steps
	public MainPipeline(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass) {
		
		//loading data concerning the oracle
		this.dataType = dataType;
		this.nameOfAttributeID = nameOfAttributeID;
		this.nameOfAttributeText = nameOfAttributeText;
		this.nameOfAttributeClass = nameOfAttributeClass;
		
	}
	
	private static boolean checkWehetherTestSetIsLabeled(String pathTestSet) {
		File file = new File(pathTestSet);
 
		try {
		    Scanner scanner = new Scanner(file);

		    //now read the file line by line...
		    int lineNum = 0;
		    String line ="";
		    while (scanner.hasNextLine()) {
		         line = scanner.nextLine();
		        lineNum++;
		        //System.out.println(line);
		        if(line.contains("\"?\"") || line.contains("'?'") || line.endsWith("?")) { 
		            System.out.println("\nCHECK DONE: the test set is non labeled, we need to label such instances" );
		            return false;
		        }
		    }
		} catch(FileNotFoundException e) { 
		    //handle this
		}
		System.out.println("\\nCHECK DONE: the test set is labeled." );
		return true;
	}
	
	public OracleUserReviewsAnalyzer runOracleAnalysis() {
		
		OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = new OracleUserReviewsAnalyzer(this.dataType,  this.nameOfAttributeID,  this.nameOfAttributeText,  this.nameOfAttributeClass, this.pathRScript,  this.baseFolder,  this.oracle_path, this.threshold);
		
		return oracleUserReviewsAnalyzer;
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
