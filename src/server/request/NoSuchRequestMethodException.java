package server.request;

public class NoSuchRequestMethodException extends  NullPointerException{
    public NoSuchRequestMethodException(){super();}
    public NoSuchRequestMethodException(String s){super(s);}
}
