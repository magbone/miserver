package server.app.sql.mysql;


import java.io.Serializable;
import java.lang.annotation.Annotation;

public class Mysql implements Serializable {

    //db config
    private String host;
    private int port;
    private String dbName;
    private String user;
    private String password;

    public Mysql(){

    }

    public Mysql(String host,int port,String dbName){
        this(host,port,dbName,null,null);
    }

    //must call this constructor
    public Mysql(String host,int port,String dbName,String user,String password){
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    /**
     *
     * @param db
     * @return
     */
    public static Mysql init(Class<? extends SQLConfig> db){
        Annotation[] annotations = db.getDeclaredAnnotations();
        for (Annotation annotation: annotations){
            if (annotation instanceof MysqlConfig){
                MysqlConfig mysqlConfig = (MysqlConfig) annotation;
                return new Mysql(mysqlConfig.host(),mysqlConfig.port(),mysqlConfig.dbName(),mysqlConfig.user(),mysqlConfig.passwd());
            }
        }
        return new Mysql();
    }

    public Reader getReader(){
        Connector connector = new Connector(host,dbName,user,password);
        connector.connect();
        return new Reader(connector);
    }

    public Writer getWriter(){
        Connector connector = new Connector(host,dbName,user,password);
        connector.connect();
        return new Writer(connector);
    }
}
