package demo;

import server.request.RequestUrl;
import server.response.Response;
import server.utils.oauth.OauthServer;

import java.io.IOException;

@RequestUrl(url = "/apis")
public class MyOauthServer extends OauthServer {


    @Override
    public void oauthServer(String clientId, String responseType, String redirectUri, Response response)throws IOException {
        response.writeHead("Content-Type:text/html\n");
        response.write("\r\n");
        response.write(clientId + responseType + redirectUri);
        response.flush();
        response.close();
    }
}
