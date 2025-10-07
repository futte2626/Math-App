package mathapp.gui.SidebarItems;

import mathapp.objects.twoD.Drawable2D;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractSidebarItem extends JPanel {
    protected final JButton hideButton;
    protected final JButton delButton;

    protected AbstractSidebarItem() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // formula row
        add(buildFormulaRow());
        add(Box.createRigidArea(new Dimension(0, 6)));

        // control row
        JPanel controlRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        controlRow.setOpaque(false);

        delButton = makeUtilButton("Del", 50);
        hideButton = makeUtilButton("Hide", 55);

        delButton.addActionListener(e -> onDelete());
        hideButton.addActionListener(e -> onToggleVisibility());

        controlRow.add(delButton);
        controlRow.add(hideButton);
        addControls(controlRow);

        add(controlRow);

        // fix size
        int h = controlRow.getPreferredSize().height
                + (getComponentCount() > 1 ? getComponent(0).getPreferredSize().height : 0)
                + 10;
        int w = 245;
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        return;
    }

    protected abstract JPanel buildFormulaRow();
    protected abstract void onDelete();
    protected abstract void onToggleVisibility();
    protected abstract void addControls(JPanel controlRow);
    public abstract boolean represents(Drawable2D obj);

    protected JButton makeUtilButton(String text, int w) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(w, 25));
        btn.setMaximumSize(new Dimension(w, 25));
        btn.setMinimumSize(new Dimension(w, 25));
        btn.setFocusPainted(false);
        return btn;
    }
}
