package mathapp.gui.SidebarItems;

import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.math.Function2D;
import mathapp.parser.ShuntingYard;
import org.scilab.forge.jlatexmath.TeXFormula;

import javax.swing.*;
import java.awt.*;

public class SidebarItem extends JPanel {
    private Object obj;
    private String infoString;

    public SidebarItem(Object obj) {
        this.obj = obj;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel formulaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        formulaRow.setOpaque(false);
        String latexText = "";
        JPanel cRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (obj instanceof Drawable2D drawable) {
            if(drawable instanceof Function2D function) {
                infoString = function.getName();
                cRow = FunctionItem(function);
                String[] parts = infoString != null ? infoString.split("=") : new String[0];
                if(parts.length!=2) return;
                String lhs = parts[0].trim();
                String rhs = parts[1].trim();
                latexText = ShuntingYard.toLatex(rhs,function.getVariable());
                TeXFormula formula = new TeXFormula("$" + lhs + "=" + latexText + "$");
                JLabel label = new JLabel(formula.createTeXIcon(TeXFormula.SERIF, 18));
                formulaRow.add(label);
                System.out.println("test");
            }
        }

        add(Box.createRigidArea(new Dimension(0, 6)));
        this.add(formulaRow);
        this.add(cRow);
    }

    private JButton makeColorButton(Color color){
        if(obj instanceof Drawable2D) {
            Drawable2D drawable = (Drawable2D) obj;
            JButton btn = new JButton();
            btn.setBackground(color);
            btn.setPreferredSize(new Dimension(25, 25));
            btn.setMaximumSize(new Dimension(25, 25));
            btn.setMinimumSize(new Dimension(25, 25));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.black));

            btn.addActionListener(e -> {
                drawable.setColor(color);
                // Let listeners know the color changed so they can repaint
                firePropertyChange("functionColor", null, color);
            });
            return btn;
        }
        return null;
    }

    private JButton makeUtilButton(String text, int w) {
        if(obj instanceof Drawable2D) {
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(w, 25));
            btn.setMaximumSize(new Dimension(w, 25));
            btn.setMinimumSize(new Dimension(w, 25));
            btn.setFocusPainted(false);
            return btn;
        }
        return null;
    }

    public Object getObject() { return obj; }
    public String getText() { return infoString; }

    public JPanel FunctionItem(Function2D function) {
        JPanel controlRow = new JPanel();
        controlRow.setLayout(new BoxLayout(controlRow, BoxLayout.X_AXIS));
        controlRow.setOpaque(false);

        // Control buttons
        JButton delBtn = makeUtilButton("Del", 50);
        JButton hideBtn = makeUtilButton("Hide", 60);
        hideBtn.addActionListener(e -> {
            boolean newVisible = !function.isVisible();
            function.setVisible(newVisible);
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
        controlRow.add(makeColorButton(Color.pink));

        add(controlRow);

        // Fix size so items don't expand to fill the whole sidebar
        int h = controlRow.getPreferredSize().height
                + (getComponentCount() > 1 ? getComponent(0).getPreferredSize().height : 0)
                + 10;
        int w = 245;
        setMinimumSize(new Dimension(w, h));
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        return controlRow;
    }
}
