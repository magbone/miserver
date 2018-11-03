package server.utils.websocket;

import server.AbstractServer;
import server.request.NoSuchRequestMethodException;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

@RequestUrl
public abstract class AbstractWebSocket extends AbstractServer{

    @Override
    public void doGet(Request request, Response response) throws IOException {
        WebSocket webSocket = new WebSocketServer(request,response);
        if (webSocket.isOpen()){
            this.onOpen(webSocket);
        }
    }

    @Override
    public final void doPost(Request request, Response response) throws IOException {
        // this can't be override
    }

    public abstract void onMessage(WebSocket webSocket,String s);

    public abstract void onOpen(WebSocket webSocket);

    public abstract void onClose(WebSocket webSocket);

    public abstract void onError(WebSocket webSocket,Exception e);

    @Override
    public void run(Request request, Response response) throws IOException {
        if (request.getAction().equals(AbstractServer.GET)){
            doGet(request,response);
        }else{
            throw new NoSuchRequestMethodException("websocket could not support other request method");
        }
    }
}
