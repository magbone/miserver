package demo;


import server.utils.websocket.AbstractWebSocket;
import server.utils.websocket.WebSocket;


public class WebSocketDemo extends AbstractWebSocket {

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("open");
    }

    @Override
    public void onClose(WebSocket webSocket) {

    }
}
