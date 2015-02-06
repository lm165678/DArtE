package dae.prefabs.ui;

import dae.prefabs.Prefab;
import dae.prefabs.ReflectionManager;
import dae.prefabs.parameters.Parameter;

/**
 *
 * @author Koen
 */
public class TextParameterUI extends javax.swing.JPanel implements ParameterUI {

    private Parameter parameter;
    private boolean disregardEvent;
    private Prefab currentNode;

    /**
     * Creates new form TextParameterUI
     */
    public TextParameterUI() {
        initComponents();
    }

    @Override
    public void setParameter(Parameter p) {
        this.parameter = p;
    }
    
    public Parameter getParameter(){
        return parameter;
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

        jLabel1 = new javax.swing.JLabel();
        txtMessage = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(txtMessage, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables

    

    @Override
    public void setNode(Prefab currentSelectedNode) {
        currentNode = currentSelectedNode;
        Object value = ReflectionManager.getInstance().invokeGetMethod(currentSelectedNode, parameter);
        if (value != null) {
            disregardEvent = true;
            txtMessage.setText(value.toString());
            disregardEvent = false;
        }
    }
    
    /**
     * Checks if a label should be created for the UI.
     * @return true if a label should be created, false othwerise.
     */
    public boolean needsLabel(){
        return true;
    }
}
