package server;

import server.request.NoSuchRequestMethodException;
import server.request.Request;
import server.request.RequestUrl;
import server.response.Response;

import java.io.IOException;

public abstract class AbstractServer {

    public final static String POST = "POST";
    public final static String GET = "GET";
    public final static String OPTIONS = "OPTIONS";
    public final static String HEAD = "HEAD";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static String TRACE = "TRACE";
    public final static String CONNECT = "CONNECT";


    private String templatesDir;
    public AbstractServer(){
        super();
    }
    @RequestUrl
    public abstract void doGet(Request request, Response response) throws IOException;
    @RequestUrl
    public abstract void doPost(Request request,Response response) throws IOException;

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void doOptions(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void doHead(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void doPut(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }

    public void doDelete(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }

    public void doTrace(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }

    public void doConnect(Request request,Response response) throws IOException{
        response.write("Content-Type:text/html");
        response.flush();
        response.close();
    }
    public final void setTemplatesDir(String templatesDir){
        this.templatesDir = templatesDir;
    }
    public void run (Request request,Response response) throws IOException{
        String action = request.getAction();
        response.setTemplatesDir(templatesDir);
       // System.out.println(request.toString());
        switch (action){
            case POST:
                doPost(request,response);
                break;
            case GET:
                doGet(request,response);
                break;
            case OPTIONS:
                doOptions(request,response);
                break;
            case HEAD:
                doHead(request,response);
                break;
            case PUT:
                doPut(request,response);
                break;
            case DELETE:
                doDelete(request,response);
                break;
            case TRACE:
                doTrace(request,response);
                break;
            case CONNECT:
                doConnect(request,response);
                break;
                default:
                    throw new NoSuchRequestMethodException("no such request: " + action);



        }
    }

}
