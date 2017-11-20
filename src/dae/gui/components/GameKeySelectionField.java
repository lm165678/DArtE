package dae.gui.components;

import dae.GlobalObjects;
import dae.gui.preferences.GameKeyDefinition;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Koen Samyn
 */
public class GameKeySelectionField extends javax.swing.JPanel implements KeyListener {

    private int keyCode;
    private int extendedKeyCode;
    
    private int changedKeyCode;
    private int changedExtKeyCode;
    
    private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

    /**
     * Creates new form GameKeySelectionField
     */
    public GameKeySelectionField() {
        initComponents();
        txtKey.addKeyListener(this);
    }
    
    /**
     * Adds a change listener to the list of change listeners.
     * @param cl the changelistener to add.
     */
    public void addChangeListener(ChangeListener cl){
        changeListeners.add(cl);
    }
    
    /**
     * Removes the change listener from the list of change listeners.
     * @param cl the changelistener to remove.
     */
    public void removeChangeListener(ChangeListener cl){
        changeListeners.remove(cl);
    }

    /**
     * Gets the label to show.
     *
     * @return the label of the keyselectionfield.
     */
    public String getLabel() {
        return lblLabel.getText();
    }

    /**
     * Sets the label of the key selection field.
     *
     * @param label the new label for the selection field.
     */
    public void setLabel(String label) {
        lblLabel.setText(label);
    }

    /**
     * Sets the key code for this component.
     *
     * @param keyCode the simple keycode, related to the physical layout of the
     * keyboard.
     * @param extendedKeyCode the extended keycode, that supports the actual
     * character on the physical key.
     */
    public void setKey(int keyCode, int extendedKeyCode) {
        this.keyCode = keyCode;
        this.extendedKeyCode = extendedKeyCode;
        adaptTextField();
    }


    /**
     * Sets the initial key code for this component.
     * @param gameKeyDefinition the changed game key definition.
     */
    public void setKey(GameKeyDefinition gameKeyDefinition) {
        this.keyCode = gameKeyDefinition.getJavaKeyCode();
        this.extendedKeyCode = gameKeyDefinition.getJavaKeyCode();
        adaptTextField();
    }
    
    /**
     * Creates a new GameKeyDefinition based on the contents of this component.
     * @return a new GameKeyDefinition object.
     */
    public GameKeyDefinition getKey(){
        GlobalObjects go = GlobalObjects.getInstance();
        GameKeyDefinition gkd = new GameKeyDefinition(keyCode, extendedKeyCode, go.convertKeyCodeToJMonkeyEngine(keyCode));
        return gkd;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblLabel = new javax.swing.JLabel();
        txtKey = new javax.swing.JTextField();
        btnChange = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lblLabel.setText("Label");
        lblLabel.setMinimumSize(new java.awt.Dimension(100, 14));
        lblLabel.setPreferredSize(new java.awt.Dimension(100, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(lblLabel, gridBagConstraints);

        txtKey.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(txtKey, gridBagConstraints);

        btnChange.setText("Change");
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(btnChange, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        txtKey.setEnabled(true);
        txtKey.setText("");
        txtKey.requestFocus();
    }//GEN-LAST:event_btnChangeActionPerformed

    /**
     * Adapt the text field to the contents of the stroke.
     */
    private void adaptTextField() {
        String key = KeyEvent.getKeyText(keyCode);
        String extendedKey = KeyEvent.getKeyText(extendedKeyCode);
        if (keyCode != extendedKeyCode) {
            txtKey.setText(extendedKey + " ( " + key + " )");
        } else {
            txtKey.setText(key);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChange;
    private javax.swing.JLabel lblLabel;
    private javax.swing.JTextField txtKey;
    // End of variables declaration//GEN-END:variables

    public void keyTyped(KeyEvent e) {
        txtKey.setEnabled(false);
        
        if ( keyCode != changedKeyCode || extendedKeyCode != changedExtKeyCode )
        {
            keyCode = changedKeyCode;
            extendedKeyCode = changedExtKeyCode;
            notifyChangeListeners();
        }
        
        adaptTextField();
    }

    public void keyPressed(KeyEvent e) {
        changedKeyCode = e.getKeyCode();
        changedExtKeyCode = e.getExtendedKeyCode();
    }

    public void keyReleased(KeyEvent e) {
    }

    /**
     * Notify the listeners that the key has changed.
     */
    private void notifyChangeListeners() {
        ChangeEvent ce = new ChangeEvent(this);
        for ( ChangeListener cl : changeListeners){
            cl.stateChanged(ce);
        }
    }
}
