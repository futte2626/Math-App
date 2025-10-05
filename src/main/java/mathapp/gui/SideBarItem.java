package mathapp.gui;

import mathapp.parser.ShuntingYard;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

public class SideBarItem extends JPanel {
    private final String infoText;

    public SideBarItem(String text) {
        this.infoText = text;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);

        // Split into LHS and RHS
        String[] parts = text.split("=");
        if (parts.length < 2) return;
        String lhs = parts[0].trim();
        String rhs = parts[1].trim();

        // Convert RHS to LaTeX
        String latexText = ShuntingYard.toLatex(rhs);

        // Render formula (slightly smaller)
        TeXFormula formula = new TeXFormula("$" + lhs + "=" + latexText + "$");
        TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 18);

        JLabel label = new JLabel(icon);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Control row ---
        JPanel controlRow = new JPanel();
        controlRow.setLayout(new BoxLayout(controlRow, BoxLayout.X_AXIS));
        controlRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons (smaller)
        controlRow.add(makeCompactButton("Del", 50, 25));
        controlRow.add(Box.createRigidArea(new Dimension(4, 0)));
        controlRow.add(makeCompactButton("Hide", 55, 25));
        controlRow.add(Box.createRigidArea(new Dimension(8, 0)));

        // Color buttons (smaller squares)
        controlRow.add(makeColorButton(Color.red));
        controlRow.add(Box.createRigidArea(new Dimension(4, 0)));
        controlRow.add(makeColorButton(Color.green));
        controlRow.add(Box.createRigidArea(new Dimension(4, 0)));
        controlRow.add(makeColorButton(Color.cyan));
        controlRow.add(Box.createRigidArea(new Dimension(4, 0)));
        controlRow.add(makeColorButton(Color.blue));

        // Ensure row fits
        controlRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // Add everything
        add(label);
        add(Box.createRigidArea(new Dimension(0, 6))); // spacing
        add(controlRow);

        // --- Fix item height ---
        int height = icon.getIconHeight() + 50;
        setPreferredSize(new Dimension(225, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height)); // full width, fixed height
    }

    private JButton makeCompactButton(String text, int w, int h) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(w, h));
        btn.setMaximumSize(new Dimension(w, h));
        btn.setMinimumSize(new Dimension(w, h));
        btn.setFocusPainted(false);
        return btn;
    }

    private JButton makeColorButton(Color color) {
        JButton btn = new JButton();
        btn.setBackground(color);
        btn.setPreferredSize(new Dimension(25, 25));
        btn.setMaximumSize(new Dimension(25, 25));
        btn.setMinimumSize(new Dimension(25, 25));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.black));
        return btn;
    }

    public String getText() {
        return infoText;
    }
}
