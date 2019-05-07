/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.util.List;

/**
 *
 * @author sorantes
 */
public class Word {
    public String WordName;
    public int Count;
    public String Tag;
    public List<LabelPercentage> Percentages;  //ingles 0.5, español 0.5, francés 0.0
    
    public Word(String name, int count, String tag){
        this.WordName = name;
        this.Count = count;
        this.Tag = tag;
    }
    public void increseCount(){
        Count++;
    }
}
class LabelPercentage{
    public String LabelName;
    public double Percentage;
    public int Occurrences;
    
    public void setLabelName(String name){
        this.LabelName = name;
    }
    public void setPercentage(double percentage){
        this.Percentage = percentage;
    }
    public void setOccurrences(int count){
        this.Occurrences = count;
    }
}