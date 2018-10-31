package server.response;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;

import java.io.IOException;

public class FaviconIcoResponse extends AbstractServer {
    public final static String FAVICONURL = "/favicon.ico";

    public FaviconIcoResponse(){
        super();
    }

    @Override
    public void doPost(Request request, Response response) {

    }

    @RequestUrl(url = "/favicon.ico")
    @Override
    public void doGet(Request request, Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }
}
