package server.utils.websocket;

import server.request.Request;
import server.response.Response;
import server.utils.websocket.util.Protocols;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketServer implements WebSocket {
    private Request request;
    private Response response;

    private Map<String,Object> params = new HashMap<>();

    private boolean isOpen = false;
    private boolean isClose = false;
    private int version = 0;
    public WebSocketServer(Request request, Response response){
        this.request = request;
        this.response = response;
        try {
            analyzer(request.toString());
        }catch (IOException e){

            e.printStackTrace();
        }
    }
    private void analyzer(String s) throws IOException{
        params.clear();
        String[] params = s.split("\\s");
        int i = 0;
        for (String s1:params) {
            int connection = s1.trim().indexOf("Connection:");
            if (connection != -1) {
                if (params[i + 1].trim().equals("Upgrade")) {
                    this.params.put("Connection", "Upgrade");
                }
            }
            int upgrade = s1.trim().indexOf("Upgrade:");
            if (upgrade != -1) {
                if (params[i + 1].trim().equals("websocket")) {
                    this.params.put("Upgrade", "websocket");
                }
            }
            int secWebSocketVersion = s1.trim().indexOf("Sec-WebSocket-Version:");
            if (secWebSocketVersion != -1) {
                this.params.put("Sec-WebSocket-Version", params[i + 1]);
            }
            int secWebSocketKey = s1.trim().indexOf("Sec-WebSocket-Key");
            if (secWebSocketKey != -1) {
                this.params.put("Sec-WebSocket-Key", params[i + 1]);
            }
            int secWebSocketProtocol = s1.trim().indexOf("Sec-WebSocket-Protocol");
            if (secWebSocketProtocol != -1){
                StringBuffer sb = new StringBuffer();
                for (i = i + 1;params[i].length() != 0;i++){
                    sb.append(params[i]);
                }
                this.params.put("Sec-WebSocket-Protocol",sb.toString());
            }
            i++;
        }
        /*
        if (this.params.size() >= 4){
            throw new IllegalArgumentException("websocket is not support");
        }
        */
        this.version = Integer.parseInt((String) this.params.get("Sec-WebSocket-Version"));
        Protocols protocols = new Protocols(this.params);
        response.write(protocols.toString());
        response.flush();
        this.isOpen = true;
        this.isClose = false;
    }
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
