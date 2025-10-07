package mathapp.gui;

import mathapp.AppCore;
import mathapp.commandHandlers.CommandParser;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;

public class CommandLine extends JPanel {
    CommandParser parser = new CommandParser();
    JTextField cmdField = new JTextField();

    public CommandLine() {

        setPreferredSize(new Dimension(650, 25));
        setBackground(Color.lightGray);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        cmdField.setEditable(true);
        cmdField.setSize(new Dimension(650, 25));

        cmdField.addActionListener(e -> {
            String text = cmdField.getText();
            parser.parseAndExecute(text);
            cmdField.setText("");
            AppCore.gui2D.repaint();
            repaint();
        });

        add(cmdField);
    }
}
