package mathapp.objects.twoD;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;

import java.awt.geom.Point2D;
import java.util.function.Function;

public class Function2D implements Drawable2D {
    private Function<Double, Double> function; // the actual f(x)
    private Color color;
    private boolean visible;
    private String functionText;

    public Function2D(Function<Double, Double> function, Color color, String text) {
        this.function = function;
        this.color = color;
        this.functionText = text;
        this.visible = true;
    }

    public double eval(double x) {
        return function.apply(x);
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) { this.color = color;}
    public String getName() { return functionText; }
    public Function<Double, Double> getFunction() { return function; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public boolean isVisible() {
        return visible;
    }


    public void draw(Graphics2D g2d, int scale, Point2D.Double origin) {
        if (!visible) return;

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        int prevX = (int) -origin.x;
        int prevY = (int) (eval(prevX / (double) scale) * scale);

        for (int x = prevX + 1; x < g2d.getClipBounds().width - origin.x; x++) {
            double evalfed = (eval(x / (double) scale) * scale);
            int y = (int)evalfed;
            if(Double.isNaN(evalfed) || Double.isInfinite(evalfed)){
                prevX = x;
                prevY = y;
                continue;
            }
            g2d.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }
    }

}
