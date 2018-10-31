package server.response.parser.util;

public class EmptyPatternException extends NullPointerException {
    public EmptyPatternException(){
        super();
    }
    public EmptyPatternException(String s){
        super(s);
    }
}
