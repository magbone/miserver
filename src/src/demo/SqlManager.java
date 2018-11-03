package demo;


import server.app.sql.DataValues;
import server.app.sql.sqlite.SQLiteDatabase;
import server.app.sql.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlManager extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "users.db";
    public SqlManager(){
        super(DB_NAME,DB_VERSION);
    }
    @Override
    protected void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists users" +
                " (id INTEGER PRIMARY KEY autoincrement not null," +
                " name varchar(50) not null," +
                " sex varchar(5) not null," +
                " age INTEGER not null," +
                " stu_id varchar(100) not null," +
                " date varchar(200) not null," +
                " pic varchar(200))";
        db.doSQL(sql);
    }

    @Override
    protected void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        String sql = "create table if not exists password" +
                " (id INTEGER PRIMARY KEY autoincrement not null," +
                " name varchar(50) not null," +
                " password varchar(18) not null)";
        db.doSQL(sql);
    }

    public List<Map<String,Object>> getAllValues(){
        SQLiteDatabase db = this.getSQLiteDatabase();
        ResultSet resultSet = db.doAllValuesRowSQL("users");
        List<Map<String,Object>> datas = new ArrayList<>();
        try{
            int i = 0;
            while (resultSet.next()){
                i++;
                if (i > 100)break;
                HashMap<String,Object> data = new HashMap<>();
                data.put("id",String.valueOf(resultSet.getInt("id")));
                data.put("name",resultSet.getString("name"));
                data.put("sex",resultSet.getString("sex"));
                data.put("stu_id",resultSet.getString("stu_id"));
                data.put("date",resultSet.getString("date"));
                data.put("pic",resultSet.getString("pic"));
                datas.add(data);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return datas;
    }
    public boolean search(String name,String password){
        SQLiteDatabase db = this.getSQLiteDatabase();

        try{
            ResultSet resultSet = db.doRowSQL("password",null,"where name = ? and password = ?",new String[]{name,password});
            if (resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void insert(String name,String password){
        SQLiteDatabase db = this.getSQLiteDatabase();
        DataValues dataValues = new DataValues();
        dataValues.put("name",name);
        dataValues.put("password",password);
        long rows = db.doInsertExecute("password",dataValues);
    }
    public void update(String name,String c,String id){
        SQLiteDatabase db = this.getSQLiteDatabase();
        DataValues dataValues = new DataValues();
        dataValues.put(c,name);
        db.doUpdateExecute("users","where id=?",new String[]{id},dataValues);
    }
    public String getHead(String id){
        SQLiteDatabase db = this.getSQLiteDatabase();
        ResultSet resultSet = db.doRowSQL("users",null,"where id = ?",new String[]{id});
        try{
            if (resultSet.next()){
                return resultSet.getString("pic");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
