/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author sorantes
 */
public class SyntacticAnalyzer {
        
    public String exportVocabulary(ArrayList<Word> Vocabularylst, ArrayList<String> Tagslst, File file){
        HashMap<String,String> BlocksbyTag = new HashMap<>();
        for(String tag:Tagslst)
            BlocksbyTag.put(tag,"");
        for (Word word: Vocabularylst) {
            for (LabelPercentage percentage : word.Percentages) {
                String tempstr = BlocksbyTag.get(percentage.LabelName);
                tempstr +=word.WordName + " ";
                BlocksbyTag.put(percentage.LabelName, tempstr);
            }
        }
        ArrayList<String> lines = new ArrayList<>();
        for(String tag:BlocksbyTag.keySet()){
            lines.add(BlocksbyTag.get(tag) + "|" + tag);
        }
        return createFile(lines, file.getPath());
    }
    
    public String createFile(ArrayList<String> lines, String path) {
        try {
            Path file = (path.contains(".txt")) ? Paths.get(path) : Paths.get(path + ".txt");
            Files.write(file, lines, Charset.forName("UTF-8"));
            return "[+] File exported " + file;
        } catch (IOException ex) {
            Logger.getLogger(SyntacticAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            return "[-] Error :(";
        }
    }
}