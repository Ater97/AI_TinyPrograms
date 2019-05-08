/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author sorantes
 */
public class SyntacticAnalyzer {
        
    //split input per line
    public ArrayList<String> ParseInputGetLines(File file) throws FileNotFoundException, IOException{
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {  
            String line;
            while ((line = br.readLine()) != null) {
               lines.add(line);
            }
        }
        return lines;
    }
    
    
    
    
    
}
