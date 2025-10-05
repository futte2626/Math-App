package mathapp.gui;

import mathapp.AppCore;
import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.Function2D;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SideBar extends JPanel implements SceneListener {
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    private final ArrayList<SideBarItem> items;
    private final JPanel gui2D; // panel where Scene2D is drawn
    private Scene2D scene;

    public SideBar() {
        this.gui2D = AppCore.gui2D;
        this.scene = AppCore.scene2D;

        setSize(250, gui2D.getHeight());
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 1000));

        // Panel inside scrollpane
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // padding

        scrollPane = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // remove default border
        add(scrollPane, BorderLayout.CENTER);

        items = new ArrayList<>();
    }

    public void AddItem(Function2D func) {
        SideBarItem item = new SideBarItem(func);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- hook up listeners ---
        item.addPropertyChangeListener("toggleVisibility", evt -> {
            // function visibility already toggled inside item
            gui2D.repaint();
        });

        item.addPropertyChangeListener("functionColor", evt -> {
            gui2D.repaint();
        });

        item.addPropertyChangeListener("deleteFunction", evt -> {
            scene.remove(func); // remove from scene
            items.remove(item);
            contentPanel.remove(item);
            contentPanel.revalidate();
            contentPanel.repaint();
            gui2D.repaint();
        });

        items.add(item);
        contentPanel.add(item);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    @Override
    public void objectAdded(Drawable2D obj) {
        if (obj instanceof Function2D func && func.getName() != null && !func.getName().isEmpty()) {
            AddItem(func);
        }
    }

    @Override
    public void objectRemoved(Drawable2D obj) {
        SideBarItem toRemove = null;
        for (SideBarItem item : items) {
            if (item.getFunction().equals(obj)) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            items.remove(toRemove);
            contentPanel.remove(toRemove);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
}
