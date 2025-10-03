package mathapp.gui;


import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {
    public SideBar() {
        setPreferredSize(new Dimension(150, 600));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
