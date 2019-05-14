/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 *
 * @author sorantes
 */
public class BagOfWords {
    private final SyntacticAnalyzer Parser = new SyntacticAnalyzer();
    private final HashMap<String, Word> Words = new HashMap<>(); 
    public final HashMap<String, Integer> TagsCount= new HashMap<>(); //tags count <tag>,<count> 
    public Calulation Caltulate;
    //double Weigh =Caltulate.weightTermFrequency(tempWord.Count, inputWordslst.size() , TagsCount.size(),TagsCount.get(tempTag));                                                                                                                                 
    public String estimateProbabilityTFIDF(String inputLine){
        
        inputLine = inputLine.toLowerCase().trim();
        if(TagsCount.size()<1)
            return "[!] It's necessary to train it first.";
        else if(inputLine.equals(""))
            return "[!] Empty input."; 
        Caltulate = new Calulation(Words.size(), 0, TagsCount.size());//totalWordscount,  smother,  totalTagsCount
        ArrayList<String> inputWordslst = new ArrayList<>(Arrays.asList(inputLine.split(" ")));
        HashMap<String, Double> TagsProduct = new HashMap<>(); // product of tags
        
        double tempProduct =0, percentage =0;
        for(String tagName:TagsCount.keySet()){
            TagsProduct.put(tagName, 0.0);
        }
        for(String inputWord:inputWordslst){
            Word tempWord = Words.get(inputWord);
            if(tempWord==null){//new word in the universe
                
            }
            else{
                    /*term t in a document d
                    Weight Wt,d of term t document d is given by:

                      TFt,d is the number of occurrences of t in document d.
                      DFt is the number of documents containing the term t.
                      N is the total number of documents in the corpus.
                  */
                  for(LabelPercentage lPercentage: tempWord.Percentages){ //wordCount, numberWords, number of documents in collection, number of documents containing word
                    tempProduct = Caltulate.weightTermFrequency(tempWord.Count, Words.size(), TagsCount.size(),tempWord.Percentages.size());
                    if(TagsProduct.get(lPercentage.LabelName)>0)
                        tempProduct = tempProduct * TagsProduct.get(lPercentage.LabelName);
                    TagsProduct.put(lPercentage.LabelName, tempProduct);
                }
            }
        }
        String MostLikelyTag = "";
        double probResult = 0;
        for(String tag:TagsProduct.keySet()){
            if(TagsProduct.get(tag) == probResult)
                MostLikelyTag +=  " equiprobable " + tag;
            else if(TagsProduct.get(tag)>probResult){
                probResult = TagsProduct.get(tag);
                MostLikelyTag = tag;
            }
        }
        return String.format("(TF*IDF)\"%s\" has %s, is  probable to be %s.", inputLine,probResult,MostLikelyTag);
    }
    /**Calculate with laplace smoothing*/
    public String estimateProbabilityLaplace(String inputLine){
        inputLine = inputLine.toLowerCase().trim();
        if(TagsCount.size()<1)
            return "[!] It's necessary to train it first.";
        else if(inputLine.equals(""))
            return "[!] Empty input."; 
        
        Caltulate = new Calulation(Words.size(), 0, TagsCount.size());//totalWordscount,  smother,  totalTagsCount
        ArrayList<String> inputWordslst = new ArrayList<>(Arrays.asList(inputLine.split(" ")));
        HashMap<String, Double> TagsProduct = new HashMap<>(); // product of tags
        double tempProduct =0, percentage =0, probResult = 0;
        String MostLikelyTag = "";
        for(String tagName:TagsCount.keySet()){
            percentage = (double)TagsCount.get(tagName)/getTagsTotalCount();
            TagsProduct.put(tagName, percentage);
        }
        for(String inputWord:inputWordslst){
            Word tempWord = Words.get(inputWord);
            if(tempWord==null){//new word in the universe
                for(String tag:TagsProduct.keySet()){
                    tempProduct = Caltulate.laplaceSmothing(0, TagsCount.get(tag));
                    tempProduct = tempProduct * TagsProduct.get(tag);
                    TagsProduct.put(tag, tempProduct);
                } 
            }
            else
                for(String tag: TagsCount.keySet()){
                    tempProduct = Caltulate.laplaceSmothing(tempWord.getTagWordOcurrences(tag), TagsCount.get(tag));
                    tempProduct = tempProduct * TagsProduct.get(tag);
                    TagsProduct.put(tag, tempProduct);
                }
        }
        for(String tag:TagsProduct.keySet()){
            if(TagsProduct.get(tag) == probResult)
                MostLikelyTag +=  " equiprobable " + tag;
            else if(TagsProduct.get(tag)>probResult){
                probResult = TagsProduct.get(tag);
                MostLikelyTag = tag;
            }
        }
        return String.format("(LaplaceSmothing) \"%s\" has %s,  is probably to be %s. ", inputLine,  probResult , MostLikelyTag);
    }
    /**Calculate probabilities*/
    public String estimateProbability(String inputLine){
        inputLine = inputLine.toLowerCase().trim();
        if(TagsCount.size()<1)
            return "[!] It's necessary to train it first.";
        else if(inputLine.equals(""))
            return "[!] Empty input."; 
        ArrayList<String> inputWordslst = new ArrayList<>(Arrays.asList(inputLine.split(" ")));
        HashMap<String, Double> TagsProbability = new HashMap<>(); // Sum of probability by tag
        double newWordProbability = 1.0/TagsCount.size(), denominator =0,MostLikelyProb = 0;
        String MostLikelyTag = "";
        for(String inputWord:inputWordslst){
            Word tempWord = Words.get(inputWord);
            if(tempWord==null){//new word in the universe
                for(String tag:TagsCount.keySet()){
                    Double tempProb = TagsProbability.get(tag);
                    if(tempProb==null)
                        TagsProbability.put(tag, newWordProbability);
                    else{
                        tempProb += newWordProbability;
                        TagsProbability.put(tag, tempProb);
                    }
                }
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
        return String.format("[+] \"%s\" has %.3f%%  probability to be %s.", inputLine,probResult,MostLikelyTag);
    }
    /**Add new phrase to the dictionary*/
    public String addPhrase(String newLineStr, boolean DeleteSpecialCharactes, boolean DeleteNumbers){
        newLineStr = newLineStr.toLowerCase().trim();
        if (!newLineStr.equals("")){
            if (!newLineStr.contains("|")){
                //report wrong format
                System.out.println("[!] Error, the input didn't have the right format.");
                return "[!] Error, the input didn't have the right format. <word> | <tag>";
            }
            else {
                String left, tag;
                int pipePos = newLineStr.indexOf('|');
                left = newLineStr.substring(0, pipePos).trim();
                tag = newLineStr.substring(pipePos + 1, newLineStr.length()).trim();
                if(DeleteSpecialCharactes)
                    left = left.replaceAll("[^a-zA-Z0-9]+"," ").trim();
                if(DeleteNumbers)
                    left = left.replaceAll("[0-9]+"," ").trim();
                if (left.isEmpty() | tag.isEmpty()){
                    //report wrong format
                    System.out.println("[!] Error, the input didn't have the right format.");
                    return "[!] Error, the input didn't have the right format. <word> | <tag>";
                }
                else{
                    List<String> tempWords = Arrays.asList(left.split(" ")) ;
                    for(String tempWord:tempWords){
                        checkNewWord(tempWord, tag);
                        checkTag(tag);
                    }
                }   
                updateStats();
                return String.format("[+] \"%s\" successfully loaded. ", newLineStr);
            }
        }
        return "[!] Empty input.";
    }
    /**Parser*/
    public String setNewFile(File file, boolean InvertFormat, boolean DeleteSpecialCharactes, boolean DeleteNumbers) throws IOException{
        List<String> lines = Files.lines(Paths.get(file.getPath())) //split input per line
               .map(word -> word.toLowerCase().trim())
               .collect(Collectors.toList());
        try {
            for (String line:lines) {
                String left, tag;
                int pipePosition = line.indexOf('|');
                if(pipePosition>0){
                    if(InvertFormat){
                        left = line.substring(pipePosition + 1, line.length()).trim();  //rigth
                        tag = line.substring(0, pipePosition).trim();                   //left
                    }
                    else{
                        left = line.substring(0, pipePosition).trim();                //left
                        tag = line.substring(pipePosition + 1, line.length()).trim(); //right
                    }
                    if (left.isEmpty() | tag.isEmpty()){
                        //Report Wrong file
                        System.out.println("[!] Empty line (Ignored)"); 
                    }
                    else{
                        if(DeleteSpecialCharactes)
                            left = left.replaceAll("[^a-zA-Z0-9]+"," ").trim();
                        if(DeleteNumbers)
                            left = left.replaceAll("[0-9]+"," ").trim();
                        List<String> tempWords = Arrays.asList(left.split(" ")) ;
                        for(String tempWord:tempWords){
                            checkNewWord(tempWord, tag);
                            checkTag(tag);
                        }
                    }   
                }
                else 
                    System.out.println("[!] line dont have | (Ignored)");
            }
            updateStats();
            return "[+] File successfully loaded.";
        } catch (Exception e) {
            //Report Wrong file
            System.out.println("[!] Error, wrong file" + e);
            return "[-] Error, wrong file.";
        }
    }
    //Update percentages of all the words in the Dictionary
    public void updateStats(){
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
        Map<String, Object> copy = new TreeMap<>(Words);
         for (String word:copy.keySet()) {
            displaylst.add(String.format("%s has %.3f%% probability to be %s",word, Words.get(word).getGreaterProbability()*100, Words.get(word).getGreaterTagProbability()));
        }
        return displaylst;
    }
    //get a list of tags with their probability 
    public List<String> getTagPercentage(){
        double percentage =0.0;
        List<String> displaylst = new ArrayList<>();
        Map<String, Object> copy = new TreeMap<>(TagsCount);
         for (String tag:copy.keySet()) {
             percentage = ((double)TagsCount.get(tag)/getTagsTotalCount()) *100;
            displaylst.add(String.format("P(%s) = %d/%d = %.3f",tag,TagsCount.get(tag),getTagsTotalCount(),percentage));//,percentage));
        }
        return displaylst;
    }
    //add new word or increasex`x` the counter
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
    /**Export the current Vocabulary*/
    public String exportVocabulary(File file){
        ArrayList<Word> Vocabularylst = new ArrayList<>(Words.values());
        ArrayList<String> Tagslst = new ArrayList<>(TagsCount.keySet()); 
        return Parser.exportVocabulary(Vocabularylst, Tagslst, file);
    }
    /**Parse one file per tag*/
    String setNewFileTag(File file, boolean deleteChars, boolean deleteNumbers) throws IOException {
        String tag = file.getName().replaceFirst("[.][^.]+$", ""); //delete extension
        Set<String> FilesWords = Files.lines(Paths.get(file.getPath()))
            .map(word -> word.toLowerCase().trim()) 
            .map(word ->  (deleteChars) ? word.replaceAll("[^a-zA-Z0-9]+"," ").trim() : word)   
            .map(word ->  (deleteNumbers) ? word.replaceAll("[0-9]+"," ").trim() : word) 
            .flatMap(line -> Arrays.stream(line.trim().split(" ")))
            .filter(word -> !word.isEmpty())
            .collect(Collectors.toSet());
        //FilesWords.forEach(System.out::println); 
        FilesWords.forEach(word -> {
            checkNewWord(word, tag);
            checkTag(tag);
        });
        updateStats();
        return "[+] File successfully loaded. Added category:" + tag;
    }
}
