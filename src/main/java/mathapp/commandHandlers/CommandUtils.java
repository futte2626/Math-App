package mathapp.commandHandlers;

public class CommandUtils {
    public static String[] getArgs(String input) {
        int start = input.indexOf("(");
        int end = input.lastIndexOf(")");
        if (start == -1 || end == -1 || end <= start) {
            return new String[0];
        }
        String inside = input.substring(start + 1, end);
        // split on commas, trim spaces
        return inside.split("\\s*,\\s*");
    }
}