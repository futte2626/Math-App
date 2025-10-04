package mathapp.gui;

import mathapp.objects.twoD.Axis2D;
import mathapp.objects.twoD.Function2D;
import mathapp.objects.twoD.Scene2D;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class SetupJFrame {
    public static Scene2D scene = new Scene2D();

    public SetupJFrame() {
        try {
            Font latexLike = new Font("Times New Roman", Font.PLAIN, 12);
            UIManager.setLookAndFeel( new FlatLightLaf() );
            UIManager.put("defaultFont", latexLike);
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        Point2D.Double origin = new Point2D.Double(400, 300);
        int scale = 150;
        scene.add(new Axis2D(100));
        scene.add(new Function2D(x -> Math.sin(x), Color.RED));
        scene.add(new Function2D(x -> Math.cos(x), Color.BLUE));

        //Create panels
        GUI2D drawingPanel = new GUI2D(scene, origin, scale);
        SideBar sidebar = new SideBar(drawingPanel, scene);
        CommandLine cmdLine = new CommandLine(drawingPanel);

        // Layout
        JFrame frame = new JFrame("MathApp 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(cmdLine, BorderLayout.SOUTH);
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
