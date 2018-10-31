package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Index extends AbstractServer {

    @Override
    @RequestUrl (url = "/")
    public void doGet(Request request, Response response)throws IOException {
        response.writeHead("Content-Type:text/html\n");
        response.write("\r\n");
        Map<String,Object> maps = new HashMap<>();
        maps.put("name","hello");
        maps.put("girl","my girl");
        response.write("index.html",maps);
        response.flush();
        response.close();
    }

    @Override
    public void doPost(Request request, Response response) {

    }


}
