/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.gui;

import com.google.common.eventbus.Subscribe;
import dae.GlobalObjects;
import dae.prefabs.ui.events.ErrorMessage;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Koen Samyn
 */
public class OutputPanel extends javax.swing.JPanel {
    
    private Style errorStyle;
    private StyleContext styleContext = new StyleContext();
    private DefaultStyledDocument outputDocument;

    /**
     * Creates new form OutputPanel
     */
    public OutputPanel() {
        outputDocument = new DefaultStyledDocument(styleContext);
        // Create and add the main document style
        Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        errorStyle = styleContext.addStyle("ErrorStyle", defaultStyle);
        Color nimbusRed = UIManager.getDefaults().getColor("nimbusRed");
        nimbusRed = nimbusRed == null ? Color.red:nimbusRed;
        StyleConstants.setForeground(errorStyle, nimbusRed);
        initComponents();
        
        GlobalObjects.getInstance().registerListener(this);
        
    }
    
    @Subscribe
    public void errorMessage(ErrorMessage message) {
        try {
            outputDocument.insertString(outputDocument.getLength(), message.getText() + "\n",errorStyle);
        } catch (BadLocationException ex) {
            Logger.getLogger(OutputPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrOutput = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        txtOutput.setDocument(this.outputDocument);
        scrOutput.setViewportView(txtOutput);

        add(scrOutput, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrOutput;
    private javax.swing.JTextPane txtOutput;
    // End of variables declaration//GEN-END:variables
}
