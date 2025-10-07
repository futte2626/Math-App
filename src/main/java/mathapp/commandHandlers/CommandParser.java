package mathapp.commandHandlers;

import mathapp.AppCore;
import mathapp.objects.twoD.Scene2D;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private Map<String, Command> commands = new HashMap<>();

    //This is for registering all commands
    public CommandParser() {
        register("func", new FunctionCommand());
        register("vec", new VectorCommand());
    }

    public void register(String name, Command command) {
        commands.put(name.toLowerCase(), command);
    }

    public void parseAndExecute(String input) {
        input = input.trim();

        // Get index of ( and )
        int open = input.indexOf("(");
        int close = input.lastIndexOf(")");

        // Makes sure that input contains ( and ) and that ( comes before )
        if (open == -1 || close == -1 || close <= open) {
            System.out.println("Invalid command format. Use: command(args...)");
            return;
        }

        // Extract command keyword aka what command is it. Fx is a func command or vec command
        String keyword = input.substring(0, open).toLowerCase();

        // Look up command
        Command cmd = commands.get(keyword);
        if (cmd != null) {
            cmd.execute(input);
        } else {
            System.out.println("Unknown command: " + keyword);
        }
    }
}
