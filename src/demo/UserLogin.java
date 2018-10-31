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
        response.writeHead("Content-Type:text/html");
        String name = request.getParam("name");
        String passwd = request.getParam("password");

        SqlManager sqlManager = new SqlManager();
        if (sqlManager.search(name,passwd)){
            response.write("ok");
        }else{
            response.write("false");
        }
        sqlManager.close();

        response.flush();
        response.close();
    }

    @Override
    public void doGet(Request request, Response response)throws IOException {

    }
}
