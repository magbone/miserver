package server.config;

import server.utils.ssl.SSL;
import server.utils.ssl.SSLBean;

import java.io.Serializable;

public class ConfigBean implements Serializable {

    private int maxConnectCount;
    private String page404;
    private int port;
    private String templatesDir;


    private SSLBean sslBean;
    public ConfigBean(int port,int maxConnectCount,String page404,String templatesDir,SSLBean sslBean){
        this.port = port;
        this.maxConnectCount = maxConnectCount;
        this.page404 = page404;
        this.templatesDir = templatesDir;
        this.sslBean = sslBean;
    }

    public int getMaxConnectCount() {
        return maxConnectCount;
    }

    public String getPage404() {
        return page404;
    }

    public int getPort() {
        return port;
    }

    public String getTemplatesDir() {
        return templatesDir;
    }

    public void setMaxConnectCount(int maxConnectCount) {
        this.maxConnectCount = maxConnectCount;
    }

    public void setPage404(String page404) {
        this.page404 = page404;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTemplatesDir(String templatesDir) {
        this.templatesDir = templatesDir;
    }

    public void setSslBean(SSLBean sslBean){ this.sslBean = sslBean;}

    public SSLBean getSslBean() {
        return sslBean;
    }
}
