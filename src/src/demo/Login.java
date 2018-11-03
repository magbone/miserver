package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

@RequestUrl(url = "/login")
public class Login extends AbstractServer {
    @Override
    public void doGet(Request request, Response response)throws IOException {
        response.writeHead("Content-Type:text/html");
        response.write("login.vm",null);
        response.flush();
        response.close();
    }

    @Override
    public void doPost(Request request, Response response) {

    }
}
