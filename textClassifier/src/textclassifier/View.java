/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sorantes
 */
public class View extends javax.swing.JFrame {

    /**
     * Creates new form view
     */
    public BagOfWords bagOfWords;
    public View() {
        initComponents();
        bagOfWords = new BagOfWords();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        ChooseFile = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Console = new javax.swing.JTextArea();
        filePaht = new javax.swing.JTextField();
        inputText = new javax.swing.JTextField();
        AddPhrase = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        VocabularyList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Reset = new javax.swing.JButton();
        EstimateProbability = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ChooseFile.setText("Choose File");
        ChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileActionPerformed(evt);
            }
        });

        Console.setColumns(20);
        Console.setRows(5);
        jScrollPane2.setViewportView(Console);

        filePaht.setEditable(false);
        filePaht.setText("fiie....");
        filePaht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filePahtActionPerformed(evt);
            }
        });

        AddPhrase.setText("Add Phrase");
        AddPhrase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPhraseActionPerformed(evt);
            }
        });

        VocabularyList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {  };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, VocabularyList, org.jdesktop.beansbinding.ObjectProperty.create(), VocabularyList, org.jdesktop.beansbinding.BeanProperty.create("elements"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(VocabularyList);

        jLabel1.setText("Console:");

        jLabel2.setText("Input:");

        jLabel3.setText("Data:");

        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        EstimateProbability.setText("Estimate Probability");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ChooseFile)
                        .addGap(18, 18, 18)
                        .addComponent(filePaht)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Reset))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputText, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(367, 367, 367)
                                .addComponent(AddPhrase)
                                .addGap(18, 18, 18)
                                .addComponent(EstimateProbability)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChooseFile)
                    .addComponent(filePaht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Reset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddPhrase)
                            .addComponent(EstimateProbability))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void ChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseFileActionPerformed

        File fileParse = null;
        JFrame parentFrame = new JFrame();
        FileNameExtensionFilter filterfrag = new FileNameExtensionFilter(".frag", "frag");
        FileNameExtensionFilter filtertxt = new FileNameExtensionFilter(".txt", "txt");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setFileFilter(filter); //Only txt ********************************************
        fileChooser.setDialogTitle("Specify a file");  
        fileChooser.addChoosableFileFilter(filterfrag);
        fileChooser.addChoosableFileFilter(filtertxt);
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileParse = fileChooser.getSelectedFile();
            filePaht.setText("File path: " + fileParse.getAbsolutePath());
            System.out.println("File path: " + fileParse.getAbsolutePath());
            try {
                //Load new file
                String logmessage = bagOfWords.setNewFile(fileParse);
                Log(logmessage);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
            aupdateVocabularyList();
        }
        else{
            filePaht.setText("[!] Error, the file didn't load correctly");
            System.out.println("[!] Error, the file didn't load correctly");
        }
    }//GEN-LAST:event_ChooseFileActionPerformed

    private void filePahtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filePahtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filePahtActionPerformed

    private void AddPhraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPhraseActionPerformed
        // Load new words
       String logmessage  = bagOfWords.AddPhrase(inputText.getText());
        Log(logmessage);
       inputText.setText("");
       aupdateVocabularyList();
    }//GEN-LAST:event_AddPhraseActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        // TODO add your handling code here:
        bagOfWords = new BagOfWords();
        inputText.setText("");
        DefaultListModel<String> model = new DefaultListModel<>();
        VocabularyList.setModel(model);
        Console.setText("");//bad programming i know i know
        filePaht.setText("file path...");
    }//GEN-LAST:event_ResetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new View().setVisible(true);
        });
    }
    //show tags
    public void aupdateVocabularyList(){
        DefaultListModel<String> model = new DefaultListModel<>();
        List<String> Voca = bagOfWords.getVocabularyPercentage();
        model.addElement("Vocabulary size: " +Voca.size());
        for (String word:Voca) {
            model.addElement(word);
        }
        model.addElement("---------------------------------");
        List<String> tags = bagOfWords.getTagPercentage();
        model.addElement("Tags count: " + tags.size() );
        for(String tag:tags){
            model.addElement(tag );
        }
        VocabularyList.setModel(model);
    }
    //dont show tags
    public void updateVocabularyList(){
        DefaultListModel<String> model = new DefaultListModel<>();
        Set<String> Voca = bagOfWords.getVocabularyList();
        model.addElement("Vocabulary size: " +Voca.size());
        for (String word:Voca) {
            model.addElement(word);
        }
        model.addElement("------------------");
        for(String tag:bagOfWords.TagsCount.keySet()){
            model.addElement(tag );//+ " count: "+bagOfWords.TagsCount.get(tag)+"/"+Voca.size());
        }
        VocabularyList.setModel(model);
    }
    //control log messages
    public void Log(String logmessage) {
        if(Console.getText().equals(""))
            Console.setText(logmessage);
        else
            Console.setText(Console.getText() +"\n" +logmessage);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPhrase;
    private javax.swing.JButton ChooseFile;
    private javax.swing.JTextArea Console;
    private javax.swing.JButton EstimateProbability;
    private javax.swing.JButton Reset;
    private javax.swing.JList<String> VocabularyList;
    private javax.swing.JTextField filePaht;
    private javax.swing.JTextField inputText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
