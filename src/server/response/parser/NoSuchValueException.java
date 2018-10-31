package server.response.parser;

public class NoSuchValueException extends RuntimeException {
    public NoSuchValueException(){super();}
    public NoSuchValueException(String s){super(s);}
}
