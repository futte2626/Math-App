package mathapp.commandHandlers;

import mathapp.objects.twoD.Function2D;
import mathapp.objects.twoD.Scene2D;
import mathapp.parser.ShuntingYard;

import java.awt.*;
import java.util.function.Function;

public class FunctionCommand implements Command {

    @Override
    public void execute(String input, Scene2D scene) {
        try {
            String[] parts = input.split("=");
            if(parts.length < 2) return;

            String expr=parts[1].trim();
            Function<Double, Double> evaluator = ShuntingYard.parse(expr);
            Function2D func = new Function2D(evaluator, Color.red);
            scene.add(func);

        }
        catch (Exception e) {
            System.err.println("Invalid function command");
        }
    }
}
