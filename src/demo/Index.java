package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestUrl (url = "/")
public class Index extends AbstractServer {

    @Override
    public void doGet(Request request, Response response)throws IOException {
        response.writeHead("Content-Type:text/html");
        Map<String,Object> maps = new HashMap<>();

        maps.put("girl","sb");
        response.write("index.vm",maps);
        response.flush();
        response.close();
    }

    @Override
    public void doPost(Request request, Response response) {

    }


}
