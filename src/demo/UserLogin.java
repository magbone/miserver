package demo;

import server.AbstractServer;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;
public class UserLogin extends AbstractServer {
    @RequestUrl(url = "/loginin")
    @Override
    public void doPost(Request request, Response response)throws IOException {
        response.writeHead("Content-Type:text/html\n");
        response.write("\r\n");
        String name = request.getParam("name");
        String passwd = request.getParam("password");
        String message = name + "\n" + passwd;
        response.write(message);
        response.flush();
        response.close();
    }

    @Override
    public void doGet(Request request, Response response)throws IOException {

    }
}
