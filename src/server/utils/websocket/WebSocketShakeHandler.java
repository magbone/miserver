package server.utils.websocket;

import server.utils.websocket.util.protocols.handshake.Protocols;


import java.util.HashMap;
import java.util.Map;

public class WebSocketShakeHandler {
    private int version;
    private Map<String,Object> params = new HashMap<>();
    private StringBuffer httpRequest = new StringBuffer();

    private AbstractWebSocket socket;

    private String response;
    public WebSocketShakeHandler(String s,AbstractWebSocket socket) {
        this.socket = socket;
        analyzer(s);
    }

    private void analyzer(String s){
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
        this.response = protocols.toString();
        socket.onOpen(null);

    }

    @Override
    public String toString() {
        return this.response;
    }
}
