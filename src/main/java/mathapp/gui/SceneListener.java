package mathapp.gui;

import mathapp.objects.twoD.Drawable2D;

public interface SceneListener {
    void objectAdded(Drawable2D obj);
    void objectRemoved(Drawable2D obj);
}
