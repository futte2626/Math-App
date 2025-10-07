package mathapp.objects.twoD;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Point2D;

public interface Drawable2D {
    void draw(Graphics2D g2d, int scale, Point2D.Double origin);
    boolean isVisible();
    void setVisible(boolean visible);

    String getName();
    Color getColor();
    void setColor(Color color);
}
