package server.utils.ssl;

public class SSLBean {

    private boolean isOpen;
    private boolean isStrict;
    private int port;

    public SSLBean(boolean isOpen,boolean isStrict,int port){
        this.isOpen = isOpen;
        this.isStrict = isStrict;
        this.port = port;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isStrict() {
        return isStrict;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setStrict(boolean strict) {
        isStrict = strict;
    }
}
