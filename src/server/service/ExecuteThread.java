package server.service;

import server.AbstractServer;
import server.EmptyServerException;
import server.app.OnRenderListener;
import server.app.OnRequestListener;
import server.app.Server;
import server.config.ConfigBean;
import server.request.NoRequestException;
import server.request.Request;
import server.request.RequestUrl;
import server.response.FaviconIcoResponse;
import server.response.Response;
import server.utils.ssl.SSLBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExecuteThread implements Runnable{
    private Socket socket;
    private Class<?>[] serverClasses;
    private ConfigBean configBean;
    private OnRequestListener listener;
    private OnRenderListener renderListener;

    private Server server = new Server();

    private boolean isServer = false;



    //params
    private Map<String,String> urlParams = new HashMap<>();

    public ExecuteThread(Socket socket, ConfigBean configBean, Class<?>[] serverClasses, OnRequestListener listener,OnRenderListener renderListener){
        this.socket = socket;
        this.configBean = configBean;
        this.serverClasses = serverClasses;
        this.listener = listener;
        this.renderListener = renderListener;
        urlParams.clear();
    }

    @Override
    public void run() {
        try{
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            final Request request = new Request(in);
            // add a listener to listen the request;
            request.addOnRequest(new Request.OnRequest() {
                @Override
                public void onStart() {
                    server.setConnectTime(new Date().getTime());
                    listener.onStart(server);
                }

                @Override
                public void doRequest() {
                    listener.doRequest(server);
                }
            });
            request.doRequest();
            final Response response = new Response(out);
            // add a listener to listen the response;
            response.addOnResponse(new Response.OnResponse() {
                @Override
                public void doOnResponse() {
                    listener.doResponse(server);
                }

                @Override
                public void onDestroy() {
                    listener.onDestroy(server);
                }
            });
            response.setOnRenderListener(renderListener);
            dispatch(request,response); //dispatch the request
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * dispatch the request
     * @param request
     * @param response
     */
    protected void dispatch(Request request,Response response){
        if (serverClasses.length <= 0){
            throw new EmptyServerException("server count should not be 0");
        }
        String url = request.getUrl();
        if (url == null) throw new NoRequestException("url is null");
        for (int i = 0;i < serverClasses.length;i++){
            Class newClass = serverClasses[i];
            AbstractServer server = getServerInstance(newClass,url);

            if (server != null){
                try {
                    server.setTemplatesDir(configBean.getTemplatesDir());
                    server.setParams(urlParams);
                    server.run(request,response);
                }catch (IOException e){
                    e.printStackTrace();
                }
                isServer = true;
                break;
            }
        }
        if (!isServer){

        }

    }

    /**
     * get the severInstance
     * @param serverClass
     * @param url
     * @return
     */
    protected AbstractServer getServerInstance(Class<?> serverClass,String url){
        AbstractServer server = null;
        try {
            if (url.equals(FaviconIcoResponse.FAVICONURL)){
                return FaviconIcoResponse.class.getDeclaredConstructor(null).newInstance(null);
            }
            for(Annotation annotation:serverClass.getDeclaredAnnotations()){
                if (annotation instanceof RequestUrl) {
                    RequestUrl requestUrl = (RequestUrl) annotation;
                    String getUrl = requestUrl.url();
                    if (urlParams(getUrl, url)) {
                        server = (AbstractServer) serverClass.getDeclaredConstructor(null).newInstance(null);
                        this.server.addAbServer(server);
                        listener.onCreate(this.server);
                    }
                    break;
                }
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            e.printStackTrace();
        }
        return server;
    }
    private boolean urlParams(String url,String requestUrl){
        String[] s = url.split("/");
        String[] sr = requestUrl.split("/");
        if (s.length == 0 &&  sr.length== 0) return true;
        if (s.length != sr.length) return false;
        for (int i = 0; i < s.length;i++){
            String s2 = s[i].trim();
            int start = s2.indexOf('{');
            int end = s2.indexOf('}');
            if (start != -1 && end != -1){
                String params = s2.substring(start + 1,end-1).trim();
                urlParams.put(params,sr[i]);
            }else{
                if (!sr[i].equals(s2)) return false;
            }
        }
        return true;
    }
}
