import demo.*;

public class Main {

    public static void main(String args[]){
        MainApplication mainApplication = new  MainApplication();
        Class[] classes = new Class[]{Index.class, Login.class, GetUserInfo.class, UserLogin.class,WebSocketDemo.class};
        mainApplication.setServerClasses(classes);
        mainApplication.setConfig(ServerConfig.class);
        mainApplication.run();
    }
}
