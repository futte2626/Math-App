package mathapp;

import mathapp.gui.CommandLine;
import mathapp.gui.GUI2D;
import mathapp.gui.SideBar;
import mathapp.objects.twoD.Axis2D;
import mathapp.objects.twoD.Scene2D;
import com.formdev.flatlaf.FlatLightLaf;
import mathapp.objects.twoD.math.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Main {

    public static void main(String[] args) {
        String testContains = "Does this word contain something";

        // Chooses default font and theme
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
            UIManager.put("defaultFont", new Font("Times New Roman", Font.PLAIN, 12));
        } catch( Exception ex ) {System.err.println( ex.getMessage());}

        //Creates scene and GUI's
        AppCore.scene2D = new Scene2D();
        AppCore.gui2D = new GUI2D(new Point2D.Double(400, 300), 150);
        AppCore.sidebar = new SideBar();
        AppCore.cmdLine = new CommandLine();

        // Adds fundamentals to scene
        AppCore.scene2D.addListener(AppCore.sidebar);
        AppCore.scene2D.add(new Axis2D(100));
        Vector2D v1 =new Vector2D(2, -2, "a");
        AppCore.scene2D.add(v1);



        // Creates JFrame
        JFrame frame = new JFrame("MathApp 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(AppCore.cmdLine, BorderLayout.SOUTH);
        frame.add(AppCore.sidebar, BorderLayout.WEST);
        frame.add(AppCore.gui2D, BorderLayout.CENTER);
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
