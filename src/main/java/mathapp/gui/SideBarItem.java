package mathapp.gui;

import mathapp.objects.twoD.Function2D;
import mathapp.parser.ShuntingYard;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

public class SideBarItem extends JPanel {
    private final Function2D function;
    private final String infoText;

    // keep a reference so we can change the label text
    private final JButton hideBtn;

    public SideBarItem(Function2D function) {
        this.function = function;
        this.infoText = function.getName();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---------- formula row (left-aligned) ----------
        JPanel formulaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        formulaRow.setOpaque(false);

        String[] parts = infoText != null ? infoText.split("=") : new String[0];
        if (parts.length >= 2) {
            String lhs = parts[0].trim();
            String rhs = parts[1].trim();
            String latexText = ShuntingYard.toLatex(rhs);

            TeXFormula formula = new TeXFormula("$" + lhs + "=" + latexText + "$");
            TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 18);
            JLabel label = new JLabel(icon);
            formulaRow.add(label);
        }
        add(formulaRow);
        add(Box.createRigidArea(new Dimension(0, 6)));

        // ---------- controls row (left-aligned) ----------
        JPanel controlRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        controlRow.setOpaque(false);

        JButton delBtn = makeUtilButton("Del", 50);
        hideBtn = makeUtilButton(function.isVisible() ? "Hide" : "Show", 55);

        // Wire actions
        delBtn.addActionListener(e ->
                // tell the outside world the user wants to delete this function
                firePropertyChange("deleteFunction", null, function)
        );

        hideBtn.addActionListener(e -> {
            // Toggle visibility on the SAME Function2D instance
            boolean newVisible = !function.isVisible();
            function.setVisible(newVisible);

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
            function.setColor(color);
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

    public Function2D getFunction() { return function; }
    public String getText() { return infoText; }
}
