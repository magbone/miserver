package server.utils.websocket;

import server.request.Request;
import server.response.Response;

import java.util.HashMap;
import java.util.Map;

public class WebSocketServer implements WebSocket {
    private Request request;
    private Response response;

    private Map<String,Object> params = new HashMap<>();

    private boolean isOpen = false;
    private boolean isClose = false;
    private int version = 0;


    @Override
    public void close() {

    }

    @Override
    public void open() {

    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void send(String s) {

    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public int readyState() {
        return 0;
    }
}
