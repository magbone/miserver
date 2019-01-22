package server.utils.ssl.protocols.handshake;

public interface TSLHandshakeImpl {
    void setHandshakeType(int handshakeType);
    void setLength(int len);
    void setVersion(int version);
    void setRandom(Random random);
    void setSession(Session session);

}
