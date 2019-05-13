/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

/**
 *
 * @author Sebastian
 */
public class Calulation {

    private final double TotalWordscount;
    private final double Smoother;
    private final double TotalTagsCount;
    
    public Calulation(double totalWordscount, double smother, double totalTagsCount) {
        this.TotalWordscount = totalWordscount;
        this.Smoother = smother;
        this.TotalTagsCount = totalTagsCount;
    }
    //Laplace Smothing
    public double laplaceSmothing(double WordTagCount, double TagCount){
        double numerator = WordTagCount + 1;
        double denominator = TagCount + TotalWordscount + Smoother;
        return (numerator / denominator);
    }
    
    //Term Frequency  Inverse Document Frequency
    /*term t in a document d
      Weight Wt,d of term t document d is given by:
    
        TFt,d is the number of occurrences of t in document d.
        DFt is the number of documents containing the term t.
        N is the total number of documents in the corpus.
    
        IDFi = log (number of documents in collection /  number of documents containing word i)
    */
    public double weightTermFrequency(double wordCount, double numberWords, double N, double DFt ){
        double TFtd = wordCount/numberWords;
        double Wtd = TFtd * Math.log(N/DFt);
        return Wtd;
    }

    public double calcProbabilityLog(double WordTagCount, double TagGlobalCount){
        double numerator = WordTagCount;
        double denominator = TagGlobalCount + TotalTagsCount * Smoother;
        return Math.log(numerator / denominator);
    }

    public double calcNegProbabilityLog(double WordTagCount, double TagGlobalCount){
        double numerator = WordTagCount;
        double denominator = TagGlobalCount + TotalTagsCount * Smoother;
        return Math.log(1 - (numerator / denominator));
    }

    public double calcPriorProbabilityLog(double TagCount){
        double numerator = TagCount;
        double denominator = TotalWordscount;
        return Math.log(numerator / denominator);
    }

}
