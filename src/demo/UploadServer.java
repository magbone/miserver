package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

@RequestUrl(url = "/upload")
public class UploadServer extends AbstractServer {

    @Override
    public void doGet(Request request, Response response) throws IOException {

    }

    @Override
    public void doPost(Request request, Response response) throws IOException {
        System.out.println(request.toString());
        response.writeHead("Content-Type:text/html");
        response.write("hello");
        response.flush();
        response.close();
    }
}
