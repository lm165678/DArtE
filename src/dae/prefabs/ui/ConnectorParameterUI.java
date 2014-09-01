/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.prefabs.ui;

import dae.animation.rig.Rig;
import dae.animation.rig.gui.RigConnectorDialog;
import dae.gui.EditFuzzySystemDialog;
import dae.prefabs.Prefab;
import dae.prefabs.parameters.Parameter;
import java.awt.Frame;
import mlproject.fuzzy.FuzzySystem;

/**
 *
 * @author Koen Samyn
 */
public class ConnectorParameterUI extends javax.swing.JPanel implements ParameterUI {

    private Parameter p;
    private Prefab currentPrefab;
    private static RigConnectorDialog rigConnectorDialog;

    /**
     * Creates new form FuzzyParameterUI
     */
    public ConnectorParameterUI() {
        initComponents();
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
        txtFuzzySystemName = new javax.swing.JTextField();
        btnEditRigConnectors = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lblLabel.setText("Property");
        lblLabel.setPreferredSize(new java.awt.Dimension(100, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblLabel, gridBagConstraints);

        txtFuzzySystemName.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 4, 0, 0);
        add(txtFuzzySystemName, gridBagConstraints);

        btnEditRigConnectors.setText("Edit ...");
        btnEditRigConnectors.setPreferredSize(null);
        btnEditRigConnectors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRigConnectorsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(btnEditRigConnectors, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditRigConnectorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRigConnectorsActionPerformed
        // TODO add your handling code here:
        if ( currentPrefab instanceof Rig){
            Rig rig = (Rig)currentPrefab;
            if (rigConnectorDialog == null){
                rigConnectorDialog = new RigConnectorDialog((Frame)getTopLevelAncestor(),true);
            }
            rigConnectorDialog.setRig(rig);
            rigConnectorDialog.setVisible(true);
        }
    }//GEN-LAST:event_btnEditRigConnectorsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditRigConnectors;
    private javax.swing.JLabel lblLabel;
    private javax.swing.JTextField txtFuzzySystemName;
    // End of variables declaration//GEN-END:variables

    public void setParameter(Parameter p) {
        this.p = p;
        lblLabel.setText(p.getLabel());

    }

    public void setNode(Prefab currentSelectedNode) {
        currentPrefab = currentSelectedNode;
        Object value = currentPrefab.getParameter(p.getId());
        if (value instanceof FuzzySystem) {
            FuzzySystem fs = (FuzzySystem) value;
            txtFuzzySystemName.setText(fs.getName());
        }
    }
}