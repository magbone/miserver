package server.utils.oauth;

import server.AbstractServer;
import server.request.NoSuchRequestMethodException;
import server.request.Request;
import server.response.Response;

import java.io.IOException;

public abstract class AbstractOauth extends AbstractServer {
    @Override
    public void doGet(Request request, Response response) throws IOException {

    }

    @Override
    public final void doPost(Request request, Response response) throws IOException {
        // this can't be override
    }

    @Override
    public void run(Request request, Response response) throws IOException {
        if (request.getAction().equals(AbstractServer.GET)){
            doPost(request,response);
        }else{
            throw new NoSuchRequestMethodException("oauth could not support the other request");
        }
    }
}
