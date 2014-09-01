/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.gui.fuzzy;

import dae.gui.fuzzy.dialog.CreateLeftSigmoidDialog;
import dae.gui.fuzzy.dialog.CreateRightSigmoidDialog;
import dae.gui.fuzzy.dialog.CreateSigmoidDialog;
import dae.gui.fuzzy.dialog.CreateTrapezoidDialog;
import dae.gui.fuzzy.model.FuzzyInputListModel;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import mlproject.fuzzy.FuzzySystem;
import mlproject.fuzzy.FuzzyVariable;
import mlproject.fuzzy.LeftSigmoidMemberShip;
import mlproject.fuzzy.MemberShip;
import mlproject.fuzzy.RightSigmoidMemberShip;
import mlproject.fuzzy.SigmoidMemberShip;
import mlproject.fuzzy.TrapezoidMemberShip;

/**
 *
 * @author Koen Samyn
 */
public class FuzzyInputVariablePanel extends javax.swing.JPanel implements ItemListener {
    
    private FuzzySystem fuzzySystem;
    private FuzzyInputListModel fuzzyInputListModel;
    private CreateLeftSigmoidDialog leftSigmoidDialog;
    private CreateRightSigmoidDialog rightSigmoidDialog;
    private CreateSigmoidDialog sigmoidDialog;
    private CreateTrapezoidDialog trapezoidDialog;

    /**
     * Creates new form FuzzyInputVariablePanel
     */
    public FuzzyInputVariablePanel() {
        initComponents();
        
        leftSigmoidDialog = new CreateLeftSigmoidDialog((Frame) this.getTopLevelAncestor(), true);
        rightSigmoidDialog = new CreateRightSigmoidDialog((Frame) this.getTopLevelAncestor(), true);
        sigmoidDialog = new CreateSigmoidDialog((Frame) this.getTopLevelAncestor(), true);
        trapezoidDialog = new CreateTrapezoidDialog((Frame) this.getTopLevelAncestor(), true);
    }
    
