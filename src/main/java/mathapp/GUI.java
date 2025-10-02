package mathapp;

import javax.swing.*;

public class GUI {
    public void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Math App");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);

    }


}
