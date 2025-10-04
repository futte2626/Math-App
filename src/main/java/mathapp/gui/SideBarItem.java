package mathapp.gui;

import javax.swing.*;
import java.awt.*;

public class SideBarItem extends JPanel {
    public SideBarItem(String title, int index) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.lightGray);

        JLabel label = new JLabel(title);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JButton("Edit " + index));
        buttonPanel.add(new JButton("Remove " + index));

        add(buttonPanel, BorderLayout.CENTER);
    }
}
