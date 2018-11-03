package server.app.sql.SQLException;

public class SqlConnectionException extends RuntimeException {
    public SqlConnectionException(){
        super();
    }
    public SqlConnectionException(String s){
        super(s);
    }
}
