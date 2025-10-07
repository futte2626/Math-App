package mathapp.gui;

import mathapp.AppCore;
import mathapp.gui.SidebarItems.FunctionSidebarItem;
import mathapp.gui.SidebarItems.FunctionSidebarItem;
import mathapp.gui.SidebarItems.VectorSidebarItem;
import mathapp.gui.SidebarItems.VectorSidebarItem;
import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.Scene2D;
import mathapp.objects.twoD.math.Function2D;
import mathapp.objects.twoD.math.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SideBar extends JPanel implements SceneListener {
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    private final ArrayList<FunctionSidebarItem> functions;
    private final ArrayList<VectorSidebarItem> vectors;
    private final JPanel gui2D; // panel where Scene2D is drawn
    private final Scene2D scene;

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

        functions = new ArrayList<>();
        vectors = new ArrayList<>();
    }

    public void AddItem(Function2D func) {
        FunctionSidebarItem item = new FunctionSidebarItem(func);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- hook up listeners ---
        item.addPropertyChangeListener("toggleVisibility", evt -> gui2D.repaint());
        item.addPropertyChangeListener("functionColor", evt -> gui2D.repaint());

        item.addPropertyChangeListener("deleteFunction", evt -> {
            scene.remove(func); // remove from scene
            functions.remove(item);
            contentPanel.remove(item);
            contentPanel.revalidate();
            contentPanel.repaint();
            gui2D.repaint();
        });

        functions.add(item);
        contentPanel.add(item);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void AddItem(Vector2D vec) {
        VectorSidebarItem item = new VectorSidebarItem(vec);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- hook up listeners ---
        item.addPropertyChangeListener("toggleVisibility", evt -> gui2D.repaint());
        item.addPropertyChangeListener("functionColor", evt -> gui2D.repaint());

        item.addPropertyChangeListener("deleteVector", evt -> {
            scene.remove(vec); // remove from scene
            vectors.remove(item);
            contentPanel.remove(item);
            contentPanel.revalidate();
            contentPanel.repaint();
            gui2D.repaint();
        });

        vectors.add(item);
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
        if (obj instanceof Vector2D vec && vec.getName() != null && !vec.getName().isEmpty()) {
            AddItem(vec);
        }
    }

    @Override
    public void objectRemoved(Drawable2D obj) {
        // check functions
        FunctionSidebarItem funcToRemove = null;
        for (FunctionSidebarItem item : functions) {
            if (item.getFunction() == obj) {
                funcToRemove = item;
                break;
            }
        }
        if (funcToRemove != null) {
            functions.remove(funcToRemove);
            contentPanel.remove(funcToRemove);
            contentPanel.revalidate();
            contentPanel.repaint();
            return;
        }

        // check vectors
        VectorSidebarItem vecToRemove = null;
        for (VectorSidebarItem item : vectors) {
            if (item.getVector() == obj) {
                vecToRemove = item;
                break;
            }
        }
        if (vecToRemove != null) {
            vectors.remove(vecToRemove);
            contentPanel.remove(vecToRemove);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
}
