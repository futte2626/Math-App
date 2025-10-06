package mathapp.gui;

import mathapp.AppCore;
import mathapp.commandHandlers.CommandParser;
import mathapp.objects.twoD.Scene2D;

import javax.swing.*;
import java.awt.*;

public class CommandLine extends JPanel {
    CommandParser parser = new CommandParser();
    JTextField cmdField = new JTextField();
    private GUI2D gui2D;
    private Scene2D scene;

    public CommandLine() {
        this.gui2D = AppCore.gui2D;
        this.scene = AppCore.scene2D;

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
