/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

/**
 *
 * @author sorantes
 */
public class SyntacticAnalyzer {
    private Log log;
    private Results results;
    
    private String filePath;
    
    private boolean checkLine(String s){
        s = s.trim();
        if (!s.equals("")){
            if (!s.contains("|")){
                results.errorFound("[!] Pipe is missing (ignored)");
            }
            else {
                String left, right;
                int pipePos = s.indexOf('|');
                left = s.substring(0, pipePos).trim();
                right = s.substring(pipePos + 1, s.length()).trim();
                if (left.isEmpty() | right.isEmpty()){
                    results.errorFound("[!] " + s + " is not valid (ignored)");
                }
                else {
                    if (right.split(" ").length > 1){
                        results.errorFound("[!] " + s + " has more than 1 tag (ignored)");
                    }
                    else 
                        return true;
                }
            }
        }
        return false;
    }
    
    private void useLine(String s){
        if (checkLine(s)){
            String left, tag;
            int pipePos = s.indexOf('|');
            left = s.substring(0, pipePos).trim();
            tag = s.substring(pipePos + 1, s.length()).trim();
            String[] wordVector = left.split(" ");
            for (String word : wordVector) {
                word = removePunctuation(word);
                if (!word.isEmpty()){
                    results.correctPair(word, tag);
                    results.wordFound(word);
                }
            }
            results.tagFound(tag);
        }
    }
    
    static String removePunctuation(String s) {
        StringBuilder res = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                res.append(c);
        }
        return res.toString();
    }
}
    interface Results{
        void correctPair(String word, String tag);

        void errorFound(String message);
        
        
        void tagFound(String tag);

        void wordFound(String word);
    }

    interface Log {
        void pLog(String logMessage);

        void pError(String errorMessage);

        void readLine(String line);
    }
