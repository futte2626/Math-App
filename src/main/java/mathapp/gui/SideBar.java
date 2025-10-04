package mathapp.gui;


import mathapp.objects.twoD.Drawable2D;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SideBar extends JPanel {
    private JScrollPane scrollPane;

    public SideBar(GUI2D gui2D, Scene2D scene) {
        setPreferredSize(new Dimension(200, gui2D.getPreferredSize().height));
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 5));
        for(int i = 0; i<30;i++) buttonPanel.add(new SideBarItem("This is a test ", i));

        scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200, scrollPane.getHeight()));
        add(scrollPane, BorderLayout.WEST);
    }
}
