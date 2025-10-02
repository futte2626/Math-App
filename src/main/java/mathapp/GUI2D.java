package mathapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GUI2D extends JPanel {

    private JLabel label;
    private int scale = 100;
    private Point2D.Double origin = new Point2D.Double(400, 300);
    private boolean dragging = false;
    private int dragPosX, dragPosY;
    int tickSpacing = 50;

    private ArrayList<Function2D> functions = new ArrayList<>();

    Function2D parabola = new Function2D(x -> x*x - 2*x + 1, Color.RED);
    Function2D linear = new Function2D(x -> 2*x + 5, Color.BLUE);
    Function2D sinFunc = new Function2D(Math::sin, Color.GREEN);
    Function2D exponential = new Function2D(Math::exp, Color.ORANGE);

    public GUI2D() {
        sinFunc.visible = false; // hide sine initially
        functions.add(linear);
        functions.add(parabola);
        functions.add(sinFunc);
        functions.add(exponential);

        JFrame frame = new JFrame("MathApp 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        frame.add(this);
        frame.setVisible(true);

        // Dragging origin
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

        // Zoom with mouse wheel
        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            if (notches < 0) scale += 10;
            else scale = Math.max(10, scale - 10);
            repaint();
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("G"), "goToOrigin");
        getActionMap().put("goToOrigin", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Example: go to coordinate (2, 3)
                goToCoord(0, 0);
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Serif", Font.PLAIN, 14));

        AffineTransform originalTransform = g2d.getTransform();

        // Translate origin and flip Y-axis
        g2d.translate(origin.x, origin.y);
        g2d.scale(1, -1);

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) -origin.x, 0, (int) (getWidth()-origin.x), 0); // X-axis
        g2d.scale(1, -1);
        g2d.drawLine(0, (int) -origin.y, 0, (int) (getHeight() - origin.y)); // Y-axis
        g2d.scale(1, -1);

        // Draw X-axis ticks and labels
        int tickLength = 7;
        g2d.scale(1, -1);
        for (int x = (int) -origin.x; x < getWidth()-origin.x; x++) {
            if(x==0) continue;
            if((x) % tickSpacing == 0){
                g2d.drawLine(x, -tickLength, x, tickLength);

                String nmbString = String.format("%.2f", x / (double)scale);
                g2d.drawString(nmbString, x-3, 20);

            }
        }
        g2d.scale(1, -1);

        // Draw Y-axis ticks and labels
        int bottomPixel = (int)-(getHeight() - origin.y); // Bottom of screen
        int topPixel = (int)(origin.y); //Top of screen

        for (int y = bottomPixel; y <= topPixel; y++ ) {
            if (y == 0) continue;
            if (y % tickSpacing == 0) {
                g2d.drawLine(-tickLength, y, tickLength, y);
                String nmbString = String.format("%.2f", y / (double)scale);
                g2d.scale(1, -1);
                g2d.drawString(nmbString, -35, -y+7);
                g2d.scale(1, -1);
            }
        }



        // Draw functions
        for (Function2D func : functions) {
            if (!func.visible) continue;
            g2d.setColor(func.getColor());
            g2d.setStroke(new BasicStroke(3));

            int prevX = - (int) origin.x;
            int prevY = (int) (func.eval(prevX / (double) scale) * scale);

            for (int x = prevX + 1; x < getWidth() - (int) origin.x; x++) {
                int y = (int) (func.eval(x / (double) scale) * scale);
                g2d.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            }
        }
    }

    public void goToCoord(double x, double y) {
        // x and y are mathematical coordinates
        // We want to center these coordinates on the screen

        // Calculate the center of the panel in pixels
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        // Convert mathematical coordinates to pixel coordinates for the origin
        // For x: mathematical x=0 should be at origin.x
        // For y: mathematical y=0 should be at origin.y (but remember Y is flipped)

        // To center on (x,y):
        // - mathematical x=0 should be at (centerX - x * scale)
        // - mathematical y=0 should be at (centerY + y * scale) because Y is flipped

        origin.x = centerX - x * scale;
        origin.y = centerY + y * scale;

        repaint();
    }
}
