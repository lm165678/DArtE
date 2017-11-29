/*
 * Digital Arts and Entertainment 
 */
package dae.animation.rig.timing.gui;

import com.jme3.scene.Spatial;
import dae.animation.rig.timing.Behaviour;
import dae.animation.rig.timing.TimeLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Koen.Samyn
 */
public class FramePanel extends javax.swing.JPanel implements MouseListener {

    private Behaviour model;
    private float scale;
    private int marginBottom = 10;
    private int marginTop = 20;

    private final Stroke thickStroke = new BasicStroke(2.0f);
    private final Stroke thinStroke = new BasicStroke(1.0f);
    private Color selectionColor = new Color(245, 194, 20, 127);

    private final BasicStroke thinDashStroke
            = new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[]{10.0f}, 0.0f);
    /**
     * The minimum frames to show.
     */
    private int minVisibleFrames = 20;
    /**
     * The amount of pixels used for the labels.
     */
    private int labelWidth = 0;
    /**
     * The current tool to use.
     */
    private TimingTool currentTool;
    private final SelectTool selectTool;

    /**
     * Creates new form FramePanel
     */
    public FramePanel() {
        initComponents();
        selectTool = new SelectTool(this);
        currentTool = selectTool;
        addMouseListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setBehaviour(Behaviour model) {
        this.model = model;
        repaint();
    }

    public Behaviour getBehaviour() {
        return model;
    }

    public int mouseXToFrame(int x) {
        int maxFrameNumber = Math.max(this.minVisibleFrames, model.getMaxFrameNumber());
        float cellWidth = getWidth() * 1.0f / maxFrameNumber;
        int frame = (int) ((x - labelWidth) / cellWidth);
        return frame;
    }

    /**
     * Paints the timeline.
     *
     * @param g the timeline to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (model == null) {
            return;
        }
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = this.getWidth();
        int height = this.getHeight();

        int maxFrameNumber = Math.max(this.minVisibleFrames, model.getMaxFrameNumber());
        float cellWidth = width * 1.0f / maxFrameNumber;

        int numTimeLines = model.getNumberOfTimeLines();
        float cellHeight = (height - marginTop - marginBottom) * 1.0f / numTimeLines;

        labelWidth = 0;
        int y = marginTop;
        for (TimeLine tl : model.getTimeLines()) {
            Spatial target = tl.getTarget();
            String label = target.getName();
            FontMetrics fm = g2d.getFontMetrics();
            int currentWidth = fm.stringWidth(label);

            labelWidth = currentWidth > labelWidth ? currentWidth : labelWidth;
            
            g.drawString(label, 2, y + fm.getAscent());
            y+=cellHeight;
        }
        labelWidth +=4;

        for (int frame = 0; frame < maxFrameNumber; frame++) {
            int currentTimeLine = 0;
            int x = (int) (frame * cellWidth) + labelWidth;
            if (frame % 5 == 0) {
                g2d.setStroke(thickStroke);
                g2d.drawString(Integer.toString(frame), x, 10);
            } else {
                g2d.setStroke(thinStroke);
            }

            g2d.drawLine(x, marginTop, x, height - marginBottom);
            for (TimeLine tl : model.getTimeLines()) {

                if (tl.containsKey(frame)) {
                    y = currentTimeLine * (int) cellHeight + marginTop;
                    g2d.setPaint(Color.PINK);
                    g2d.fillRect(x , y, (int) cellWidth - 1, (int) cellHeight);

                    g2d.setPaint(Color.BLACK);
                    g2d.drawRect(x , y, (int) cellWidth - 1, (int) cellHeight);

                }
                currentTimeLine++;
            }
        }

        // currentFrame
        int x = (int) (model.getCurrentFrame() * cellWidth)+ labelWidth;
        g2d.setStroke(thickStroke);

        g2d.setPaint(selectionColor);
        g2d.fillRect(x + 1, marginTop, (int) cellWidth - 1, (int) (height - marginBottom - marginTop));

        g2d.setPaint(Color.BLACK);
        g2d.drawRect(x + 1, marginTop, (int) cellWidth - 1, (int) (height - marginBottom - marginTop));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentTool.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentTool.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentTool.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
