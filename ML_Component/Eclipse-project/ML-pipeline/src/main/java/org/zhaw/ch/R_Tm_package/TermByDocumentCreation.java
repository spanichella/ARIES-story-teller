package org.zhaw.ch.R_Tm_package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
/**
 * 
 * @author panc
 *
 */

public class TermByDocumentCreation {
	
	

	public TermByDocumentCreation(String[] tbdArgs) {
		this.main(tbdArgs);
	}

	public static void main(String[] tbdArgs) {

		//local path to the R script "MainScript.r"
		//String pathTbDRScript = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/MainScript.r";
		String pathTbDRScript = "";
		
		// here are located the "documents" folder and the  "utilities.R script"
		//String docs_location = "~/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/";
		String docs_location = "";

		// locations of training and test sets
		//String documentsTrainingSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2/training-set";
		//String documentsTestSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2/test-set";
		String documentsTrainingSet = "";
		String documentsTestSet = "";
		
		// path oracle
		//String simplifiedOracle_path = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2/truth_set-simplified.csv";
		String simplifiedOracle_path = "";
		
		// if  args is not null, we use it for setting the various arguments
		if(tbdArgs != null && tbdArgs.length==9)
		{
			System.out.println("\n\nEvaluation of  TermByDocument Args..."+tbdArgs.length+" \n");
			
			pathTbDRScript = tbdArgs[0];
			docs_location = tbdArgs[1];
			documentsTrainingSet = tbdArgs[2];
			documentsTestSet =  tbdArgs[3];
			simplifiedOracle_path =  tbdArgs[4];
			String nameOfAttributeID =  tbdArgs[5];
			String nameOfAttributeClass =  tbdArgs[6];
			String  nameOfAttributeTexttbd = tbdArgs[7] ;
			String oracleFolder = tbdArgs[8]; 
		
		//command to execute
		String command = "/usr/local/bin/Rscript "+ pathTbDRScript+" "+docs_location+ " "+documentsTrainingSet+ " "+documentsTestSet+" "+simplifiedOracle_path+" "+nameOfAttributeID+" "+nameOfAttributeClass+ " "+nameOfAttributeTexttbd+ " "+oracleFolder;// path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
		
		//we print the command to execute
		System.out.println(" \n \n Executing command (considering R script and arguments): \n "+command+" \n");
		System.out.println("R script and arguments: ");
		System.out.println("- base_folder2 <- args[1] ");
		System.out.println("- trainingSetDirectory2 <- args[2]  ");
		System.out.println("- testSetDirectory2  <- args[3] ");
		System.out.println("- simplifiedOracle2_path  <- args[4]  \n");
		System.out.println("- nameOfAttributeID2  <- args[5]  \n");
		System.out.println("- nameOfAttributeClass2  <- args[6]  \n");
		System.out.println("- nameOfAttributeText2  <- args[7]  \n");
		System.out.println("- oracleFolder2  <- args[8]  \n");
		
		System.out.println("Rscript running  \n");
		// -- Linux/Mac osx --
		try {
		    //Process process = Runtime.getRuntime().exec("ls /Users/panc/Desktop");
			Process process = Runtime.getRuntime().exec(command);
			 
		    BufferedReader reader = new BufferedReader(
		            new InputStreamReader(process.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }	    
		 
		    reader.close();
		 
		} catch (IOException e) {
		    e.printStackTrace();
		 }
		}
	else
		{
			System.out.println("Error - TermByDocument Args == null or not sufficient \n");
		 }
	}

}
