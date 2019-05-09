/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 *
 * @author sorantes
 */
public class BagOfWords {
    private final SyntacticAnalyzer Parser = new SyntacticAnalyzer();
    private final HashMap<String, Word> Words = new HashMap<>(); 
    public final HashMap<String, Integer> TagsCount= new HashMap<>(); //tags count <tag>,<count>

    //public void EditWords(){     }
    
    int truePositive = 0, falsePositive = 0, falseNegative = 0, correctClassification = 0, incorrectClassification = 0;
    /**Calculate probabilities*/
    public String EstimateProbability(String inputLine){
        ArrayList<String> inputWordslst = new ArrayList<String>(Arrays.asList(inputLine.split(",")));
        
        return " ";
    }
    /**Calculate individual word probabilities and get the highest*/
    public double CalculateWordProbability(String inputWord){
        
        
        double accuracy = correctClassification/(correctClassification + incorrectClassification + 0.0);
        double precision = truePositive/(truePositive+falsePositive+0.0);
        double recall = truePositive/(truePositive+falseNegative+0.0);
        double fscore = 2*precision*recall/(precision+recall);
        System.out.println("Accuracy="+accuracy+"\nPrecision= "+precision+" Recall="+recall+" F-Score="+fscore);
        return 0;
    }
    

    
    /**Add new phrase to the dictionary*/
    public String AddPhrase(String newLineStr){
        if (!newLineStr.equals("")){
            if (!newLineStr.contains("|")){
                //report wrong format
                System.out.println("[!] Error, the file didn't have the right format.");
                return "[!] Error, the inout didn't have the right format. <word> | <tag>";
            }
            else {
                String left, tag;
                int pipePos = newLineStr.indexOf('|');
                left = newLineStr.substring(0, pipePos).trim();
                tag = newLineStr.substring(pipePos + 1, newLineStr.length()).trim();
                if (left.isEmpty() | tag.isEmpty()){
                    //report wrong format
                    System.out.println("[!] Error, the file didn't have the right format.");
                    return "[!] Error, the inout didn't have the right format. <word> | <tag>";
                }
                else{
                    List<String> tempWords = Arrays.asList(left.split(" ")) ;
                    for(String tempWord:tempWords){
                        checkNewWord(tempWord, tag);
                        checkTag(tag);
                    }
                }   
                UpdateStats();
                return "[+] Word successfully loaded. ";
            }
        }
        return "[!] Empty input.";
    }
    /**Parser*/
    public String setNewFile(File file) throws IOException{
        List<String> lines = Parser.ParseInputGetLines(file);   
        String extension = Parser.getFileExtension(file);
        if(extension.equals(".csv"))
            try {
                for (String line:lines) {
                    String left, tag;
                    int pipePosition = line.indexOf(',');
                    left = line.substring(0, pipePosition).trim().toLowerCase();
                    tag = line.substring(pipePosition + 1, line.length()).trim().toLowerCase();
                    if (left.isEmpty() | tag.isEmpty()){
                        //Report Wrong file
                        return "[!] Error, the file didn't have the right format.";
                    }
                    else{
                        List<String> tempWords = Arrays.asList(left.split(" ")) ;
                        for(String tempWord:tempWords){
                            checkNewWord(tempWord, tag);
                            checkTag(tag);
                        }
                    }   
                }
                UpdateStats();
            } catch (Exception e) {
                //Report Wrong file
            }
        else
            try {
                for (String line:lines) {
                    String left, tag;
                    int pipePosition = line.indexOf('|');
                    left = line.substring(0, pipePosition).trim().toLowerCase();
                    tag = line.substring(pipePosition + 1, line.length()).trim().toLowerCase();
                    if (left.isEmpty() | tag.isEmpty()){
                        //Report Wrong file
                        System.out.println("[!] Error, the file didn't have the right format");
                        return "[!] Error, the file didn't have the right format.";
                    }
                    else{
                        List<String> tempWords = Arrays.asList(left.split(" ")) ;
                        for(String tempWord:tempWords){
                            checkNewWord(tempWord, tag);
                            checkTag(tag);
                        }
                    }   
                }
                UpdateStats();
                return "[+] File successfully loaded.";
            } catch (Exception e) {
                //Report Wrong file
                System.out.println("[!] Error, wrong file");
                return "[!] Error, wrong file.";
            }
        return "";//bad programming i know i know
    }
    //Update percentages of all the words in the Dictionary
    public void UpdateStats(){
        Integer count =getTagsTotalCount();
        for(String word:Words.keySet()){
            Word w = Words.get(word);
            w.updateStats(count);
            Words.put(word, w);
        }
    }
    //Get the total number of terms in the universe
    public int getTagsTotalCount(){
        Integer count =0;
        for(String tag:TagsCount.keySet()){
            Integer i = TagsCount.get(tag);
            count += i;
        }
        return count;
    }
    //get a set of the words to display on the view
    public Set<String> getVocabularyList(){
       return  Words.keySet();
    }
    //get a list of words with their highest probability tag
    public List<String> getVocabularyPercentage(){
        List<String> displaylst = new ArrayList<>();
         for (String word:Words.keySet()) {
            //displaylst.add(String.format("P(%s | %s) = %f ",word,Words.get(word).getGreaterTagProbability(),Words.get(word).getGreaterProbability()));
            displaylst.add(String.format("\"%s\" has %f probability to be %s",word,Words.get(word).getGreaterProbability(),Words.get(word).getGreaterTagProbability()));
            //displaylst.add(word + " " + Words.get(word).getGreaterProbability() +  " "+ Words.get(word).getGreaterTagProbability());
        }
        return displaylst;
    }
    //get a list of tags with their probability 
    public List<String> getTagPercentage(){
        //String.format("P(%s) = %s/%s"
        List<String> displaylst = new ArrayList<>();
         for (String tag:TagsCount.keySet()) {
            displaylst.add(String.format("P(%s) = %d/%d",tag,TagsCount.get(tag),getTagsTotalCount()));
        }
        return displaylst;
    }
    //add new word or increase the counter
    public void checkNewWord(String word,String tag){        
        Word tempWord = Words.get(word);
        if(tempWord==null)
            Words.put(word, new Word(word,1,tag));
        else{
            tempWord.increseCount();
            tempWord.setTag(tag);
            Words.put(word, tempWord);
        }
    }
    //add new taf or increase the counter
    public void checkTag(String tag){
        Integer i = TagsCount.get(tag);
        if (i == null)
            TagsCount.put(tag, 1);
        else {
            i++;
            TagsCount.put(tag, i);
        }
    }
}
