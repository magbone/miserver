package server.app;



public interface OnRequestListener {
    void onStart(Server server);
    void doRequest(Server server);
    void onCreate(Server server);
    void doResponse(Server server);
    void onDestroy(Server server);
}
