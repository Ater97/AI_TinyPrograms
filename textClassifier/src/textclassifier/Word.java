/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sorantes
 */
public class Word {
    public final String WordName;
    public int Count;
    public List<LabelPercentage> Percentages= new ArrayList<>();  //ingles 0.5, español 0.5, francés 0.0
    
    public Word(String name, int count, String tag){
        this.WordName = name;
        this.Count = count;
        setTag(tag);
    }

    public void increseCount(){
        Count++;
    }
    //add new taf or increase the counter
    public void setTag(String tag){
        LabelPercentage tempPercentage = exists(tag);
        if(tempPercentage == null)
            Percentages.add(new LabelPercentage(tag,1,1));
        else{
            Integer index = Percentages.indexOf(tempPercentage);
            tempPercentage.increaseOccurrences();
            Percentages.set(index, tempPercentage);
        }    
    }
    //Check if a LabelPercentage exists 
    public LabelPercentage exists(String tag){
        for(LabelPercentage percentage: Percentages){
            if(percentage.LabelName.equals(tag))
                return percentage;
        }
        return null;
    }
    //highest probability 
    public double getGreaterProbability(){
        double prob = 0;
        for (LabelPercentage Percentage : Percentages) {
            if(Percentage.TagPercentage > prob)
                prob = Percentage.TagPercentage;
        }
        return prob;
    }
    //get highest probability tag for display
    public String getGreaterTagProbability(){
        double prob = 0;
        String tag ="";
        for (LabelPercentage Percentage : Percentages) {
            if(Percentage.TagPercentage == prob)
                tag += " equiprobable " + Percentage.LabelName;
            else if(Percentage.TagPercentage > prob){
                prob = Percentage.TagPercentage;
                tag = Percentage.LabelName;}
        }
        return tag;
    }
    //Update all the percentages of the word (Calculate Term Frequency!)
    public void updateStats(double tagsCount){
        for(LabelPercentage tempPercentage:Percentages){
            Integer index = Percentages.indexOf(tempPercentage);
            
            tempPercentage.TagPercentage = (tempPercentage.Occurrences/tagsCount); //Term Frequency on the universe
            tempPercentage.TagPercentage = (tempPercentage.Occurrences/getTagsTotalCount()); //Term Frequency on the tags of the word
            Percentages.set(index, tempPercentage);
        }
    }
    public double getTagsTotalCount(){
        double count =0;
        for(LabelPercentage tempPercentage:Percentages){
            double i = tempPercentage.Occurrences;
            count += i;
        }
        return count;
    }
}
class LabelPercentage{
    public final String LabelName;
    public double TagPercentage; //Actually Term Frequency
    public double Occurrences;
    public LabelPercentage(String name, int count, double percentage){
        this.LabelName = name;
        this.Occurrences = count;
        this.TagPercentage = percentage;
    }
    
    public void setPercentage(double percentage){
        this.TagPercentage = percentage;
    }
    public void increaseOccurrences(){
        Occurrences++;
    }
}