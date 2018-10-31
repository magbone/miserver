package server.request;

public class NoRequestException extends NullPointerException {
    public NoRequestException(){super();}
    public NoRequestException(String s){super(s);}
}
