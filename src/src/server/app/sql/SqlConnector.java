package server.app.sql;



import java.sql.*;
import java.util.Map;

public class SqlConnector {
    private Connection sqlConnect = null;
    private boolean isClose = true;
    public SqlConnector(String dbName){
        initConnect();
        createDb(dbName);
        /*
        createTable("create table users" +
                "(id INTEGER PRIMARY KEY autoincrement not null," +
                "name varchar(50) not null," +
                "sex varchar(5) not null," +
                "age INTEGER not null," +
                "stu_id varchar(100) not null," +
                "date varchar(200) not null)");
                */
    }

    private void initConnect(){
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private void createDb(String dbName){
        if (dbName == null) throw new NullPointerException();
        try{
            sqlConnect = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            isClose = false;
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }
    }
    public boolean isConnect(){
        try{
            return !sqlConnect.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    private void createTable(String sql){
        if (sqlConnect == null) throw  new NullPointerException();
        Statement statement = null;
        try{
            statement = sqlConnect.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
    public void insert(String sql,DataValues dataValues){
        synchronized (sqlConnect){
            try{
                PreparedStatement preparedStatement = sqlConnect.prepareStatement(sql);
                Map<String,Object> map = dataValues.getMap();

                preparedStatement.execute();
                preparedStatement.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void close(){
        if (!isClose){
            try{
                sqlConnect.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
