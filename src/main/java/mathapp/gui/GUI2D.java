package mathapp.gui;

import mathapp.objects.twoD.Axis2D;
import mathapp.objects.twoD.Function2D;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
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

    private static ArrayList<Function2D> functions = new ArrayList<>();

    public GUI2D() {
        scene = new Scene2D();
        scene.add(new Axis2D(tickSpacing));
        scene.add(new Function2D(x -> Math.sin(x), Color.RED));
        scene.add(new Function2D(x -> Math.cos(x), Color.BLUE));

        setupFrame();
        setupMouseControls();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Optional: enable anti-aliasing for smoother lines
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
        frame.add(this);        // add this JPanel
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
