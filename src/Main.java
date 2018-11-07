import demo.*;
import server.AbstractServer;
import server.MainApplication;
import server.app.OnRenderListener;
import server.app.OnRequestListener;
import server.app.Server;
import server.utils.websocket.WebSocketHandler;


public class Main {

    public static void main(String args[]){
        MainApplication mainApplication = new  MainApplication();
        Class<?>[] classes = new Class<?>[]{Index.class, Login.class, GetUserInfo.class, UserLogin.class,MyOauthServer.class,Page.class};
        mainApplication.setServerClasses(classes);
        mainApplication.setConfig(ServerConfig.class);

        //handler
        //WebSocket handler
        mainApplication.addWebSocketHandler(WebSocketDemo.class,8074);

        //Server request listener
        mainApplication.addOnRequestListener(new OnRequestListener() {
            @Override
            public void onStart(Server server) {
                System.out.println("start");
                System.out.println(server.getConnectTime());
            }

            @Override
            public void doRequest(Server server) {
                System.out.println("request");
            }

            @Override
            public void onCreate(Server server){
                System.out.println("create");
                AbstractServer server1 = server.getAbServerInstance();
            }
            @Override
            public void doResponse(Server server) {
                System.out.println("response");
            }

            @Override
            public void onDestroy(Server server) {
                System.out.println("destroy");
            }
        });

        //Server render listener
        mainApplication.setOnRenderListener(new OnRenderListener() {
            @Override
            public void beforeRender(String template) {
                System.out.println("beforeRender");
            }

            @Override
            public void onRender(String template) {
                System.out.println("onRender");
            }

            @Override
            public void afterRender(String template, String rendered) {
                System.out.println("afterRender");
            }
        });
        mainApplication.run();
    }
}
