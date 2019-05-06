/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesiannetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sorantes
 */
public class BayesianNetwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = OpenFile();
        ArrayList<String> list = GetData(file);
        Network BayesianNetwork = new Network(list);
        
        
    }
    public static File OpenFile()
    {
        File fileParse = null;
        JFrame parentFrame = new JFrame();
        FileNameExtensionFilter filterfrag = new FileNameExtensionFilter(".frag", "frag");
        FileNameExtensionFilter filtertxt = new FileNameExtensionFilter(".txt", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file");  
        fileChooser.addChoosableFileFilter(filterfrag);
        fileChooser.addChoosableFileFilter(filtertxt);
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileParse = fileChooser.getSelectedFile();
            System.out.println("File path: " + fileParse.getAbsolutePath());}
        else
            System.exit(0);
        return  fileParse;
    }
    public static ArrayList<String> GetData(File file) throws FileNotFoundException{
        ArrayList<String> list;
        try (Scanner s = new Scanner(file)) {
            list = new ArrayList<>();
            while (s.hasNext()){
                list.add(s.next());
            }
        }
        return list;
    }
}
