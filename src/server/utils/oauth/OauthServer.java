package server.utils.oauth;

import server.AbstractServer;
import server.request.Request;
import server.response.Response;

import java.io.IOException;

public abstract class OauthServer extends AbstractServer {

    private String clientId;
    private String responseType;
    private String redirectUri;

    @Override
    public void doGet(Request request, Response response) throws IOException {
        clientId = request.getParam("clientId");
        responseType = request.getParam("responseType");
        redirectUri = request.getParam("redirectUri");
    }

    public abstract void oauthServer(String clientId,String responseType,String redirectUri,Response response) throws IOException;

    @Override
    public final void doPost(Request request, Response response) throws IOException {

    }
}
