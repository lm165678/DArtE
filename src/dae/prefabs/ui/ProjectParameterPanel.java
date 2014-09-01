/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.prefabs.ui;

import com.google.common.eventbus.Subscribe;
import dae.GlobalObjects;
import dae.gui.model.ProjectAssetFolderListModel;
import dae.gui.model.ProjectLevelListModel;
import dae.gui.renderers.ProjectAssetFolderListCellRenderer;
import dae.gui.renderers.ProjectLevelListCellRenderer;
import dae.prefabs.ui.events.ProjectEvent;
import dae.prefabs.ui.events.ProjectEventType;
import dae.project.Project;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * Shows the properties of the current project.
 * @author Koen Samyn
 */
public class ProjectParameterPanel extends javax.swing.JPanel {
    private Project currentProject;
    private JFileChooser directoryChooser = new JFileChooser();
    
    private ProjectAssetFolderListModel assetFolderListModel;
    /**
     * Creates new form ProjectParameterPanel
     */
    public ProjectParameterPanel() {
        initComponents();
        GlobalObjects.getInstance().registerListener(this);
        
        lstLevels.setCellRenderer(new ProjectLevelListCellRenderer());
        lstAssetFolders.setCellRenderer(new ProjectAssetFolderListCellRenderer());
        
        directoryChooser.setDialogTitle("Add asset folder");
        directoryChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);
    }
    
    @Subscribe
    public void projectChanged(ProjectEvent event){
        currentProject = event.getProject();
        if ( currentProject == null ){
            lstLevels.setModel(null);
            return;
        }
        this.txtProjectName.setText(currentProject.getProjectName());
        
        this.lstLevels.setModel(new ProjectLevelListModel(currentProject));
        assetFolderListModel = new ProjectAssetFolderListModel(currentProject);
        this.lstAssetFolders.setModel(assetFolderListModel);
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

        scrContent = new javax.swing.JScrollPane();
        pnlProject = new javax.swing.JPanel();
        lblProjectName = new javax.swing.JLabel();
        txtProjectName = new javax.swing.JTextField();
        lblLevels = new javax.swing.JLabel();
        scrLevels = new javax.swing.JScrollPane();
        lstLevels = new javax.swing.JList();
        lblAssets = new javax.swing.JLabel();
        scrAssetFolders = new javax.swing.JScrollPane();
        lstAssetFolders = new javax.swing.JList();
        pnlAssetFolderButtons = new javax.swing.JPanel();
        btnAddAssetFolder = new javax.swing.JButton();
        btnRemoveAssetFolder = new javax.swing.JButton();
        btnMoveUp = new javax.swing.JButton();
        btnMoveDown = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Project"));
        setLayout(new java.awt.BorderLayout());

        scrContent.setBorder(null);
        scrContent.setPreferredSize(null);

        pnlProject.setMinimumSize(new java.awt.Dimension(100, 100));
        pnlProject.setLayout(new java.awt.GridBagLayout());

        lblProjectName.setText("Project name: ");
        lblProjectName.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlProject.add(lblProjectName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 10);
        pnlProject.add(txtProjectName, gridBagConstraints);

        lblLevels.setText("Levels:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        pnlProject.add(lblLevels, gridBagConstraints);

        lstLevels.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scrLevels.setViewportView(lstLevels);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 0, 10);
        pnlProject.add(scrLevels, gridBagConstraints);

        lblAssets.setText("Asset Folders:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        pnlProject.add(lblAssets, gridBagConstraints);

        lstAssetFolders.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scrAssetFolders.setViewportView(lstAssetFolders);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        pnlProject.add(scrAssetFolders, gridBagConstraints);

        pnlAssetFolderButtons.setLayout(new javax.swing.BoxLayout(pnlAssetFolderButtons, javax.swing.BoxLayout.PAGE_AXIS));

        btnAddAssetFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/folder_add.png"))); // NOI18N
        btnAddAssetFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAssetFolderActionPerformed(evt);
            }
        });
        pnlAssetFolderButtons.add(btnAddAssetFolder);

        btnRemoveAssetFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/folder_delete.png"))); // NOI18N
        btnRemoveAssetFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAssetFolderActionPerformed(evt);
            }
        });
        pnlAssetFolderButtons.add(btnRemoveAssetFolder);

        btnMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/arrow_up.png"))); // NOI18N
        pnlAssetFolderButtons.add(btnMoveUp);

        btnMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dae/icons/arrow_down.png"))); // NOI18N
        pnlAssetFolderButtons.add(btnMoveDown);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        pnlProject.add(pnlAssetFolderButtons, gridBagConstraints);

        scrContent.setViewportView(pnlProject);

        add(scrContent, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAssetFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssetFolderActionPerformed
        // TODO add your handling code here:
        int option = directoryChooser.showDialog(this, "Add" );
        if ( option == JFileChooser.APPROVE_OPTION){
            File directory = directoryChooser.getSelectedFile();
            this.currentProject.addAssetFolder(directory);
            int index = currentProject.getAssetFolderIndex(directory);
            assetFolderListModel.assetFolderAdded(index);
            
            GlobalObjects.getInstance().postEvent(new ProjectEvent(currentProject,ProjectEventType.ASSETFOLDERCHANGED,this));
        }
    }//GEN-LAST:event_btnAddAssetFolderActionPerformed

    private void btnRemoveAssetFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAssetFolderActionPerformed
        // TODO add your handling code here:
        File file = (File) lstAssetFolders.getSelectedValue();
        int indexOfFile = currentProject.getAssetFolderIndex(file);
        if ( indexOfFile > -1 )
        {
            currentProject.removeAssetFolder(file);
            assetFolderListModel.assetFolderRemoved(indexOfFile);
            GlobalObjects.getInstance().postEvent(new ProjectEvent(currentProject,ProjectEventType.ASSETFOLDERCHANGED,this));
        }
    }//GEN-LAST:event_btnRemoveAssetFolderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssetFolder;
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnRemoveAssetFolder;
    private javax.swing.JLabel lblAssets;
    private javax.swing.JLabel lblLevels;
    private javax.swing.JLabel lblProjectName;
    private javax.swing.JList lstAssetFolders;
    private javax.swing.JList lstLevels;
    private javax.swing.JPanel pnlAssetFolderButtons;
    private javax.swing.JPanel pnlProject;
    private javax.swing.JScrollPane scrAssetFolders;
    private javax.swing.JScrollPane scrContent;
    private javax.swing.JScrollPane scrLevels;
    private javax.swing.JTextField txtProjectName;
    // End of variables declaration//GEN-END:variables
}
