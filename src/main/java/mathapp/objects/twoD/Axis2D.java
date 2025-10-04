package mathapp.objects.twoD;

import java.awt.*;
import java.awt.geom.Point2D;

public class Axis2D implements Drawable2D{
    private int tickSpacing;
    private String axisName;
    private Color color;
    boolean visible;

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
        // Draw axes
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;

        if(!visible){return;}
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
            if(x % tickSpacing == 0){
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
            if (y % tickSpacing == 0) {
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


}
