/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 *
 * @author sorantes
 */
public class BagOfWords {
    private HashMap<String, Word> Words = new HashMap<>();
    private final SyntacticAnalyzer Parser = new SyntacticAnalyzer();
    private HashMap<String, Integer> TagsCount= new HashMap<>(); //tags count
    
    
    
    public void setNewFile(File file) throws IOException{
        List<String> lines = Parser.ParseInputGetLines(file);    
        try {
            for (String line:lines) {
            String left, tag;
            int pipePosition = line.indexOf('|');
            left = line.substring(0, pipePosition).trim().toLowerCase();
            tag = line.substring(pipePosition + 1, line.length()).trim().toLowerCase();
            if (left.isEmpty() | tag.isEmpty()){
                //Report Error
            }
            else{
                List<String> tempWords = Arrays.asList(left.split(" ")) ;
                for(String tempWord:tempWords){
                    checkNewWord(tempWord, tag);
                }
            }
            checkTag(tag);
        }
        } catch (Exception e) {
            //Report Wrong file
        }
        
        
    }
    public Set<String> getVocabularyList(){
       return  Words.keySet();
    }
    
    public void checkNewWord(String word,String tag){        
        Word tempWord = Words.get(word);
        if(tempWord==null)
            Words.put(word, new Word(word,1,tag));
        else{
            tempWord.increseCount();
            Words.put(word, tempWord);
        }
    }
    public void checkTag(String tag){
        Integer i = TagsCount.get(tag);
        if (i == null)
            TagsCount.put(tag, 1);
        else {
            i++;
            TagsCount.put(tag, i);
        }
    }
    
    
    public void Parser(){
        
    }
    
    public void Cold(){
        
    }
    public void AddPhrase(){
        
    }
    public void EditWords(){
        
    }
    
   
}
