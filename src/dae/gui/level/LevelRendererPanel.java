/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.gui.level;

import com.google.common.eventbus.Subscribe;
import dae.GlobalObjects;
import dae.prefabs.ui.classpath.FileNode;
import dae.prefabs.ui.events.LevelEvent;
import dae.prefabs.ui.events.ProjectEvent;
import dae.prefabs.ui.events.ProjectEventType;
import dae.project.Level;
import dae.project.Project;
import java.awt.Frame;
import java.io.File;

/**
 *
 * @author Koen Samyn
 */
public class LevelRendererPanel extends javax.swing.JPanel {

    private Project currentProject;
    private Level currentLevel;

    /**
     * Creates new form LevelRendererPanel
     */
    public LevelRendererPanel() {
        initComponents();
        GlobalObjects.getInstance().registerListener(this);
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

        prefabComponentHeader1 = new dae.prefabs.ui.PrefabComponentHeader();
        lblSkyBox = new javax.swing.JLabel();
        txtSkyBox = new javax.swing.JTextField();
        btnChooseSkybox = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.GridBagLayout());

        prefabComponentHeader1.setTitle("Render Settings");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(prefabComponentHeader1, gridBagConstraints);

        lblSkyBox.setText("Skybox texture : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblSkyBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(txtSkyBox, gridBagConstraints);

        btnChooseSkybox.setText("...");
        btnChooseSkybox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseSkyboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(btnChooseSkybox, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseSkyboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseSkyboxActionPerformed
        FileNode skyboxTexture = GlobalObjects.getInstance().selectAsset((Frame) this.getTopLevelAncestor(), this, currentProject, "Choose skybox texture", "dds");
        if (skyboxTexture != null) {
            txtSkyBox.setText(skyboxTexture.getFullName());
            currentLevel.setSkyBoxTexture(skyboxTexture.getFullName());
        }
    }//GEN-LAST:event_btnChooseSkyboxActionPerformed

    /*
     * Called when the project has changed.
     * @param pe the project event.
     */
    @Subscribe
    public void projectChanged(ProjectEvent pe) {
        if (pe.getEventType() == ProjectEventType.SELECTED) {
            currentProject = pe.getProject();
        }
    }

    @Subscribe
    public void levelSelected(LevelEvent le) {
        if (le.getEventType() == LevelEvent.EventType.LEVELSELECTED) {
            currentLevel = le.getLevel();
            txtSkyBox.setText(currentLevel.getSkyBoxTexture());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseSkybox;
    private javax.swing.JLabel lblSkyBox;
    private dae.prefabs.ui.PrefabComponentHeader prefabComponentHeader1;
    private javax.swing.JTextField txtSkyBox;
    // End of variables declaration//GEN-END:variables
}