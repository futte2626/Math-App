package mathapp.commandHandlers;

import mathapp.AppCore;
import mathapp.objects.twoD.math.Function2D;
import mathapp.parser.ShuntingYard;

import java.awt.*;
import java.util.function.Function;

public class FunctionCommand implements Command {
    @Override
    public void execute(String input) {
        try {
            //Splits the inputs into the arguments
            String[] args = CommandUtils.getArgs(input);
            // A function needs 3 args, the function itself, the variable and the name
            if(args.length != 3){
                System.out.println("Wrong number of arguments");
                return;
            }
            String expression = args[0];
            String variable = args[1];
            String name = args[2];

            Function<Double,Double> evaluator = ShuntingYard.parseFunction(expression, variable);
            String funcDef = name+"("+variable+")="+expression;
            Function2D func = new Function2D(evaluator,variable,Color.red,funcDef);

            AppCore.scene2D.add(func);



        } catch (Exception e) {
            System.err.println("Invalid function command");
        }
    }
}
