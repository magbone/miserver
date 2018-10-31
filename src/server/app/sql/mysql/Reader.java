package server.app.sql.mysql;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Reader {

    // database reader;

    private Connector connector;
    public Reader(Connector connector){
        this.connector = connector;
    }

    public List<Class<?>> readSum(@NotNull String table,@NotNull Class<?> bean,int sum){
        Field[] fields = bean.getFields();
        StringBuffer buffer = new StringBuffer();
        buffer.append("select ");
        String value = null;
        for (Field field:fields){
            value += (field.getName() + ",");
        }
        value = "(" + value.substring(0,value.lastIndexOf(',')) + ")";
        buffer.append(value);
        buffer.append(" from " + table );
        if (sum != -1) buffer.append(" limit " + sum);
        String sql = buffer.toString();
        Connection connection = connector.getConnection();
        try {
            Statement statement = connection.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Class<?>> readAll(@NotNull String table,@NotNull Class<?> bean){
        return readSum(table,bean,-1);
    }
}
