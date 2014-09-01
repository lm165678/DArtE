/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.prefabs.ui;

import com.google.common.eventbus.Subscribe;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import dae.GlobalObjects;
import dae.prefabs.Prefab;
import dae.prefabs.parameters.Parameter;
import dae.prefabs.parameters.converter.PropertyConverter;

import dae.prefabs.standard.UpdateObject;
import dae.prefabs.ui.events.PrefabChangedEvent;
import dae.prefabs.ui.events.PrefabChangedEventType;
import static dae.prefabs.ui.events.PrefabChangedEventType.TRANSLATION;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Koen
 */
public class Float3ParameterUI extends javax.swing.JPanel implements ParameterUI {

    private Parameter parameter;
    private Prefab currentNode;
    private boolean disregardEvent = false;
    private PrefabChangedEventType eventType;
    private PropertyConverter converter;

    /**
     * Creates new form Float3ParameterUI
     */
    public Float3ParameterUI() {
        initComponents();
        for (Component c : this.getComponents()) {
            if (c instanceof JComponent && c != lblLabel) {
                ((JComponent) c).putClientProperty("JComponent.sizeVariant", "small");
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setLabel(String label) {
        lblLabel.setText(label);
    }

    public String getLabel() {
        return lblLabel.getText();
    }

    public void setFloat3(Vector3f value) {
        disregardEvent = true;
        xSpinner.setValue(value.x);
        ySpinner.setValue(value.y);
        zSpinner.setValue(value.z);
        disregardEvent = false;
    }

    public void setFloat3(float x, float y, float z) {
        disregardEvent = true;
        xSpinner.setValue(x);
        ySpinner.setValue(y);
        zSpinner.setValue(z);
        disregardEvent = false;
    }

    public Vector3f getFloat3() {
        float x = (Float) xSpinner.getValue();
        float y = (Float) ySpinner.getValue();
        float z = (Float) zSpinner.getValue();
        return new Vector3f(x, y, z);
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
        lblBracket = new javax.swing.JLabel();
        xSpinner = new javax.swing.JSpinner();
        ySpinner = new javax.swing.JSpinner();
        lblComma1 = new javax.swing.JLabel();
        lblComma2 = new javax.swing.JLabel();
        zSpinner = new javax.swing.JSpinner();
        lblCloseBracket = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblLabel.setText("float3 : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblLabel, gridBagConstraints);

        lblBracket.setText("[");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 2, 0, 0);
        add(lblBracket, gridBagConstraints);

        xSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.5f)));
        xSpinner.setMinimumSize(new java.awt.Dimension(100, 22));
        xSpinner.setPreferredSize(null);
        xSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        add(xSpinner, gridBagConstraints);

        ySpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.5f)));
        ySpinner.setMinimumSize(new java.awt.Dimension(100, 22));
        ySpinner.setPreferredSize(null);
        ySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ySpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        add(ySpinner, gridBagConstraints);

        lblComma1.setText(",");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 0, 0);
        add(lblComma1, gridBagConstraints);

        lblComma2.setText(",");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 0, 0);
        add(lblComma2, gridBagConstraints);

        zSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.5f)));
        zSpinner.setAlignmentX(0.0F);
        zSpinner.setMinimumSize(new java.awt.Dimension(100, 22));
        zSpinner.setPreferredSize(null);
        zSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        add(zSpinner, gridBagConstraints);

        lblCloseBracket.setText("]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 0, 0);
        add(lblCloseBracket, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void xSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xSpinnerStateChanged
        updateNode();
    }//GEN-LAST:event_xSpinnerStateChanged

    private void ySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ySpinnerStateChanged
        updateNode();
    }//GEN-LAST:event_ySpinnerStateChanged

    private void zSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zSpinnerStateChanged
        updateNode();
    }//GEN-LAST:event_zSpinnerStateChanged

    @Override
    public void setParameter(Parameter p) {
        parameter = p;
        converter = p.getConverter();
        if (p.getId().equals("localTranslation") || p.getId().equals("localPrefabTranslation")) {
            eventType = PrefabChangedEventType.TRANSLATION;
        } else if (p.getId().equals("localRotation") || p.getId().equals("localPrefabTranslation")) {
            eventType = PrefabChangedEventType.ROTATION;
        } else if (p.getId().equals("localScale")) {
            eventType = PrefabChangedEventType.SCALE;
        }
        if (eventType != null) {
            GlobalObjects.getInstance().registerListener(this);
        }
    }

    @Override
    public void setNode(Prefab currentSelectedNode) {
        currentNode = currentSelectedNode;
        if (currentNode == null) {
            return;
        }
        String property = parameter.getId();
        Object value = currentSelectedNode.getParameter(property);
        if ( converter != null ){
            value = converter.convertFromObjectToUI(value);
        }
        if (value != null && value instanceof Vector3f) {
            this.setFloat3((Vector3f) value);
        }
    }

    @Subscribe
    public void prefabChanged(final PrefabChangedEvent event) {
        if (event.getType() == this.eventType && event.getChangedNode() == this.currentNode) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    switch (event.getType()) {
                        case TRANSLATION:
                            setFloat3(currentNode.getLocalPrefabTranslation());
                            break;
                        case ROTATION:
                            float angles[] = new float[3];
                            currentNode.getLocalPrefabRotation().toAngles(angles);
                            setFloat3(angles[0]*FastMath.RAD_TO_DEG, angles[1]*FastMath.RAD_TO_DEG, angles[2]*FastMath.RAD_TO_DEG);
                            break;
                        case SCALE:
                            setFloat3(currentNode.getLocalScale());
                            break;
                    }
                }
            });
        }
    }

    private void updateNode() {
        if (disregardEvent) {
            return;
        }
        Object value = getFloat3();
        if ( converter != null){
            value = converter.convertFromUIToObject(value);
        }
        String property = parameter.getId();
        if (currentNode != null) {
            //System.out.println("Setting " + property + " to " + value);
            currentNode.addUpdateObject(new UpdateObject(property, value, true));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBracket;
    private javax.swing.JLabel lblCloseBracket;
    private javax.swing.JLabel lblComma1;
    private javax.swing.JLabel lblComma2;
    private javax.swing.JLabel lblLabel;
    private javax.swing.JSpinner xSpinner;
    private javax.swing.JSpinner ySpinner;
    private javax.swing.JSpinner zSpinner;
    // End of variables declaration//GEN-END:variables
}
