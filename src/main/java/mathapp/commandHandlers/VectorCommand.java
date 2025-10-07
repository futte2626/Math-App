package mathapp.commandHandlers;


import mathapp.AppCore;
import mathapp.objects.twoD.math.Vector2D;

import java.util.Vector;
import java.util.regex.*;

public class VectorCommand implements Command {
    public void execute(String input) {
        try {
            String[] args = CommandUtils.getArgs(input);

            if(args.length != 3) {
                System.out.println("Wrong number of arguments. A vector need 3 arguments");
                return;
            }

            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            String name = args[2];
            Vector2D vec = new Vector2D(x, y, name);
            AppCore.scene2D.add(vec);
        } catch (NumberFormatException e) {
            System.out.println("The first two arguments must be a number");
        }
    }
}
