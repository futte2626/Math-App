package mathapp.gui.SidebarItems;

import org.scilab.forge.jlatexmath.TeXFormula;

import javax.swing.*;
import java.awt.*;

public class SidebarItemSetup extends JPanel {
    public static void setupDefaults(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setBackground(Color.white);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static JPanel makeFormulaRow(String latex) {
        JPanel formulaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        formulaRow.setOpaque(false);

        TeXFormula formula = new TeXFormula(latex);
        JLabel label = new JLabel(formula.createTeXIcon(TeXFormula.SERIF, 18));
        formulaRow.add(label);

        return formulaRow;
    }

    public static void addSpacer(JPanel panel, int height) {
        panel.add(Box.createRigidArea(new Dimension(0, height)));
    }
}
