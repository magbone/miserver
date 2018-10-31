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
        response.writeHead("Content-Type:text/html\n");
        response.write("\r\n");
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("token",token);
        objectMap.put("name",name);
        response.write("userinfo.html",objectMap);
        response.flush();
        response.close();
    }
}
