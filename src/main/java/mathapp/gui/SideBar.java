package mathapp.gui;

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
    private final JPanel graphPanel; // panel where Scene2D is drawn

    public SideBar(JPanel graphPanel) {
        this.graphPanel = graphPanel;

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

    private void addItem(Function2D func) {
        SideBarItem item = new SideBarItem(func);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Listen for color changes
        item.addPropertyChangeListener("functionColor", evt -> {
            graphPanel.repaint(); // redraw when function color changes
        });

        // Handle "Del" button
        item.addPropertyChangeListener("deleteFunction", evt -> {
            // remove from sidebar
            items.remove(item);
            contentPanel.remove(item);
            contentPanel.revalidate();
            contentPanel.repaint();

            // also remove from Scene2D
            if (evt.getNewValue() instanceof Scene2D scene) {
                scene.remove(func);
            }

            graphPanel.repaint();
        });

        // Handle "Hide" button
        item.addPropertyChangeListener("toggleVisibility", evt -> {
            func.setVisible(!func.isVisible());
            graphPanel.repaint();
        });

        items.add(item);
        contentPanel.add(item);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5))); // spacing
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    @Override
    public void objectAdded(Drawable2D obj) {
        if (obj instanceof Function2D func && func.getName() != null && !func.getName().isEmpty()) {
            addItem(func);
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
