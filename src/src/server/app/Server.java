package server.app;

import server.AbstractServer;
import server.request.Request;

public class Server {

    private AbstractServer abServer;
    private long connectTime;
    private Request request;

    public void addAbServer(AbstractServer abServer){
        this.abServer = abServer;
    }

    public void setRequest(Request request){
        this.request = request;
    }
    public AbstractServer getAbServerInstance(){
        if (abServer == null) throw new NullPointerException("the server is not instance");
        return abServer;
    }
    public Request getRequest(){
        if (request == null) throw new NullPointerException("request is not start");
        return request;
    }
    public void setConnectTime(long time){
        connectTime = time;
    }

    public long getConnectTime(){
        return connectTime;
    }
}
