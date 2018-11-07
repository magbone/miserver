package demo;

import server.AbstractServer;
import server.app.sql.mysql.Mysql;
import server.app.sql.mysql.Reader;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

@RequestUrl(url = "/page/{ pageId }/{ homePath }")
public class Page extends AbstractServer{


    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.writeHead("Content-Type:text/html");
        Reader reader = Mysql.init(MysqlConfigDemo.class).getReader();
        response.write(getParams("pageId") + " " + getParams("homePath"));
        response.flush();
        response.close();
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {

    }
}
