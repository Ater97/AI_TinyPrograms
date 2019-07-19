#--Libraries
import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.externals import joblib
#--global variables
cv = CountVectorizer()

def createModel(trainigSetPath, modelPath):
    df = pd.read_csv(trainigSetPath, encoding="latin-1")
    x = df['message'].values
    y = df['caseName'].values
    x = cv.fit_transform(x) # Fit the Data
    clf = MultinomialNB() #Multinomial Naive Bayes Classifier
    clf.fit(x, y)
    
    # alpha -> Additive Laplace smoothing parameter
    # fit_prior ->Whether to learn class prior probabilities or not. If false, a uniform prior will be used
    # class_prior->Prior probabilities of the classes. If specified the priors are not adjusted according to the data.
    MultinomialNB(alpha=1.0, fit_prior=True, class_prior=None) 
    #save model
    joblib.dump(clf,modelPath)

def loadModel(inputPath, modelPath, resultPath):
    NB_emailPrediction = open(modelPath,'rb')
    clf = joblib.load(NB_emailPrediction)
    input_df = pd.read_csv(inputPath, encoding="latin-1")
    #get input files and do a list
    messageList = list(input_df['message'].values)
    fromList =  list(input_df['from'].values)
    #use model 
    verifier_Wcount = cv.transform(messageList)
    predictions = clf.predict(verifier_Wcount)
    #generate output
    outputDict ={'from': fromList,'message':messageList,'predictions':predictions}
    outputDf = pd.DataFrame.from_dict(outputDict)
    outputDf.to_csv(path_or_buf=resultPath, sep=',',index=True)