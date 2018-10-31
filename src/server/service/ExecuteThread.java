package server.service;

import server.AbstractServer;
import server.EmptyServerException;
import server.config.ConfigBean;
import server.request.NoRequestException;
import server.request.Request;
import server.request.RequestUrl;
import server.response.FaviconIcoResponse;
import server.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ExecuteThread implements Runnable{
    private Socket socket;
    private Class<?>[] serverClasses;
    private ConfigBean configBean;
    public ExecuteThread(Socket socket, ConfigBean configBean, Class<?>[] serverClasses){
        this.socket = socket;
        this.configBean = configBean;
        this.serverClasses = serverClasses;
    }

    @Override
    public void run() {
        try{
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            final Request request = new Request(in);
            final Response response = new Response(out);
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
                    server.run(request,response);
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    /**
     * get the severInstance
     * @param serverClass
     * @param url
     * @return
     */
    protected AbstractServer getServerInstance(Class serverClass,String url){
        AbstractServer server = null;
        try {
            if (url.equals(FaviconIcoResponse.FAVICONURL)){
                return FaviconIcoResponse.class.getDeclaredConstructor(null).newInstance();
            }
            for(Method method: serverClass.getDeclaredMethods()){
                RequestUrl requestUrl = method.getAnnotation(RequestUrl.class);

                if (requestUrl != null){
                    String getUrl = requestUrl.url();
                    if (url.equals(getUrl)){
                        server = (AbstractServer) serverClass.getDeclaredConstructor(null).newInstance();
                    }
                    break;
                }
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            e.printStackTrace();
        }
        return server;
    }
}
