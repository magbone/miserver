package server.utils.websocket;

public interface WebSocket {
    boolean isOpen();
    boolean isClose();
    void close();
    void open();
    void send(String s);
    int version();
    int readyState();
}
