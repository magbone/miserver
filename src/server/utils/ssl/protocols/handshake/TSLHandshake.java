package server.utils.ssl.protocols.handshake;


import server.utils.ssl.protocols.Bytes;
import server.utils.ssl.protocols.ProtocolsHeadImpl;

public class TSLHandshake implements TSLHandshakeImpl,ProtocolsHeadImpl,Bytes{

    //HandshakeType
    public static final int HANDSHAKETYPE_HELLO_REQUEST = 0; // hello request
    public static final int HANDSHAKETYPE_CLIENT_HELLO = 1; // client hello
    public static final int HANDSHAKETYPE_SERVER_HELLO = 2; // server hello
    public static final int HANDSHAKETYPE_CERTIFICATE = 11; // certificate
    public static final int HANDSHAKETYPE_SERVER_KEY_EXCHANGE = 12 ;// server key exchange
    public static final int HANDSHAKETYPE_CERTIFICATE_REQUEST = 13 ;// certificate request
    public static final int HANDSHAKETYPE_SERVER_HELLO_DONE = 14; // server hello done
    public static final int HANDSHAKETYPE_VERIFY = 15; // certificate verify
    public static final int HANDSHAKETYPE_CLIENT_KEY_EXCHANGE = 16; // client key exchange
    public static final int HANDSHAKETYPE_FINISHED = 20; // finished

    private int contentType;
    private int headVersion;
    private int headLength;
    private int handshakeType;
    private int length;
    private int version;
    private Random random;
    private Session session;

    @Override
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setHeadLength(int headLength) {
        this.headLength = headLength;
    }

    @Override
    public void setHeadVersion(int headVersion) {
        this.headVersion = headVersion;
    }

    @Override
    public void setHandshakeType(int handshakeType) {
        this.handshakeType = handshakeType;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }


    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public static class Builder{
        private TSLHandshake handshake = new TSLHandshake();

        public Builder setContentType(int contentType) {
            handshake.setContentType(contentType);
            return this;
        }

        public Builder setHeadVersion(int headVersion) {
            handshake.setHeadVersion(headVersion);
            return this;
        }

        public Builder setHeadLength(int length) {
            handshake.setHeadLength(length);
            return this;
        }

        public Builder setHandshakeType(int type) {
            handshake.setHandshakeType(type);
            return this;
        }

        public Builder setLength(int len) {
            handshake.setLength(len);
            return this;
        }

        public Builder setVersion(int version) {
            handshake.setVersion(version);
            return this;
        }

        public Builder setRandom(Random random) {
            handshake.setRandom(random);
            return this;
        }

        public Builder setSeesion(Session session) {
            handshake.setSession(session);
            return this;
        }

        public TSLHandshake build() {
            return handshake;
        }


    }


}
