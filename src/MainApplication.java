import server.AbstractServer;
import server.EmptyServerException;
import server.config.BaseConfig;
import server.config.Config;
import server.config.ConfigBean;
import server.config.ConfigSyntaxException;
import server.request.Request;
import server.request.RequestUrl;
import server.response.FaviconIcoResponse;
import server.response.Response;
import server.service.ExecuteThread;
import server.service.ExecuteThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.charset.Charset;


public class MainApplication implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private Class<?>[] serverClasses;

    private ConfigBean configBean = null;
    private ExecuteThreadPool executeThreadPool = new ExecuteThreadPool();

    //private Charset charset = Charset.forName("UTF-8");
    //private Selector selector = null;


    public MainApplication(){
        this.defaultConfig();
        if (configBean == null) throw new ConfigSyntaxException("Config syntax error: No such config");
        this.port = configBean.getPort();

    }

    public void setServerClasses(Class[] serverClasses){
        this.serverClasses = serverClasses;
    }
    @Override
    public void run() {
        System.out.println("Server run as http://127.0.0.1:" + this.port);
        /*
        try {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(this.port));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            Request request = null;
            Response response = null;
            while (selector.select() > 0){

                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);
                    if (sk.isAcceptable()) {
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        sk.interestOps(SelectionKey.OP_ACCEPT);
                    }
                    else if(sk.isValid() && sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        request = new Request(sc);
                        System.out.println(request.toString());
                        sk.interestOps(SelectionKey.OP_WRITE);
                    }

                    else if (sk.isValid() && sk.isWritable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        response = new Response(sc);
                        if (request == null) System.out.println("kong");
                        sk.interestOps(SelectionKey.OP_READ);

                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        */
        try{
            serverSocket = new ServerSocket(port);
            while (true){

                Socket socket = serverSocket.accept();
                ExecuteThread executeThread = new ExecuteThread(socket,configBean,serverClasses);
                executeThreadPool.addThread(executeThread);
                /*
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Request request = new Request(in);
                Response response = new Response(out);
                dispatch(request,response); //dispatch the request

                out.close();
                in.close();
                socket.close();
                */
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

    /**
     * dispatch the request
     * @param request
     * @param response
     */
    protected void dispatch(Request request,Response response){
        if (serverClasses.length <= 0){
            throw new EmptyServerException("server count should not be 0");
        }
        final String url = request.getUrl();
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
