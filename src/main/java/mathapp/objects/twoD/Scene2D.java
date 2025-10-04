package mathapp.objects.twoD;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Scene2D {
    private List<Drawable2D> objects = new ArrayList<>();

    public void add(Drawable2D obj) {
        objects.add(obj);
    }

    public void remove(Drawable2D obj) {
        objects.remove(obj);
    }

    public void clear() {
        objects.clear();
    }

    public List<Drawable2D> getObjects() {
        return new ArrayList<>(objects);
    }

    public void drawAll(Graphics2D g2d, int scale, Point2D.Double origin) {
        for (Drawable2D obj : objects) {
            if (obj.isVisible()) {
                obj.draw(g2d, scale, origin);
            }
        }
    }
}
