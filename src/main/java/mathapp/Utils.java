package mathapp;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static void DeleteAfter(Component component, int delayMs) {
        Timer timer = new Timer(delayMs, e -> {
            Container parent = component.getParent();
            if (parent != null) {
                parent.remove(component);
                parent.revalidate();
                parent.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}