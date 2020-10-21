package org.zhaw.ch.configFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ConfigFileReader {
	
	// PART 1. - ORACLE PARAMETERS
	private String docs_location;
	//local path to the R script "MainScript.r"
	 private String pathRScript ;
	 private String  pathRScriptOracle;
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
	 
		protected static  String dataType;
		
		protected static String nameOfAttributeID;
		
		protected static String nameOfAttributeText;
		
		protected static String nameOfAttributeClass;
		
		protected static String machineLearningModelName;

		private static String pathTrainingSet;
		
		private static String pathTestSet;
		
		private static String pathModel;
		
		private static String pathWholeDataset;
		
		private static String machineLearningModel;
		
		private static String strategy;
		
		private static String pathResultsPrediction;
		
		public ConfigFileReader(String pathXMLConfigFile, String mode) {
			
			System.out.println("We are loading the config File...");
			Document doc = null;
			try {

				File fXmlFile = new File(pathXMLConfigFile);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);

				
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();

				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("ADSORB");

				System.out.println("----------------------------");
				if (doc!=null) {
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						// ORACLE_AND_TbD_ANALYSIS
						Element eElement = (Element) nNode;
						String id = eElement.getAttribute("id");
						System.out.println("ADSORB-ML id : " + id);
						if(mode.equals("ORACLE_AND_TbD_ANALYSIS" ))
						{
						this.docs_location = eElement.getElementsByTagName("docs_location").item(0).getTextContent();
						System.out.println("docs_location : " + this.docs_location);
						this.pathRScriptOracle = eElement.getElementsByTagName("pathRScriptOracle").item(0).getTextContent();
						System.out.println("pathRScriptOracle : " + this.pathRScriptOracle);
						this.baseFolder = eElement.getElementsByTagName("baseFolder").item(0).getTextContent();
						System.out.println("baseFolder: " + this.baseFolder);
						this.oracle_path = eElement.getElementsByTagName("oracle_path").item(0).getTextContent();
						System.out.println("oracle_path: " + this.oracle_path);
						this.dataType = eElement.getElementsByTagName("dataType").item(0).getTextContent();
						System.out.println("dataType: " + this.dataType);
						this.nameOfAttributeID = eElement.getElementsByTagName("nameOfAttributeID").item(0).getTextContent();
						System.out.println("nameOfAttributeID: " + this.nameOfAttributeID);						
						this.nameOfAttributeText = eElement.getElementsByTagName("nameOfAttributeText").item(0).getTextContent();
						System.out.println("nameOfAttributeText: " + this.nameOfAttributeText);	
						this.nameOfAttributeClass = eElement.getElementsByTagName("nameOfAttributeClass").item(0).getTextContent();
						System.out.println("nameOfAttributeClass: " + this.nameOfAttributeClass);	
						this.pathTbDRScript = eElement.getElementsByTagName("pathTbDRScript").item(0).getTextContent();
						System.out.println("pathTbDRScript: " + this.pathTbDRScript);	
						this.documentsTrainingSet = eElement.getElementsByTagName("documentsTrainingSet").item(0).getTextContent();
						System.out.println("documentsTrainingSet: " + this.documentsTrainingSet);	
						this.documentsTestSet = eElement.getElementsByTagName("documentsTestSet").item(0).getTextContent();
						System.out.println("documentsTestSet: " + this.documentsTestSet);	
						this.simplifiedOracle_path = eElement.getElementsByTagName("simplifiedOracle_path").item(0).getTextContent();
						System.out.println("simplifiedOracle_path: " + this.simplifiedOracle_path);	
						}
						if(mode.equals("ML_ANALYSIS"))
						{
						// ML_ANALYSIS
						this.strategy = eElement.getElementsByTagName("strategy").item(0).getTextContent();
						System.out.println("strategy: " + this.strategy);	
						this.pathModel = eElement.getElementsByTagName("pathModel").item(0).getTextContent();
						System.out.println("pathModel: " + this.pathModel);	
						this.machineLearningModel = eElement.getElementsByTagName("machineLearningModel").item(0).getTextContent();
						System.out.println("machineLearningModel: " + this.machineLearningModel);
						this.pathResultsPrediction = eElement.getElementsByTagName("pathResultsPrediction").item(0).getTextContent();
						System.out.println("pathResultsPrediction: " + this.pathResultsPrediction);
							if(strategy.equals("Training_and_test_set"))
							{
								this.pathTrainingSet = eElement.getElementsByTagName("pathTrainingSet").item(0).getTextContent();
								System.out.println(": " + this.pathTrainingSet);	
								this.pathTestSet = eElement.getElementsByTagName("pathTestSet").item(0).getTextContent();
								System.out.println("pathTestSet: " + this.pathTestSet);	
							}
							if(strategy.equals("10-fold"))
							{
								this.pathWholeDataset = eElement.getElementsByTagName("pathWholeDataset").item(0).getTextContent();
								System.out.println("pathWholeDataset: " + this.pathWholeDataset);		
							}
						//System.out.println(": " + this.);	
						}
					}
				  }
				 }
			    } catch (Exception e) {
				//e.printStackTrace();
			        System.out.println("ERROR: Something went wrong.");
			        if (doc==null)
			        {
			        	System.out.println(" Error message: No such file or directory -> "+ pathXMLConfigFile);
			        }
			    }
		} 
		

		public static String getPathResultsPrediction() {
			return pathResultsPrediction;
		}




		public static void setResultsPrediction(String resultsPrediction) {
			ConfigFileReader.pathResultsPrediction = resultsPrediction;
		}




		public static String getStrategy() {
			return strategy;
		}




		public static void setStrategy(String strategy) {
			ConfigFileReader.strategy = strategy;
		}




		public static String getMachineLearningModel() {
			return machineLearningModel;
		}




		public static void setMachineLearningModel(String machineLearningModel) {
			ConfigFileReader.machineLearningModel = machineLearningModel;
		}




		public static String getPathWholeDataset() {
			return pathWholeDataset;
		}




		public static void setPathWholeDataset(String pathWholeDataset) {
			ConfigFileReader.pathWholeDataset = pathWholeDataset;
		}




		public static String getMachineLearningModelName() {
			return machineLearningModelName;
		}




		public static void setMachineLearningModelName(String machineLearningModelName) {
			ConfigFileReader.machineLearningModelName = machineLearningModelName;
		}




		public static String getPathTrainingSet() {
			return pathTrainingSet;
		}




		public static void setPathTrainingSet(String pathTrainingSet) {
			ConfigFileReader.pathTrainingSet = pathTrainingSet;
		}




		public static String getPathTestSet() {
			return pathTestSet;
		}




		public static void setPathTestSet(String pathTestSet) {
			ConfigFileReader.pathTestSet = pathTestSet;
		}




		public static String getPathModel() {
			return pathModel;
		}




		public static void setPathModel(String pathModel) {
			ConfigFileReader.pathModel = pathModel;
		}




	public static String getDataType() {
			return dataType;
		}




		public static void setDataType(String dataType) {
			ConfigFileReader.dataType = dataType;
		}




		public static String getNameOfAttributeID() {
			return nameOfAttributeID;
		}




		public static void setNameOfAttributeID(String nameOfAttributeID) {
			ConfigFileReader.nameOfAttributeID = nameOfAttributeID;
		}




		public static String getNameOfAttributeText() {
			return nameOfAttributeText;
		}




		public static void setNameOfAttributeText(String nameOfAttributeText) {
			ConfigFileReader.nameOfAttributeText = nameOfAttributeText;
		}




		public static String getNameOfAttributeClass() {
			return nameOfAttributeClass;
		}




		public static void setNameOfAttributeClass(String nameOfAttributeClass) {
			ConfigFileReader.nameOfAttributeClass = nameOfAttributeClass;
		}




	public String getPathRScriptOracle() {
			return pathRScriptOracle;
		}




		public void setPathRScriptOracle(String pathRScriptOracle) {
			this.pathRScriptOracle = pathRScriptOracle;
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
