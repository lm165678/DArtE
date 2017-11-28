package dae.gui.level;

import com.google.common.eventbus.Subscribe;
import dae.GlobalObjects;
import dae.prefabs.ui.events.LevelEvent;
import dae.prefabs.ui.events.LevelEvent.EventType;
import dae.project.Level;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Koen Samyn
 */
public class LevelMainSettingsPanel extends javax.swing.JPanel {

    private Level current;
    private boolean disregardEvent;

    private JFileChooser chooser;

    /**
     * Creates new form LevelParameterPanel
     */
    public LevelMainSettingsPanel() {
        initComponents();
        GlobalObjects.getInstance().registerListener(this);
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("j3o files", "j3o"));
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
        lblHideShow = new javax.swing.JLabel();
        cboTargetObjects = new javax.swing.JCheckBox();
        cboHideWaypoints = new javax.swing.JCheckBox();
        txtLevelName = new javax.swing.JTextField();
        lblExportSettings = new javax.swing.JLabel();
        lblJ3OExport = new javax.swing.JLabel();
        txtJ3OFileLocation = new javax.swing.JTextField();
        btnExportAsJ3O = new javax.swing.JButton();
        cboExportOnSave = new javax.swing.JCheckBox();
        prefabComponentHeader1 = new dae.prefabs.ui.PrefabComponentHeader();
        lblOVExport = new javax.swing.JLabel();
        txtOVFileLocation = new javax.swing.JTextField();
        btnExportAsOV = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setLayout(new java.awt.GridBagLayout());

        lblName.setText("Name : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        add(lblName, gridBagConstraints);

        lblHideShow.setText("Hide/Show elements");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        add(lblHideShow, gridBagConstraints);

        cboTargetObjects.setText("Hide target objects");
        cboTargetObjects.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTargetObjectsItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(cboTargetObjects, gridBagConstraints);

        cboHideWaypoints.setText("Hide waypoints");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(cboHideWaypoints, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        add(txtLevelName, gridBagConstraints);

        lblExportSettings.setText("Export settings :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(lblExportSettings, gridBagConstraints);

        lblJ3OExport.setText("J3O :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(lblJ3OExport, gridBagConstraints);

        txtJ3OFileLocation.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(txtJ3OFileLocation, gridBagConstraints);

        btnExportAsJ3O.setText("...");
        btnExportAsJ3O.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportAsJ3OActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 4);
        add(btnExportAsJ3O, gridBagConstraints);

        cboExportOnSave.setText("Export on save");
        cboExportOnSave.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboExportOnSaveItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        add(cboExportOnSave, gridBagConstraints);

        prefabComponentHeader1.setTitle("Level");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(prefabComponentHeader1, gridBagConstraints);

        lblOVExport.setText("OV:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(lblOVExport, gridBagConstraints);

        txtOVFileLocation.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        add(txtOVFileLocation, gridBagConstraints);

        btnExportAsOV.setText("...");
        btnExportAsOV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportAsOVActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 4);
        add(btnExportAsOV, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cboTargetObjectsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTargetObjectsItemStateChanged
        if (current != null) {
            if (!disregardEvent) {
                current.showTargetObjects(evt.getStateChange() != ItemEvent.SELECTED);
            }
        }
    }//GEN-LAST:event_cboTargetObjectsItemStateChanged

    private void btnExportAsJ3OActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportAsJ3OActionPerformed
        File j3o = current.getExportLocation("j3o");
        if (j3o != null) {
            chooser.setCurrentDirectory(j3o.getParentFile());
        }
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (!selected.getPath().toLowerCase().endsWith(".j3o")) {
                selected = new File(selected.getParentFile(), selected.getName() + ".j3o");
            }
            current.setExportLocation("j3o", selected);
            txtJ3OFileLocation.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_btnExportAsJ3OActionPerformed

    private void cboExportOnSaveItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboExportOnSaveItemStateChanged
        current.setExportOnSave(cboExportOnSave.isSelected());
    }//GEN-LAST:event_cboExportOnSaveItemStateChanged

    private void btnExportAsOVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportAsOVActionPerformed
        File j3o = current.getExportLocation("ov");
        if (j3o != null) {
            chooser.setCurrentDirectory(j3o.getParentFile());
        }
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (!selected.getPath().toLowerCase().endsWith(".ov")) {
                selected = new File(selected.getParentFile(), selected.getName() + ".ov");
            }
            current.setExportLocation("ov", selected);
            txtOVFileLocation.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_btnExportAsOVActionPerformed

    @Subscribe
    public void levelSelected(LevelEvent le) {
        if (le.getEventType() == EventType.LEVELSELECTED) {
            current = le.getLevel();
            disregardEvent = true;
            cboTargetObjects.setSelected(!current.getShowsTargetObjects());
            disregardEvent = false;
            txtLevelName.setText(current.getName());
            cboExportOnSave.setSelected(current.isExportOnSave());
            File j3oExportFile = current.getExportLocation("j3o");
            if (j3oExportFile != null) {
                txtJ3OFileLocation.setText(j3oExportFile.getAbsolutePath());
            }
            File ovExportFile = current.getExportLocation("ov");
            if (ovExportFile != null) {
                txtOVFileLocation.setText(ovExportFile.getAbsolutePath());
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportAsJ3O;
    private javax.swing.JButton btnExportAsOV;
    private javax.swing.JCheckBox cboExportOnSave;
    private javax.swing.JCheckBox cboHideWaypoints;
    private javax.swing.JCheckBox cboTargetObjects;
    private javax.swing.JLabel lblExportSettings;
    private javax.swing.JLabel lblHideShow;
    private javax.swing.JLabel lblJ3OExport;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOVExport;
    private dae.prefabs.ui.PrefabComponentHeader prefabComponentHeader1;
    private javax.swing.JTextField txtJ3OFileLocation;
    private javax.swing.JTextField txtLevelName;
    private javax.swing.JTextField txtOVFileLocation;
    // End of variables declaration//GEN-END:variables
}
