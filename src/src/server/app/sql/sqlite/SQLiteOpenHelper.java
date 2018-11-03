package server.app.sql.sqlite;



import server.app.sql.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLiteOpenHelper implements SqlClose{
    private SQLiteDatabase SQLiteDatabase = new SQLiteDatabase();
    private int oldVersion;
    public SQLiteOpenHelper(String dbName, int dbVersion){
        if (!isExists(dbName)){
            SQLiteDatabase.connectDataBase(dbName);
            onCreate(SQLiteDatabase);
            createVersion();
            createVersion(dbVersion);
        }
        SQLiteDatabase.connectDataBase(dbName);
        updateVersion(dbVersion);
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return SQLiteDatabase;
    }
    protected abstract void onCreate(SQLiteDatabase db);
    protected abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    private boolean isExists(String databasePath){
        File file = new File(databasePath);
        return file.exists();
    }
    private void createVersion(){
        String sql = "create table if not exists db_version" +
                "(id INTEGER PRIMARY KEY autoincrement not null," +
                "version INTEGER )";
        SQLiteDatabase.doSQL(sql);
    }
    private void createVersion (int id){
        DataValues dataValues = new DataValues();
        dataValues.put("version",id);
        SQLiteDatabase.doInsertExecute("db_version",dataValues);
    }
    public void updateVersion (int id){
        ResultSet resultSet = SQLiteDatabase.doAllValuesRowSQL("db_version");
        try{
            if (resultSet.next()){
                oldVersion = resultSet.getInt("version");

                if (id > oldVersion){
                    System.out.println("update..");
                    DataValues dataValues = new DataValues();
                    dataValues.put("version",id);
                    SQLiteDatabase.doUpdateExecute("db_version","where id =?",new String[] {"1"},dataValues);
                    onUpgrade(SQLiteDatabase,oldVersion,id);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void close() {
        SQLiteDatabase.close();
    }
}
