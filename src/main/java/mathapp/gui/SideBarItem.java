package mathapp.gui;

import javax.swing.*;
import java.awt.*;

public class SideBarItem extends JPanel {
    private String name;
    public SideBarItem(String title) {
        this.name = title;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.lightGray);


        JLabel label = new JLabel(title);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JButton("Edit "));
        buttonPanel.add(new JButton("Remove "));

        add(buttonPanel, BorderLayout.CENTER);
    }

    public String getText() {
        return name;
    }

}
