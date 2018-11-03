package server.app.sql.sqlite;

import server.app.sql.DataValues;
import server.app.sql.SQLException.NullTableException;
import server.app.sql.SQLException.SqlConnectionException;

import server.app.sql.SqlClose;

import java.sql.*;
import java.util.Map;

/**
 *
 * @author magbone
 *
 *
 */

public class SQLiteDatabase implements SqlClose{
    private Connection connection = null;
    private boolean isClose = true;

    public SQLiteDatabase(){
        initConnect();
    }

    /**
     *
     *
     * @param dbName the database what you want to connect;
     */
    public void connectDataBase(String dbName){
        if (dbName == null) throw new NullPointerException();
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            isClose = false;
        }catch (SQLException e){
            e.printStackTrace();
            close();
            isClose = true;
        }
    }
    private void initConnect(){
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * to examine the database if connect;
     *
     * @return
     */
    public boolean isConnect(){
        try{
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * execute server.app.sql
     * @param sql
     */
    public void doSQL(String sql){
        if (sql == null) throw new NullPointerException();
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param table
     * @param sql
     * @param whereArgs
     * @param args
     * @return
     */
    public ResultSet doRowSQL(String table,String[] sql,String whereArgs,String[] args){
        return doExecute(table,sql,whereArgs,args);
    }

    /**
     *
     * @param table
     * @return
     */
    public ResultSet doAllValuesRowSQL(String table){
        return doExecute(table,null,null,null);
    }

    /**
     *
     * @param table
     * @param sql
     * @param args
     * @return
     */
    public ResultSet doAllRowSQL(String table,String[] sql,String[] args){
        return doExecute(table,sql,null,args);
    }

    /**
     *
     * @return
     */
    public long getCount(){
        return 0;
    }

    public long doInsertExecute(String table,DataValues dataValues){
        return doInsertDataExecute(table,dataValues);
    }
    public void delect(String table,String whereArgs,String[] args){
        deleteData(table,whereArgs,args);
    }
    public void doUpdateExecute(String table,String whereArgs,String[] args,DataValues dataValues){
        update(table,whereArgs,args,dataValues);
    }
    /**
     *
     * @param table
     * @param rowName
     * @param whereArgs
     * @param args
     * @return
     */
    private synchronized ResultSet doExecute(String table,String[] rowName,String whereArgs,String[] args){
        if (table == null) throw new NullTableException("table should not be null");
        if (!isConnect()) throw new SqlConnectionException("Wrong,the connection was close");
        PreparedStatement preparedStatement;
        Statement statement;
        if (rowName == null && whereArgs == null && args== null){
            String sql = "select * from " + table;
            ResultSet resultSet = null;
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

            }catch (SQLException e){
                e.printStackTrace();

            }
            return resultSet;
        }else if (rowName == null && whereArgs != null && args != null){
            String sql = "select * from " + table + " " + whereArgs;
            System.out.println(sql);
            try{
                preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length;i++){
                    preparedStatement.setString(i + 1,args [i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    private long doInsertDataExecute(String table, DataValues dataValues){
        if (table == null) throw new NullTableException();
        if (dataValues == null) throw  new NullPointerException();

        String sql = "insert into " + table;

        String column = "(";
        String values = "(";
        for (Map.Entry<String,Object> entry : dataValues.getMap().entrySet()){
            column = column + entry.getKey() + ",";
            values = values + "? ,";
        }
        int last = column.lastIndexOf(",");
        int lastP = values.lastIndexOf(",");
        column = column.substring(0,last) + ")";
        values = values.substring(0,lastP) + ")";
        sql = sql + " "+ column + " values " + values;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            int cursor = 1;
            for (Map.Entry <String,Object> entry : dataValues.getMap().entrySet()){
                if (entry.getValue() instanceof String){
                    preparedStatement.setString(cursor,(String)entry.getValue());
                }else if (entry.getValue() instanceof Integer){
                    preparedStatement.setInt(cursor,(int)entry.getValue());
                }else if (entry.getValue() instanceof Boolean){
                    preparedStatement.setBoolean(cursor,(boolean) entry.getValue());
                }
                cursor ++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    private void deleteData(String table,String whereArgs,String[] args){
        if (table == null) throw new NullTableException();

        String sql = "delete from " + table;
        if (whereArgs == null && args == null){
            try{
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            sql = sql + " " + whereArgs;
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length;i++){
                    preparedStatement.setString(i + 1,args[i]);
                }
                preparedStatement.execute();
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    private void update(String table,String whereArgs,String[] args,DataValues dataValues){
        if (table == null) throw new NullTableException();
        String sql = "update " + table + " set ";
        for (Map.Entry<String,Object> entry: dataValues.getMap().entrySet()){
            sql = sql + entry.getKey() + " = ? ,";
        }
        sql = sql.substring(0,sql.lastIndexOf(','));
        sql = sql + " " + whereArgs;
        System.out.println(sql);
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int i = 1;
            for (Map.Entry<String,Object> entry: dataValues.getMap().entrySet()){
                if (entry.getValue() instanceof Integer){
                    preparedStatement.setInt(i,(int) entry.getValue());
                }else if (entry.getValue() instanceof String){
                    preparedStatement.setString(i,(String)entry.getValue());
                }else if (entry.getValue() instanceof Boolean){
                    preparedStatement.setBoolean(i,(boolean)entry.getValue());
                }
                i++;
            }
            for (int j = 0;j < args.length;j++,i++){
                preparedStatement.setString(i,args[j]);

            }
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    /**
     *
     * @param table
     * @return
     */
    private long getTableConstructionCount(String table){
        String sql = "select * from sqlite_master where type=\"table\" and name=" + table;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.getRow();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    /**
     *
     * to close the database;
     *
     */
    @Override
    public void close() {
        if(!isClose){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
