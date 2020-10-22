from textaugment import Word2vec
from textaugment import Wordnet
from textaugment import Translate

import os
import nltk
import gensim


###INITIAL SETUP###
nltk.download('wordnet')
nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
dataContainer = {}
directory = './'
model = gensim.models.KeyedVectors.load_word2vec_format('./GoogleNews-vectors-negative300.bin', binary=True)
augText = ""
savePath = "./augmentedData"

if not os.path.isdir(savePath):
    os.makedirs(savePath)


##PARAMS###
#v enable/disable verbs augmentation
#n enable/disable nouns augmentation
#runs number of times to augment a sentence
#p = 0.5 probability of success of an individual trial
t = Word2vec(model=model)
t2 = Wordnet(v=True,n=True,runs=3,p=0.5)

#SPECIFY HERE WHICH AUGMENTATION PIPELINE PROCESS TO USE
augmentation = t
######


if augmentation == t:
    augText = "Word2Vec"
elif augmentation == t2:
    augText = "WordNet"


for filename in os.listdir(directory):
    if filename.endswith('.txt'):
        datapath = os.path.join(directory,filename)
        dataSet = open(datapath, 'r')
        newFile = open(os.path.join(savePath, filename[0:-4] + "_aug_" + augText + ".txt"),"w+", encoding="utf-8")

        for line in dataSet:
            newFile.write(augmentation.augment((line)) + "\n")
        newFile.close()
        continue
    else:
        continue


