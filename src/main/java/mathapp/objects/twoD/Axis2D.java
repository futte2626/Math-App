package mathapp.objects.twoD;

import java.awt.*;
import java.awt.geom.Point2D;

public class Axis2D implements Drawable2D{
    private static int tickSpacing;
    private String axisName;
    private Color color;
    boolean visible;
    private double tickValue = 1.0;   // how much each tick represents (1, 0.5, 0.2, ...)
    private final int desiredPixelSpacing = 100;

    public Axis2D(int tickSpacing) {
        this.tickSpacing = tickSpacing;
        visible = true;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void draw(Graphics2D g2d, int scale, Point2D.Double origin) {
        if(!visible){return;}
        updateTickSpacing(scale);
        // Draw axes
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;



        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) -origin.x, 0, (int) (width-origin.x), 0); // X-axis
        g2d.scale(1, -1);
        g2d.drawLine(0, (int) -origin.y, 0, (int) (height - origin.y)); // Y-axis
        g2d.scale(1, -1);

        // Draw X-axis ticks and labels
        int tickLength = 7;
        g2d.scale(1, -1);
        for (int x = (int) -origin.x; x < width-origin.x; x++) {
            if(x==0) continue;
            if(x % tickSpacing*tickValue == 0){
                g2d.drawLine(x, -tickLength, x, tickLength);
                String nmbString = String.format("%.2f", x / (double)scale);
                g2d.drawString(nmbString, x-3, 20);

            }
        }
        g2d.scale(1, -1);

        // Draw Y-axis ticks and labels
        int bottomPixel = (int) -(height- origin.y); // Bottom of screen
        int topPixel = (int) origin.y; //Top of screen

        for (int y = bottomPixel; y <= topPixel; y++ ) {
            if (y == 0) continue;
            if (y % tickSpacing*tickValue == 0) {
                g2d.drawLine(-tickLength, y, tickLength, y);
                String nmbString = String.format("%.2f", y / (double)scale);
                g2d.scale(1, -1);
                g2d.drawString(nmbString, -35, -y+7);
                g2d.scale(1, -1);
            }
        }
    }


    public Color getColor() {
        return color;
    }
    public String getName() { return axisName; }
    public void setVisible(boolean visible) { this.visible = visible; }

    private void updateTickSpacing(int scale) {
        double unitPerTick = desiredPixelSpacing / (double) scale;

        // Start from 1 as the base value
        double value = 1.0;
        tickValue = value; // default
        if(0.7<=unitPerTick && unitPerTick<=1.3){
            tickValue = 1;
        }
        else if (unitPerTick < value) {
            // Zoom in (subdivide)
            while (true) {
                if (unitPerTick >= value/2) {
                    tickValue = value;
                    break;
                }

                value = nextNiceDown(value);
            }
        } else {
            // Zoom out (expand)
            while (true) {
                if (unitPerTick <= value*2) {
                    tickValue = value;
                    break;
                }

                value = nextNiceUp(value);
            }
        }

        tickSpacing = (int) (tickValue * scale);
    }

    private double nextNiceDown(double value) {
        double halved = value / 2.0;
        double exponent = Math.floor(Math.log10(halved));
        double mantissa = halved / Math.pow(10, exponent);

        if (mantissa >= 5.0) mantissa = 5.0;
        else if (mantissa >= 2.0) mantissa = 2.0;
        else mantissa = 1.0;

        return mantissa * Math.pow(10, exponent);
    }

    // Double and snap up to nearest 1-2-5
    private double nextNiceUp(double value) {
        double doubled = value * 2.0;
        double exponent = Math.floor(Math.log10(doubled));
        double mantissa = doubled / Math.pow(10, exponent);

        if (mantissa <= 1.0) mantissa = 1.0;
        else if (mantissa <= 2.0) mantissa = 2.0;
        else mantissa = 5.0;

        return mantissa * Math.pow(10, exponent);
    }
}
