package demo;

import server.request.RequestUrl;
import server.utils.websocket.AbstractWebSocket;
import server.utils.websocket.WebSocket;

@RequestUrl(url = "/chat")
public class WebSocketDemo extends AbstractWebSocket {

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onOpen(WebSocket webSocket) {

    }

    @Override
    public void onClose(WebSocket webSocket) {

    }
}
