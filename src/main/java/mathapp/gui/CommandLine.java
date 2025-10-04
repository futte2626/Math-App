package mathapp.gui;

import mathapp.commandHandlers.CommandParser;

import javax.swing.*;
import java.awt.*;

import static mathapp.gui.GUI2D.scene;

public class CommandLine extends JPanel {
    CommandParser parser = new CommandParser();
    JTextField cmdField = new JTextField();
    private GUI2D gui2D;

    public CommandLine(GUI2D gui2D) {
        this.gui2D = gui2D;
        setPreferredSize(new Dimension(650, 25));
        setBackground(Color.lightGray);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        cmdField.setEditable(true);
        cmdField.setSize(new Dimension(650, 25));

        cmdField.addActionListener(e -> {
            String text = cmdField.getText();
            parser.parseAndExecute(text, scene);
            cmdField.setText("");
            gui2D.repaint();
            repaint();
        });

        add(cmdField);
    }
}
