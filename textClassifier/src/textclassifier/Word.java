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
    public String WordName;
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
    
    public void setTag(String tag){
        LabelPercentage tempPercentage = exists(tag);
        if(tempPercentage == null){
            Percentages.add(new LabelPercentage(tag,1,1));
        }
        else{
            Integer index = Percentages.indexOf(tempPercentage);
            //LabelPercentage lp = Percentages.get(index);
            //int tagcount = Percentages.size() + 1;
            tempPercentage.increaseOccurrences();
            //tempPercentage.TagPercentage = tempPercentage.Occurrences/tagcount;
            Percentages.set(index, tempPercentage);
        }    
    }
    public LabelPercentage exists(String tag){
        for(LabelPercentage percentage: Percentages){
            if(percentage.LabelName.equals(tag))
                return percentage;
        }
        return null;
    }
    public double getGreaterProbability(){
        double prob = 0;
        for (LabelPercentage Percentage : Percentages) {
            if(Percentage.TagPercentage > prob)
                prob = Percentage.TagPercentage;
        }
        return prob;
    }
    public String getGreaterTagProbability(){
        double prob = 0;
        String tag ="";
        for (LabelPercentage Percentage : Percentages) {
            if(Percentage.TagPercentage > prob){
                prob = Percentage.TagPercentage;
                tag = Percentage.LabelName;}
        }
        return tag;
    }
    
    public void updateStats(int tagsCount){
        for(LabelPercentage tempPercentage:Percentages){
        Integer index = Percentages.indexOf(tempPercentage);
        //LabelPercentage lp = Percentages.get(index);
        int tagcount = Percentages.size();
        tempPercentage.increaseOccurrences();
        tempPercentage.TagPercentage = tempPercentage.Occurrences/tagcount;
        Percentages.set(index, tempPercentage);
        }

    }
}
class LabelPercentage{
    public String LabelName;
    public double TagPercentage;
    public int Occurrences;
    public LabelPercentage(String name, int count, double percentage){
        this.LabelName = name;
        this.Occurrences = count;
        this.TagPercentage = percentage;
    }
    
    public void setLabelName(String name){
        this.LabelName = name;
    }
    public void setPercentage(double percentage){
        this.TagPercentage = percentage;
    }
    public void increaseOccurrences(){
        Occurrences++;
    }
}