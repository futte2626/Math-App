package mathapp.gui;


import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.Scene2D;
import mathapp.parser.ShuntingYard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SideBar extends JPanel implements SceneListener {
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    private final ArrayList<SideBarItem> items;

    public SideBar() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 1000));

        // Panel inside scrollpane
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // top padding

        scrollPane = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // remove extra borders
        add(scrollPane, BorderLayout.CENTER);

        items = new ArrayList<>();
    }

    public void AddItem(String text) {
        SideBarItem item = new SideBarItem(text);
      //  item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400)); // fixed height, stretch width
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        items.add(item);
        contentPanel.add(item);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5))); // spacing
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void RemoveItem(Drawable2D obj) {
        SideBarItem toRemove = null;
        for (SideBarItem item : items) {
            if (item.getName().equals(obj.getName())) {
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

    @Override
    public void objectAdded(Drawable2D obj) {
        if (obj.getName() != null && !obj.getName().isEmpty()) {
            AddItem(obj.getName());
        }
    }

    @Override
    public void objectRemoved(Drawable2D obj) {
        RemoveItem(obj);
    }
}
