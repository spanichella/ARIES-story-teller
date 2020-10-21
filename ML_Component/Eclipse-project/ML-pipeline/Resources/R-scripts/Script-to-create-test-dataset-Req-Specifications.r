args <- commandArgs(trailingOnly = TRUE)
base_folder2 <- args[1]
oracle_path2  <- args[2]
threshold2  <- args[3]
nameOfAttributeID2  <- args[4]
nameOfAttributeText2  <- args[5]
nameOfAttributeClass2  <- args[6]

if (!require(stringr)){ install.packages("stringr") } 

#load the libraries...
library(stringr)

#path inputs
base_folder <- "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/hassebjo"

if(!is.na(base_folder2))
{
  base_folder<- base_folder2
  print("1) argument \"docs_location\" used in R by setwd() ")
}

setwd(base_folder)

oracle_path<- "truth_set_ReqSpecification.txt"

if(!is.na(oracle_path2))
{
  oracle_path<- oracle_path2
  print("2) argument \"oracle_path2\" used in R by setwd() ")
}


#path output
folder_training_set_files <- "./training-set-Req-Specifications"
folder_test_set_files <- "./test-set-Req-Specifications"
path_csv_version_oracle <- "csv_version_of_truth_set_ReqSpecificationOracle.csv"

# delete a directory -- must add recursive = TRUE
print("In case these folders are already present we delete them")
unlink(folder_training_set_files, recursive = TRUE)
unlink(folder_test_set_files, recursive = TRUE)

print("We create training and test set folders wehere file will be created")
dir.create(folder_training_set_files, showWarnings = FALSE, recursive = TRUE)
dir.create(folder_test_set_files, showWarnings = FALSE, recursive = TRUE)

oracle_trainingSet_path<- "./trainingSet_truth_set-Req-Specifications.csv"
oracle_testSet_path<- "./testSet_truth_set-Req-Specifications.csv"
simplified_oracle_path<- "./truth_set-simplified-Req-Specifications.csv"

print("We read the oracle text file")
con <- file(oracle_path, "r", blocking = FALSE)
data_set<- readLines(con) # empty
close(con)
print(paste("First two lines of the file:"))
print(data_set[1:2])

threshold<- 50/100# 50%

if(!is.na(threshold2))
{
  threshold<- as.numeric(threshold2)
  print("3) argument \"threshold2\" used in R by setwd() ")
  print(paste("arguments length", length(args)))
}

if(length(args)==6)
 {
   print("All fine with the arguments..")
   print("We generate a csv file version of the oracle")
   oracle <- list(nameOfAttributeID2=c(), nameOfAttributeText2=c(),nameOfAttributeClass2=c())
   names(oracle) <- c(nameOfAttributeID2, nameOfAttributeText2, nameOfAttributeClass2)
   #print("preliminar empty oracle")
   #print(oracle)
   i<-1 
   for(i in 1:length(data_set))
   {
     line <- data_set[i]
     lineVector<- strsplit(line, split="\t")[[1]]
     #print(lineVector)
     pos<- length(oracle[[nameOfAttributeID2]])+1
     #if the current line (or audio recording sentence) is not already present, then we add it
     #if(sum(oracle[[nameOfAttributeID2]]!=lineVector[1]) == 0 )
     {
       #print(paste("element not present",lineVector[1],lineVector[2], lineVector[3]))
        oracle[[nameOfAttributeID2]][pos] <- lineVector[1]
        oracle[[nameOfAttributeText2]][pos] <- lineVector[2]
        oracle[[nameOfAttributeClass2]][pos] <- lineVector[3]
        #print(oracle)
     }
   }
   #oracle <- unique(oracle)
   print(paste("We save the cvs version of the oracle in file \"",path_csv_version_oracle,sep=""))
   write.csv(oracle,path_csv_version_oracle,row.names = FALSE)
   
oracle<- read.csv(path_csv_version_oracle)
print("We use the threshold to split the dataset in training and test set")
cut_point<- round(length(oracle[[nameOfAttributeID2]])*threshold)
#print(length(oracle[[nameOfAttributeID2]]))
#print(cut_point)
# 
 oracle_trainingSet<- oracle[1:cut_point,]
 #print(oracle_trainingSet)
 oracle_testSet<- oracle[(cut_point+1):length(oracle[[nameOfAttributeID2]]),]

oracle_trainingSet[[nameOfAttributeID2]]<- as.character(oracle_trainingSet[[nameOfAttributeID2]])
oracle_testSet[[nameOfAttributeID2]]<- as.character(oracle_testSet[[nameOfAttributeID2]])

oracle_trainingSet[[nameOfAttributeText2]]<- as.character(oracle_trainingSet[[nameOfAttributeText2]])
oracle_testSet[[nameOfAttributeText2]]<- as.character(oracle_testSet[[nameOfAttributeText2]])

oracle_trainingSet[[nameOfAttributeClass2]]<- as.character(oracle_trainingSet[[nameOfAttributeClass2]])
oracle_testSet[[nameOfAttributeClass2]]<- as.character(oracle_testSet[[nameOfAttributeClass2]])

print("We write the generated training and test set in corresponding files")
write.csv(oracle_trainingSet,oracle_trainingSet_path,row.names = FALSE)
write.csv(oracle_testSet,oracle_testSet_path,row.names = FALSE)

print("--> we populate the folder of the training set")
#we populate the folder of the training set
i<- 1
for(i in 1:length(oracle_trainingSet[[nameOfAttributeID2]]))
  {
    path_file <- paste(folder_training_set_files,oracle_trainingSet[[nameOfAttributeID2]][i],sep="/")
    corpus <- paste(oracle_trainingSet[[nameOfAttributeText2]][i],"eheh, eheh")
    write(corpus,path_file)
   }

#we populate the folder of the test set
print("--> we populate the folder of the test set")
i<- 1
for(i in 1:length(oracle_testSet[[nameOfAttributeID2]]))
{
  path_file <- paste(folder_test_set_files,oracle_testSet[[nameOfAttributeID2]][i],sep="/")
  corpus <- paste(oracle_testSet[[nameOfAttributeText2]][i],"eheh, eheh")
  write(corpus,path_file)
}

print("--> we populate a simplified version of the oracle")
simplified_oracle <- oracle
simplified_oracle[[nameOfAttributeText2]] <- NULL
write.csv(simplified_oracle, simplified_oracle_path,row.names = FALSE)
 }
 if(length(args)!=6)
 {
   print("Error: some problems with the arguments of the oracle..")
 }




