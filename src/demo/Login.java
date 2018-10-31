package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

public class Login extends AbstractServer {

    @RequestUrl(url = "/login")
    @Override
    public void doGet(Request request, Response response)throws IOException {
        response.writeHead("Content-Type:text/html\n");
        response.write("\r\n");
        response.write("login.html",null);
        response.flush();
        response.close();
    }

    @Override
    public void doPost(Request request, Response response) {

    }
}
