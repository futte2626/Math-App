package mathapp.objects.twoD;

import mathapp.gui.SceneListener;
import mathapp.gui.SideBar;
import mathapp.gui.SideBarItem;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Scene2D {
    private List<Drawable2D> objects = new ArrayList<>();
    private List<SceneListener> listeners = new ArrayList<>();


    public void addListener(SceneListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SceneListener listener) {
        listeners.remove(listener);
    }

    public void add(Drawable2D obj) {
        objects.add(obj);
        listeners.forEach(l -> l.objectAdded(obj));
    }

    public void remove(Drawable2D obj) {
        objects.remove(obj);
        listeners.forEach(l -> l.objectRemoved(obj));
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
