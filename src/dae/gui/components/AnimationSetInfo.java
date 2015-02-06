/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dae.gui.components;

import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import dae.GlobalObjects;
import dae.animation.AnimationSet;
import dae.prefabs.ui.classpath.FileNode;
import dae.project.Project;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Koen Samyn
 */
public class AnimationSetInfo extends javax.swing.JPanel {

    private Project project;
    ArrayList<String> animations = new ArrayList<String>();
    
    /** Creates new form AnimationSetInfo */
    public AnimationSetInfo() {
        initComponents();
    }

    public void setProject(Project project){
        this.project = project;
    }
    
    public void setFileNode(FileNode node){
        AssetManager am = GlobalObjects.getInstance().getAssetManager();
        AnimationSet animSet = (AnimationSet)am.loadAsset(node.getFullName());
        animations.clear();
        for ( Spatial sp : animSet.getAnimations()){
            AnimControl ac = sp.getControl(AnimControl.class);
            if (ac != null)
            {
                animations.addAll(ac.getAnimationNames());
            }
        }
        lstAnimations.setListData(animations.toArray());
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

        nonCollapsibleHeader1 = new dae.gui.components.NonCollapsibleHeader();
        lblAnimations = new javax.swing.JLabel();
        scrAnimations = new javax.swing.JScrollPane();
        lstAnimations = new javax.swing.JList();
        lblFiller = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        nonCollapsibleHeader1.setTitle("Animation Set");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(nonCollapsibleHeader1, gridBagConstraints);

        lblAnimations.setText("Animations :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblAnimations, gridBagConstraints);

        scrAnimations.setViewportView(lstAnimations);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 20);
        add(scrAnimations, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1.0;
        add(lblFiller, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAnimations;
    private javax.swing.JLabel lblFiller;
    private javax.swing.JList lstAnimations;
    private dae.gui.components.NonCollapsibleHeader nonCollapsibleHeader1;
    private javax.swing.JScrollPane scrAnimations;
    // End of variables declaration//GEN-END:variables

}