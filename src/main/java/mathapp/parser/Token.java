package mathapp.parser;

public class Token {
    public enum Type { NUMBER, OPERATOR, FUNCTION, VARIABLE, LEFT_PAREN, RIGHT_PAREN }

    public Type type;
    public String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
