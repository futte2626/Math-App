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

    public SideBarItem(Function2D function) {
        this.function = function;
        this.infoText = function.getName();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---------- Top: formula, forced left ----------
        JPanel formulaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        formulaRow.setOpaque(false);

        JLabel latexLabel = null;
        String[] parts = infoText != null ? infoText.split("=") : new String[0];
        if (parts.length >= 2) {
            String lhs = parts[0].trim();
            String rhs = parts[1].trim();
            String latexText = ShuntingYard.toLatex(rhs);

            TeXFormula formula = new TeXFormula("$" + lhs + "=" + latexText + "$");
            TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 18);
            latexLabel = new JLabel(icon);
            formulaRow.add(latexLabel);
        }
        add(formulaRow);
        add(Box.createRigidArea(new Dimension(0, 6)));

        // ---------- Controls: left-aligned single row ----------
        JPanel controlRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        controlRow.setOpaque(false);

        JButton delBtn  = makeUtilButton("Del", 50);
        JButton hideBtn = makeUtilButton("Hide", 60);

        // actions
        delBtn.addActionListener(e ->
                firePropertyChange("deleteFunction", null, function));

        hideBtn.addActionListener(e -> {
            boolean newVisible = !function.isVisible();
            function.setVisible(newVisible);
            hideBtn.setText(newVisible ? "Hide" : "Show");
            firePropertyChange("toggleVisibility", !newVisible, newVisible);
        });

        controlRow.add(delBtn);
        controlRow.add(hideBtn);

        controlRow.add(makeColorButton(Color.red));
        controlRow.add(makeColorButton(Color.green));
        controlRow.add(makeColorButton(Color.cyan));
        controlRow.add(makeColorButton(Color.blue));

        add(controlRow);

        // ---------- Fix the component size (no vertical stretching) ----------
        int labelH = (latexLabel != null) ? latexLabel.getPreferredSize().height : 0;
        int controlsH = controlRow.getPreferredSize().height;
        int totalH = labelH + 6 + controlsH + 6; // small padding
        int width = 250;                          // your sidebar width

        setMinimumSize(new Dimension(width, totalH));
        setPreferredSize(new Dimension(width, totalH));
        setMaximumSize(new Dimension(width, totalH)); // fixed height -> won't fill half the bar
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

    public String getText() {
        return infoText;
    }

    public Function2D getFunction() {
        return function;
    }
}
