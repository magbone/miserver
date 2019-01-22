package server.utils.ssl.protocols;

public interface ProtocolsHeadImpl {

    void setContentType(int contentType);
    void setHeadVersion (int version);
    void setHeadLength(int length);
}
