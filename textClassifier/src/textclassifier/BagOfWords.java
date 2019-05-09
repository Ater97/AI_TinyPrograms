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
    
    double truePositive = 0, falsePositive = 0, falseNegative = 0, correctClassification = 0, incorrectClassification = 0, smoother =0;
    /**Calculate probabilities*/
    public String EstimateProbability(String inputLine){
        inputLine = inputLine.toLowerCase().trim();
        if(TagsCount.size()<1)
            return "[!] It's necessary to train it first.";
        else if(inputLine.equals(""))
            return "[!] Empty input.";
        ArrayList<String> inputWordslst = new ArrayList<>(Arrays.asList(inputLine.split(" ")));
        HashMap<String, Double> TagsProbability = new HashMap<>(); // Sum of probability by tag
        
        //Term Frequency–Inverse Document Frequency
        for(String inputWord:inputWordslst){
            Word tempWord = Words.get(inputWord);
            if(tempWord==null){//new word in the universe
            
                
                
            }
            else{ //The word exists in the universe
                for (int i = 0; i < tempWord.Percentages.size(); i++) {
                    String tempTag =tempWord.Percentages.get(i).LabelName;
                    Double probSum = TagsProbability.get(tempTag);
                    if (probSum == null)
                        TagsProbability.put(tempTag, tempWord.Percentages.get(i).TagPercentage);
                    else {
                        probSum += tempWord.Percentages.get(i).TagPercentage;
                        TagsProbability.put(tempTag, probSum);
                    }
                }
            }
        }
        double denominator =0,MostLikelyProb = 0;
        String MostLikelyTag = "";

        for(String tag:TagsProbability.keySet())
            denominator +=  TagsProbability.get(tag);
        
        for (String tag:TagsProbability.keySet()) {
            if(TagsProbability.get(tag) == MostLikelyProb)
                MostLikelyTag +=  " equiprobable " + tag;
            else if(TagsProbability.get(tag)>MostLikelyProb){
                MostLikelyTag = tag;
                MostLikelyProb = TagsProbability.get(tag);
            }
        }
        
        double probResult = (MostLikelyProb/denominator)*100;
        //double IDf = Math.log((tagsCount/tempPercentage.Occurrences));//TF-IDF
        //double TF_IDF = (tempPercentage.Occurrences/tagsCount) -IDf;
        
        double idf = Math.log((0));
        
        return String.format("\"%s\" has %.3f%%  probability to be %s.", inputLine,probResult,MostLikelyTag);
    }
    /**Add new phrase to the dictionary*/
    public String AddPhrase(String newLineStr){
        newLineStr = newLineStr.toLowerCase().trim();
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
                return String.format("[+] \"%s\" successfully loaded. ", newLineStr);
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
                    if(pipePosition>0){
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
                    }   }
                }
                UpdateStats();
            } catch (Exception e) {
                //Report Wrong file
                return "[-] Error, wrong file.";
            }
        else
            try {
                for (String line:lines) {
                    String left, tag;
                    int pipePosition = line.indexOf('|');
                    if(pipePosition>0){
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
                }
                UpdateStats();
                return "[+] File successfully loaded.";
            } catch (Exception e) {
                //Report Wrong file
                System.out.println("[!] Error, wrong file" + e);
                return "[-] Error, wrong file.";
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
            displaylst.add(String.format("\"%s\" has %.3f%% probability to be %s",word, Words.get(word).getGreaterProbability()*100, Words.get(word).getGreaterTagProbability()));
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
