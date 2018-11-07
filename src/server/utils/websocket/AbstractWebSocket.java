package server.utils.websocket;


public abstract class AbstractWebSocket{


    public abstract void onError(WebSocket webSocket, Exception e);


    public abstract void onMessage(WebSocket webSocket, String s);


    public abstract void onOpen(WebSocket webSocket);


    public abstract void onClose(WebSocket webSocket);

}
