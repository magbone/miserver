package server;


import server.app.OnRenderListener;
import server.app.OnRequestListener;
import server.config.BaseConfig;
import server.config.Config;
import server.config.ConfigBean;
import server.config.ConfigSyntaxException;
import server.service.ExecuteThread;
import server.service.ExecuteThreadPool;
import server.utils.websocket.AbstractWebSocket;
import server.utils.websocket.WebSocketHandler;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainApplication implements Runnable{

    private int port;
    private ServerSocket serverSocket;
    private Class<?>[] serverClasses;

    private ConfigBean configBean = null;
    private ExecuteThreadPool executeThreadPool;

    //private Charset charset = Charset.forName("UTF-8");
    //private Selector selector = null;


    private OnRequestListener listener;
    private OnRenderListener renderListener;

    private List<? extends Object> threads = new ArrayList<>();



    public MainApplication(){
        this.defaultConfig();
        if (configBean == null) throw new ConfigSyntaxException("Config syntax error: No such config");

    }

    public final void setServerClasses(Class[] serverClasses){
        this.serverClasses = serverClasses;
    }

    @Override
    public void run() {
        this.port = configBean.getPort();
        //start();
        System.out.println("Server run as http://127.0.0.1:" + this.port);
        executeThreadPool = new ExecuteThreadPool(configBean.getMaxConnectCount());
        try{
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                ExecuteThread executeThread = new ExecuteThread(socket,configBean,serverClasses,listener,renderListener);
                executeThreadPool.addThread(executeThread);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }


    public final void setConfig(Class<? extends BaseConfig> configs){
        Annotation[] annotations = configs.getDeclaredAnnotations();
        for (Annotation annotation: annotations){
            if (annotation instanceof Config){
                Config config = (Config)annotation;
                configBean = new ConfigBean(config.port(),config.maxConnectCount(),config.page404(),config.templateDir());
                break;
            }
        }
    }

    public final void addWebSocketHandler(Class<? extends AbstractWebSocket> handler, int port){
        WebSocketHandler handler1 = new WebSocketHandler(handler,port);
        new Thread(()->{
            handler1.run();
        }).start();
        //threads.add(handler1);
    }



    public void addOnRequestListener(OnRequestListener onRequestListener){
        this.listener = onRequestListener;
    }

    public void setOnRenderListener(OnRenderListener onRenderListener){
        this.renderListener = onRenderListener;
    }
    private void defaultConfig(){
        try {
            Class configClass = Class.forName("server.config.BaseConfig");
            Annotation[] annotations = configClass.getDeclaredAnnotations();
            for (Annotation annotation : annotations){
                if (annotation instanceof Config){
                    Config config = (Config)annotation;
                    configBean = new ConfigBean(config.port(),config.maxConnectCount(),config.page404(),config.templateDir());
                    break;
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
