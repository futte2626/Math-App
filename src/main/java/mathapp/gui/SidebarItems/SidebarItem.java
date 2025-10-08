package mathapp.gui.SidebarItems;

import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.math.Function2D;
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

        if (obj instanceof Drawable2D drawable) {
            if(drawable instanceof Function2D function) {
                infoString = function.getName();
            }
        }
        System.out.println(obj.getClass());
        System.out.println(obj);
        System.out.println(infoString);


       // TeXFormula formula = new TeXFormula(latex);
      //  JLabel label = new JLabel(formula.createTeXIcon(TeXFormula.SERIF, 18));
     //   formulaRow.add(label);

        add(Box.createRigidArea(new Dimension(0, 6)));
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
}
