/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.gui.fuzzy;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mlproject.fuzzy.LeftSigmoidMemberShip;

/**
 *
 * @author Koen Samyn
 */
public class LeftSigmoidPanel extends javax.swing.JPanel implements DocumentListener {

    private LeftSigmoidMemberShip membership;
    private Color errorColor;
    private Color normalColor;

    /**
     * Creates new form LeftSigmoidPanel
     */
    public LeftSigmoidPanel() {
        initComponents();
        for (Component c : this.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).putClientProperty("JComponent.sizeVariant", "small");
            }
        }
        txtMembershipName.getDocument().addDocumentListener(this);
        errorColor = UIManager.getDefaults().getColor("nimbusRed");
        if (errorColor == null) {
            errorColor = Color.RED;
        }
        normalColor = UIManager.getDefaults().getColor("text");
        if (normalColor == null) {
            normalColor = Color.BLACK;
        }
    }

    public void setMemberShip(LeftSigmoidMemberShip memberShip) {
        this.membership = memberShip;
        txtMembershipName.setText(memberShip.getName());
        spnCenter.setValue(memberShip.getCenter());
        spnRight.setValue(memberShip.getRight());
    }

    public void insertUpdate(DocumentEvent e) {
        updateMemberShipName();
    }

    public void removeUpdate(DocumentEvent e) {
        updateMemberShipName();
    }

    public void changedUpdate(DocumentEvent e) {
        updateMemberShipName();
    }

    private void updateMemberShipName() {
        String newName = txtMembershipName.getText();
        this.membership.setName(newName);

        if (!membership.getName().equals(newName)) {
            txtMembershipName.setForeground(errorColor);
        } else {
            txtMembershipName.setForeground(normalColor);
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
        java.awt.GridBagConstraints gridBagConstraints;

        lblName = new javax.swing.JLabel();
        txtMembershipName = new javax.swing.JTextField();
        lblCenter = new javax.swing.JLabel();
        spnCenter = new javax.swing.JSpinner();
        lblRight = new javax.swing.JLabel();
        spnRight = new javax.swing.JSpinner();
        lblFiller = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblName.setText("Name :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(lblName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(txtMembershipName, gridBagConstraints);

        lblCenter.setText("Center : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(lblCenter, gridBagConstraints);

        spnCenter.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        spnCenter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnCenterStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(spnCenter, gridBagConstraints);

        lblRight.setText("Right : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(lblRight, gridBagConstraints);

        spnRight.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        spnRight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnRightStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(spnRight, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 1.0;
        add(lblFiller, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void spnCenterStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnCenterStateChanged
        membership.setCenter((Float) spnCenter.getValue());
    }//GEN-LAST:event_spnCenterStateChanged

    private void spnRightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnRightStateChanged
        membership.setRight((Float) spnRight.getValue());
    }//GEN-LAST:event_spnRightStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCenter;
    private javax.swing.JLabel lblFiller;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRight;
    private javax.swing.JSpinner spnCenter;
    private javax.swing.JSpinner spnRight;
    private javax.swing.JTextField txtMembershipName;
    // End of variables declaration//GEN-END:variables
}
