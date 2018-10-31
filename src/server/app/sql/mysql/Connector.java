package server.app.sql.mysql;

import server.app.sql.SqlClose;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector implements SqlClose{

    private String host;
    private String user;
    private String password;
    private String dbName;

    private Connection connection;
    public Connector(String host,String dbName,String user,String password){
        this.host = host;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch (ClassNotFoundException|IllegalAccessException|InstantiationException e){
            e.printStackTrace();
        }
    }
    public void connect(){
        String connectAddress = "jdbc:mysql://" + host + "/" + dbName + "?" + "user=" + user + "&password=" + password;
        try{
            connection = DriverManager.getConnection(connectAddress);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public Connection getConnection(){
        return connection;
    }
    @Override
    public void close() {
        try {
            if (!connection.isClosed())
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
