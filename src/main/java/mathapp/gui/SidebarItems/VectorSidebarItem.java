package mathapp.gui.SidebarItems;

import mathapp.gui.SidebarItems.AbstractSidebarItem;
import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.math.Function2D;
import mathapp.objects.twoD.math.Vector2D;
import org.scilab.forge.jlatexmath.TeXFormula;

import javax.swing.*;
import java.awt.*;

public class VectorSidebarItem extends JPanel {
    private Vector2D vector;
    private String name;
    private JButton btn;

    public VectorSidebarItem(Vector2D vector) {
        this.vector = vector;
        this.name = vector.getName();

        SidebarItemSetup.setupDefaults(this);

        String latex = "$\\vec{" + vector.getName() + "}=<" + vector.getX() + "," + vector.getY() + ">$";
        add(SidebarItemSetup.makeFormulaRow(latex));

        SidebarItemSetup.addSpacer(this, 6);

        JPanel controlRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        controlRow.setOpaque(false);

        JButton delBtn = makeUtilButton("Del", 50);
        JButton hideBtn = makeUtilButton(vector.isVisible() ? "Hide" : "Show", 55);

        // Wire actions
        delBtn.addActionListener(e ->
                // tell the outside world the user wants to delete this function
                firePropertyChange("deleteVector", null, vector)
        );

        hideBtn.addActionListener(e -> {
            // Toggle visibility on the SAME Function2D instance
            boolean newVisible = !vector.isVisible();
            vector.setVisible(newVisible);

            // Update button text
            hideBtn.setText(newVisible ? "Hide" : "Show");

            // Notify any PropertyChangeListeners registered on this component
            // name  = "toggleVisibility"
            // old   = previous value (opposite of newVisible)
            // new   = new value (newVisible)
            firePropertyChange("toggleVisibility", !newVisible, newVisible);
        });

        controlRow.add(delBtn);
        controlRow.add(hideBtn);

        // Color buttons
        controlRow.add(makeColorButton(Color.red));
        controlRow.add(makeColorButton(Color.green));
        controlRow.add(makeColorButton(Color.blue));
        controlRow.add(makeColorButton(Color.cyan));

        add(controlRow);

        // Fix size so items don't expand to fill the whole sidebar
        int h = controlRow.getPreferredSize().height
                + (getComponentCount() > 1 ? getComponent(0).getPreferredSize().height : 0)
                + 10;
        int w = 245;
        setMinimumSize(new Dimension(w, h));
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
    }

    private JButton makeColorButton(Color color) {
        JButton btn = new JButton();
        btn.setBackground(color);
        btn.setPreferredSize(new Dimension(25, 25));
        btn.setMaximumSize(new Dimension(25, 25));
        btn.setMinimumSize(new Dimension(25, 25));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.black));

        btn.addActionListener(e -> {
            vector.setColor(color);
            // Let listeners know the color changed so they can repaint
            firePropertyChange("functionColor", null, color);
        });

        return btn;
    }

    private JButton makeUtilButton(String text, int w) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(w, 25));
        btn.setMaximumSize(new Dimension(w, 25));
        btn.setMinimumSize(new Dimension(w, 25));
        btn.setFocusPainted(false);
        return btn;
    }

    public Vector2D getVector() { return vector; }
}
