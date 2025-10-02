package mathapp;

import java.awt.*;
import java.util.function.Function;

public class Function2D {
    private Function<Double, Double> function; // the actual f(x)
    private Color color;
    public boolean visible;

    public Function2D(Function<Double, Double> function, Color color) {
        this.function = function;
        this.color = color;
        this.visible = true;
    }

    public double eval(double x) {
        return function.apply(x);
    }

    public Color getColor() {
        return color;
    }
}
