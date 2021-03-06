package dae.gui.preferences;

import com.jme3.math.Vector3f;
import dae.GlobalObjects;
import dae.prefabs.AxisEnum;
import java.io.File;
import java.util.List;

/**
 *
 * @author Koen Samyn
 */
public class EditingOptionPanel extends javax.swing.JPanel implements PreferencePanel{

    /** Creates new form EditingOptionPanel */
    public EditingOptionPanel() {
        initComponents();
        Vector3f grid = GlobalObjects.getInstance().getGrid();
        spnGridX.setValue(grid.x);
        spnGridY.setValue(grid.y);
        spnGridZ.setValue(grid.z);
        
        AxisEnum upAxis  = GlobalObjects.getInstance().getUpAxis();
        this.cboUpAxis.setSelectedItem(upAxis);
        
        List<File> recentFiles = GlobalObjects.getInstance().getRecentFiles();
        lstRecentFiles.setListData(recentFiles.toArray());
    }
    
    public void doOk(){
        String axis = cboUpAxis.getSelectedItem().toString();
        AxisEnum upAxis = AxisEnum.valueOf(axis);
        GlobalObjects.getInstance().setUpAxis(upAxis);

        float x = (Float) spnGridX.getValue();
        float y = (Float) spnGridY.getValue();
        float z = (Float) spnGridZ.getValue();
        GlobalObjects.getInstance().setGrid(new Vector3f(x, y, z));
    }
    
    public void createBackup() {
        
    }

    public void commitChanges() {
        doOk();
    }

    public void revertChanges() {
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblUpAxis = new javax.swing.JLabel();
        cboUpAxis = new javax.swing.JComboBox();
        lblGrid = new javax.swing.JLabel();
        lblX = new javax.swing.JLabel();
        spnGridX = new javax.swing.JSpinner();
        lblY = new javax.swing.JLabel();
        spnGridY = new javax.swing.JSpinner();
        lblZ = new javax.swing.JLabel();
        spnGridZ = new javax.swing.JSpinner();
        scrRecentFileList = new javax.swing.JScrollPane();
        lstRecentFiles = new javax.swing.JList();
        lblRecentFileList = new javax.swing.JLabel();
        btnClearRecentFileList = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lblUpAxis.setText("Default Up Axis :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        add(lblUpAxis, gridBagConstraints);

        cboUpAxis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "X", "Y", "Z" }));
        cboUpAxis.setSelectedIndex(1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        add(cboUpAxis, gridBagConstraints);

        lblGrid.setText("Grid :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(lblGrid, gridBagConstraints);

        lblX.setText("X:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(lblX, gridBagConstraints);

        spnGridX.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(spnGridX, gridBagConstraints);

        lblY.setText("Y : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(lblY, gridBagConstraints);

        spnGridY.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(spnGridY, gridBagConstraints);

        lblZ.setText("Z :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(lblZ, gridBagConstraints);

        spnGridZ.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(spnGridZ, gridBagConstraints);

        scrRecentFileList.setViewportView(lstRecentFiles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 2, 2);
        add(scrRecentFileList, gridBagConstraints);

        lblRecentFileList.setText("Recent file list:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 0, 2);
        add(lblRecentFileList, gridBagConstraints);

        btnClearRecentFileList.setText("Clear");
        btnClearRecentFileList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearRecentFileListActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(btnClearRecentFileList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearRecentFileListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearRecentFileListActionPerformed
        GlobalObjects.getInstance().clearRecentFileList();
    }//GEN-LAST:event_btnClearRecentFileListActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearRecentFileList;
    private javax.swing.JComboBox cboUpAxis;
    private javax.swing.JLabel lblGrid;
    private javax.swing.JLabel lblRecentFileList;
    private javax.swing.JLabel lblUpAxis;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lblZ;
    private javax.swing.JList lstRecentFiles;
    private javax.swing.JScrollPane scrRecentFileList;
    private javax.swing.JSpinner spnGridX;
    private javax.swing.JSpinner spnGridY;
    private javax.swing.JSpinner spnGridZ;
    // End of variables declaration//GEN-END:variables



}
