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

    private double TotalWordscount;
    private double Smoother;
    private double TotalTagsCount;
    
    public Calulation(double totalWordscount, double smother, double totalTagsCount) {
        this.TotalWordscount = totalWordscount;
        this.Smoother = smother;
        this.TotalTagsCount = totalTagsCount;
    }
    
    public double LaplaceSmothing(double WordTagCount, double TagCount){
        double numerator = WordTagCount + 1;
        double denominator = TagCount + TotalWordscount + Smoother;
        return (numerator / denominator);
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