    public void setFuzzySystem(FuzzySystem fuzzySystem) {
        this.fuzzySystem = fuzzySystem;
        fuzzyInputListModel = new FuzzyInputListModel(fuzzySystem);
        lstFuzzyVariables.setModel(fuzzyInputListModel);
        fuzzyVariableGUI1.addItemListener(this);
        if (fuzzyInputListModel.getSize() > 0) {
            lstFuzzyVariables.setSelectedIndex(0);
            fuzzyVariableGUI1.setFuzzyVariable(fuzzySystem.getFuzzyInputAt(0));
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

        jButton1 = new javax.swing.JButton();
        memberShipEditorPanel1 = new dae.gui.fuzzy.MemberShipEditorPanel();
        pnlInputList = new javax.swing.JPanel();
        scrVariableList = new javax.swing.JScrollPane();
        lstFuzzyVariables = new javax.swing.JList();
        btnAddFuzzyVariable = new javax.swing.JButton();
        btnDeleteVariable = new javax.swing.JButton();
        lblFiller = new javax.swing.JLabel();
        pnlMemberships = new javax.swing.JPanel();
        fuzzyVariableGUI1 = new mlproject.fuzzy.gui.FuzzyVariableGUI();
        btnAddMembership = new javax.swing.JButton();
        btnRemoveMembership = new javax.swing.JButton();
        cboMembershipType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setPreferredSize(new java.awt.Dimension(800, 320));
        setLayout(new java.awt.GridBagLayout());

        memberShipEditorPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Membership Editor"));
        memberShipEditorPanel1.setMinimumSize(new java.awt.Dimension(150, 23));
        memberShipEditorPanel1.setPreferredSize(new java.awt.Dimension(150, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 4);
        add(memberShipEditorPanel1, gridBagConstraints);

        pnlInputList.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));
        pnlInputList.setLayout(new java.awt.GridBagLayout());

        scrVariableList.setMinimumSize(new java.awt.Dimension(150, 23));
        scrVariableList.setPreferredSize(new java.awt.Dimension(150, 130));

        lstFuzzyVariables.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstFuzzyVariables.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFuzzyVariablesValueChanged(evt);
            }
        });
        scrVariableList.setViewportView(lstFuzzyVariables);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        pnlInputList.add(scrVariableList, gridBagConstraints);

        btnAddFuzzyVariable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/add.png"))); // NOI18N
        btnAddFuzzyVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFuzzyVariableActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 0);
        pnlInputList.add(btnAddFuzzyVariable, gridBagConstraints);

        btnDeleteVariable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/delete.png"))); // NOI18N
        btnDeleteVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteVariableActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlInputList.add(btnDeleteVariable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        pnlInputList.add(lblFiller, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 4);
        add(pnlInputList, gridBagConstraints);

        pnlMemberships.setBorder(javax.swing.BorderFactory.createTitledBorder("Memberships"));
        pnlMemberships.setMinimumSize(new java.awt.Dimension(120, 158));
        pnlMemberships.setPreferredSize(new java.awt.Dimension(120, 158));
        pnlMemberships.setLayout(new java.awt.GridBagLayout());

        fuzzyVariableGUI1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
        fuzzyVariableGUI1.setPreferredSize(new java.awt.Dimension(300, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 4);
        pnlMemberships.add(fuzzyVariableGUI1, gridBagConstraints);

        btnAddMembership.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/add.png"))); // NOI18N
        btnAddMembership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMembershipActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 4);
        pnlMemberships.add(btnAddMembership, gridBagConstraints);

        btnRemoveMembership.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/delete.png"))); // NOI18N
        btnRemoveMembership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMembershipActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 2);
        pnlMemberships.add(btnRemoveMembership, gridBagConstraints);

        cboMembershipType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Left", "Triangular", "Right", "Trapezoid" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 0);
        pnlMemberships.add(cboMembershipType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        pnlMemberships.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 4);
        add(pnlMemberships, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void lstFuzzyVariablesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFuzzyVariablesValueChanged
        // TODO add your handling code here:

        FuzzyVariable selected = (FuzzyVariable) lstFuzzyVariables.getSelectedValue();
        if (selected != null) {
            fuzzyVariableGUI1.setFuzzyVariable(selected);
        }
    }//GEN-LAST:event_lstFuzzyVariablesValueChanged
    
    private void btnAddFuzzyVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFuzzyVariableActionPerformed
        // TODO add your handling code here:
        String name = "input";
        int index = 1;
        while (fuzzySystem.hasFuzzyInputVariable(name + index)) {
            ++index;
        }
        FuzzyVariable fv = new FuzzyVariable(name + index);
        fv.addMemberShip(new LeftSigmoidMemberShip(-15, -14, "FARLEFT"));
        fv.addMemberShip(new TrapezoidMemberShip(-15, -14, -1,-0.5f, "LEFT"));
        fv.addMemberShip(new TrapezoidMemberShip(-1,-0.5f, 0.5f, 1, "CENTER"));
        fv.addMemberShip(new TrapezoidMemberShip(0.5f, 1, 14, 15, "RIGHT"));
        fv.addMemberShip(new RightSigmoidMemberShip(14, 15, "FARRIGHT"));
        
        int addedIndex = fuzzyInputListModel.addFuzzyVariable(fv);
        lstFuzzyVariables.setSelectedIndex(addedIndex);
    }//GEN-LAST:event_btnAddFuzzyVariableActionPerformed
    
    private void btnAddMembershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMembershipActionPerformed
        // TODO add your handling code here:
        // String memberShipName = JOptionPane.show(this, "Name for this membership function : ");
        if (cboMembershipType.getSelectedItem().equals("Left")) {
            leftSigmoidDialog.setLocationRelativeTo(this);
            leftSigmoidDialog.setVisible(true);
            if (leftSigmoidDialog.getReturnStatus() == CreateLeftSigmoidDialog.RET_OK) {
                fuzzyVariableGUI1.addMemberShip(leftSigmoidDialog.getResult());
            }
        } else if (cboMembershipType.getSelectedItem().equals("Right")) {
            rightSigmoidDialog.setLocationRelativeTo(this);
            rightSigmoidDialog.setVisible(true);
            if (rightSigmoidDialog.getReturnStatus() == CreateRightSigmoidDialog.RET_OK) {
                fuzzyVariableGUI1.addMemberShip(rightSigmoidDialog.getResult());
            }
        } else if (cboMembershipType.getSelectedItem().equals("Triangular")) {
            sigmoidDialog.setLocationRelativeTo(this);
            sigmoidDialog.setVisible(true);
            if (sigmoidDialog.getReturnStatus() == CreateSigmoidDialog.RET_OK) {
                fuzzyVariableGUI1.addMemberShip(sigmoidDialog.getResult());
            }
        } else if (cboMembershipType.getSelectedItem().equals("Trapezoid")) {
            trapezoidDialog.setLocationRelativeTo(this);
            trapezoidDialog.setVisible(true);
            if (trapezoidDialog.getReturnStatus() == CreateTrapezoidDialog.RET_OK) {
                fuzzyVariableGUI1.addMemberShip(trapezoidDialog.getResult());
            }
        }
    }//GEN-LAST:event_btnAddMembershipActionPerformed
    
    private void btnRemoveMembershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMembershipActionPerformed
        fuzzyVariableGUI1.deleteSelection();
    }//GEN-LAST:event_btnRemoveMembershipActionPerformed

    private void btnDeleteVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteVariableActionPerformed
        int selectedIndex = lstFuzzyVariables.getSelectedIndex();
        fuzzyInputListModel.removeFuzzyVariable(selectedIndex);
        if ( selectedIndex == fuzzyInputListModel.getSize() ){
            selectedIndex--;
        }
        lstFuzzyVariables.setSelectedIndex(selectedIndex);
    }//GEN-LAST:event_btnDeleteVariableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFuzzyVariable;
    private javax.swing.JButton btnAddMembership;
    private javax.swing.JButton btnDeleteVariable;
    private javax.swing.JButton btnRemoveMembership;
    private javax.swing.JComboBox cboMembershipType;
    private mlproject.fuzzy.gui.FuzzyVariableGUI fuzzyVariableGUI1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFiller;
    private javax.swing.JList lstFuzzyVariables;
    private dae.gui.fuzzy.MemberShipEditorPanel memberShipEditorPanel1;
    private javax.swing.JPanel pnlInputList;
    private javax.swing.JPanel pnlMemberships;
    private javax.swing.JScrollPane scrVariableList;
    // End of variables declaration//GEN-END:variables

    public void itemStateChanged(ItemEvent e) {
        memberShipEditorPanel1.setMemberShip((MemberShip) e.getItem());
    }
}
