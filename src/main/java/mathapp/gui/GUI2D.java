package mathapp.gui;

import mathapp.objects.twoD.Axis2D;
import mathapp.objects.twoD.Function2D;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GUI2D extends JPanel {
    public static Scene2D scene;

    private JLabel label;
    private int scale = 100;
    private Point2D.Double origin = new Point2D.Double(400, 300);
    private boolean dragging = false;
    private int dragPosX, dragPosY;
    int tickSpacing = 50;

    public GUI2D(Scene2D scene, Point2D.Double origin, int scale) {
        this.scene = scene;
        this.origin = origin;
        this.scale = scale;
        setupMouseControls(); // optional
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Anti-Aliasing for smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // Translate origin
        g2d.translate(origin.x, origin.y);
        g2d.scale(1, -1); // flip Y-axis

        // Draw everything in the scene
        scene.drawAll(g2d, scale, origin);
    }

    private void setupFrame() {
        JFrame frame = new JFrame("MathApp 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(this);  // add this JPanel
        setBackground(Color.white);
        frame.setVisible(true);
    }

    private void setupMouseControls() {
        // dragging origin
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragging = true;
                dragPosX = e.getX();
                dragPosY = e.getY();
            }
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    origin.x += e.getX() - dragPosX;
                    origin.y += e.getY() - dragPosY;
                    dragPosX = e.getX();
                    dragPosY = e.getY();
                    repaint();
                }
            }
        });

        // zoom with mouse wheel
        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            if (notches < 0) scale += 10;
            else scale = Math.max(10, scale - 10);
            repaint();
        });
    }
}
