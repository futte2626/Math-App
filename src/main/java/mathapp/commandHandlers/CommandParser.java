package mathapp.commandHandlers;

import mathapp.objects.twoD.Scene2D;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private Map<String, Command> commands = new HashMap<>();

    public CommandParser() {
        register("f", new FunctionCommand());
    }

    public void register(String name, Command command) {
        commands.put(name.toLowerCase(), command);
    }

    public void parseAndExecute(String input, Scene2D scene) {
        input = input.trim();

        // Check for function definitions: f(x) = ...
        if (input.matches("[a-zA-Z]\\(x\\)\\s*=.*")) {
            new FunctionCommand().execute(input, scene);
            return;
        }

        // Otherwise, check registered commands (Vec, etc.)
        String keyword = input.contains("(") ? input.substring(0, input.indexOf("(")).toLowerCase() : input.toLowerCase();
        Command cmd = commands.get(keyword);
        if (cmd != null) {
            cmd.execute(input, scene);
        } else {
            System.out.println("Unknown command: " + input);
        }
    }
}
