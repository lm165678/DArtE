package dae.prefabs.ui;

import dae.GlobalObjects;
import dae.prefabs.Prefab;
import dae.prefabs.parameters.Parameter;
import dae.prefabs.standard.SoundEntity;
import dae.prefabs.ui.classpath.FileNode;
import dae.project.Project;
import dae.project.ProjectTreeNode;
import java.awt.Frame;

/**
 *
 * @author Koen Samyn
 */
public class SoundParameterUI extends javax.swing.JPanel implements ParameterUI {

    private Parameter parameter;
    private Prefab currentNode;

    /**
     * Creates new form SoundParameterUI
     */
    public SoundParameterUI() {
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
        txtObjectName = new javax.swing.JTextField();
        btnPickSound = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lblLabel.setText("Label :");
        lblLabel.setPreferredSize(new java.awt.Dimension(100, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblLabel, gridBagConstraints);

        txtObjectName.setEditable(false);
        txtObjectName.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(txtObjectName, gridBagConstraints);

        btnPickSound.setText("...");
        btnPickSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPickSoundActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(btnPickSound, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPickSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPickSoundActionPerformed
        Frame rootComponent = (Frame) this.getTopLevelAncestor();
        Project currentProject = null;
        ProjectTreeNode p = currentNode;
        while (p.getProjectParent() != null) {
            if (p.getProjectParent() instanceof Project) {
                currentProject = (Project) p.getProjectParent();
                break;
            }
            p = p.getProjectParent();
        }
        if (currentProject != null) {
            // todo replace with query in application extension registry.
            FileNode fn = GlobalObjects.getInstance().selectAsset(rootComponent, this, currentProject, "Select sound", "oog|wav");
            if (currentNode instanceof SoundEntity && fn != null) {
                currentNode.setParameter(parameter.getId(),fn.getFullName(),true);
                txtObjectName.setText(fn.getFullName());
            }
        }
    }//GEN-LAST:event_btnPickSoundActionPerformed

    public void setParameter(Parameter p) {
        this.parameter = p;
        lblLabel.setText(parameter.getLabel());
    }

    public void setNode(Prefab currentSelectedNode) {
        this.currentNode = currentSelectedNode;
        txtObjectName.setText(currentSelectedNode.getParameter(parameter.getId()).toString());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPickSound;
    private javax.swing.JLabel lblLabel;
    private javax.swing.JTextField txtObjectName;
    // End of variables declaration//GEN-END:variables
}