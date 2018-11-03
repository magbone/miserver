package server.app.sql.SQLException;

public class NullTableException extends NullPointerException {
    public NullTableException(){
        super();
    }
    public NullTableException(String s){
        super(s);
    }
}
