package org.zhaw.ch.oracle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 
 * @author panc
 *
 */
public class OracleUserReviewsAnalyzer extends Oracle{

	public OracleUserReviewsAnalyzer(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass,String pathRScriptOracle, String baseFolder, String oracle_path, double threshold) {
		super(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
		// TODO Auto-generated constructor stub
		String[] oracleArgs = new String [4];
		oracleArgs[0] = pathRScriptOracle;  
		oracleArgs[1] = baseFolder;  
		oracleArgs[2] = oracle_path;  
		oracleArgs[3] = ""+threshold; //we pass this as String argument, it will be converted later
		this.main(oracleArgs);
	}

	public static void main(String[] args) {
		
		//local path to the R script "MainScript.r"
		//String pathRScript = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2/Script-to-create-test-dataset.r";
		String pathRScriptOracle = ""; 
		// here are located the "documents" folder and the  "utilities.R script"
		//String baseFolder = "~/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2";
		String baseFolder = "";
		// path oracle 
		//String oracle_path = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents2/truth_set_ICSME2015.csv";
		String oracle_path = "";
		// path threshold 
		//double threshold = 0.5;
		double threshold = -1;
		// if  args is not null, we use it for setting the various arguments
		if(args != null && args.length==4)
		{
			//System.out.println("\n\nEvaluation of OracleArgs... \n");
			
			pathRScriptOracle = args[0];
			baseFolder = args[1];
			oracle_path = args[2];
			//System.out.println("- Double.parseDouble(args[0])"+ Double.parseDouble(args[3]));
			threshold =  Double.parseDouble(args[3]);
		
		//command to execute
		//String command = "/usr/local/bin/Rscript "+ pathRScriptOracle+" "+baseFolder+ " "+oracle_path+ " "+threshold+ " "+getNameOfAttributeID()+ " "+getNameOfAttributeText()+ " "+getNameOfAttributeClass();
		String command = "Rscript "+ pathRScriptOracle+" "+baseFolder+ " "+oracle_path+ " "+threshold+ " "+getNameOfAttributeID()+ " "+getNameOfAttributeText()+ " "+getNameOfAttributeClass();

			//we print the command to execute
				/*System.out.println(" \n \n Executing command (considering R script and arguments): \n "+command+" \n");
				System.out.println("R script and arguments: ");
				System.out.println("- pathRScriptOracle <- args[1] ");
				System.out.println("- base_folder2 <- args[2] ");
				System.out.println("- oracle_path2 <- args[3]  ");
				System.out.println("- threshold2 <- args[4]  ");
				System.out.println("- nameOfAttributeID2 <- args[5]  ");
				System.out.println("- nameOfAttributeText2 <- args[6]  ");
				System.out.println("- nameOfAttributeClass2 <- args[7]  ");
				
				System.out.println("Rscript running  \n");*/
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
		System.out.println("Error - oracleArgs == null or not sufficient \n");
	 }
	}
}
