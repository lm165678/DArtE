package dae.prefabs.ui;

import dae.prefabs.Prefab;
import dae.prefabs.ReflectionManager;
import dae.prefabs.parameters.Parameter;

/**
 *
 * @author Koen Samyn
 */
public class BooleanParameterUI extends javax.swing.JPanel implements ParameterUI {

    private Parameter parameter;
    private Prefab prefab;

    private boolean internalEvent = true;
    /**
     * Creates new form BooleanParameterUI
     */
    public BooleanParameterUI() {
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

        cbxBooleanParameter = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        cbxBooleanParameter.setText("Label");
        cbxBooleanParameter.setMinimumSize(null);
        cbxBooleanParameter.setPreferredSize(null);
        cbxBooleanParameter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxBooleanParameterItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(cbxBooleanParameter, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxBooleanParameterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxBooleanParameterItemStateChanged
        // TODO add your handling code here:
        if ( prefab != null && !internalEvent){
            prefab.setParameter(parameter, cbxBooleanParameter.isSelected(), true);
        }
    }//GEN-LAST:event_cbxBooleanParameterItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbxBooleanParameter;
    // End of variables declaration//GEN-END:variables

    public void setParameter(Parameter p) {
        cbxBooleanParameter.setText(p.getLabel());
        this.parameter = p;
    }
    
    public Parameter getParameter(){
        return parameter;
    }

    public void setNode(Prefab prefab) {
        Object value = ReflectionManager.getInstance().invokeGetMethod(prefab,parameter); 
        if (value != null && value instanceof Boolean) {
            internalEvent = true;
            cbxBooleanParameter.setSelected((Boolean) value);
            internalEvent = false;
        }
        this.prefab = prefab;
    }
    
    /**
     * Checks if a label should be created for the UI.
     * @return true if a label should be created, false othwerise.
     */
    public boolean needsLabel(){
        return false;
    }
}