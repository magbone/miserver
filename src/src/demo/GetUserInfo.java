package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class GetUserInfo extends AbstractServer {
    @Override
    public void doPost(Request request, Response response) {

    }

    @RequestUrl(url = "/getuserinfo")
    @Override
    public void doGet(Request request, Response response) throws IOException{

        String token = request.getParam("token");
        String name = request.getParam("name");
        response.writeHead("Content-Type:text/html; charset=utf-8");
        response.writeHead("Cache-Control:max-age=0, private");
        response.write("\n");
        SqlManager sqlManager = new SqlManager();
        /*
        for (int i = 0;i < 5000;i++){
            sqlManager.insert("学生" + String.valueOf(i),"男","null",String.valueOf(i),String.valueOf(new Date().getTime()));
        }*/
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("name",name);
        map.put("dataCount",String.valueOf(sqlManager.getAllValues().size()));
        map.put("data",sqlManager.getAllValues());
        response.write("hello.vm",map);
        sqlManager.close();
        response.flush();
        response.close();
    }

}
