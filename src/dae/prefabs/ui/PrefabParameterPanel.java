/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.prefabs.ui;

import com.google.common.eventbus.Subscribe;
import dae.GlobalObjects;
import dae.prefabs.Prefab;
import dae.prefabs.parameters.ParameterSection;
import dae.prefabs.types.ObjectType;
import dae.prefabs.types.ObjectTypeCategory;
import dae.prefabs.ui.events.SelectionEvent;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author samyn_000
 */
public class PrefabParameterPanel extends javax.swing.JPanel {

    private Prefab selected;
    private ObjectTypeCategory otCategories;
    private ObjectType currentObjectType;

    private JLabel filler =new JLabel();
    /**
     * Creates new form PrefabParameterPanel
     */
    public PrefabParameterPanel() {
        initComponents();
        GlobalObjects.getInstance().registerListener(this);
    }

    public void createSelectedNodeUI() {
        if (selected == null) {
            return;
        }
        //System.out.println("Create selected ui");
        // find the correct definition for the type.
        if (otCategories == null) {
            otCategories = GlobalObjects.getInstance().getObjectsTypeCategory();
        }

        ObjectType ot = otCategories.getObjectType(selected.getCategory(), selected.getType());

        //System.out.println("Found description : " + ot.getLabel());
        if (ot == null || ot != currentObjectType) {
            // remove parameter panels.
            for (Component c : this.getComponents()) {
                this.remove(c);
            }
            currentObjectType = ot;
            this.invalidate();
        }

        //System.out.println("Adding parameter sections");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 7, 6, 7);

        if (currentObjectType != null) {
            for (ParameterSection ps : currentObjectType.getParameterSections()) {
                ParameterPanel pp = ps.createParameterPanel();

                pp.setParameterSection(ps);
                pp.setNode(selected);
                this.add(pp, gbc);
                gbc.gridy++;
                pp.revalidate();
            }
        }
        gbc.weighty = 1.0;
        this.add(filler,gbc);
        synchronized (this.getTreeLock()) {
            this.validateTree();
        }
        this.repaint();
    }

    @Subscribe
    public void prefabSelected(SelectionEvent selectionEvent) {
        //System.out.println("Prefab selected: " + selectionEvent.getSelectedNode().getName());
        this.selected = selectionEvent.getSelectedNode();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createSelectedNodeUI();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        add(jLabel1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
