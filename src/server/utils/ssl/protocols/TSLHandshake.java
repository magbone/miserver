package server.utils.ssl.protocols;

import server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TSLHandshake {

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




    private int handshakeType;
    private int length;
    private TSLRecord.ProtocolVersion protocolVersion;
    private Random random;
    private Session session;
    private CipherSuits cipherSuits;

    public TSLHandshake(String[] data,int index){
        int c = index;
        //handshake type
        handshakeType = Integer.parseInt(data[c++],16);

        //handshake length
        length = Integer.parseInt(data[c++] + data[c++] + data[c++],16);

        //protocol version
        protocolVersion = new TSLRecord.ProtocolVersion(Integer.parseInt(data[c++],16),Integer.parseInt(data[c++],16));

        //Random
        random = new Random(data[c++] + data[c++] + data[c++] + data[c++],
                Utils.strings2String(data,index,28));
        c += 28;

        //Session
        session = new Session();
        session.setSessionIdLen(Integer.parseInt(data[c++],16));
        session.setSessionId(Utils.strings2String(data,index,session.getSessionIdLen()));
        c += session.getSessionIdLen();
        System.out.println(session.getSessionIdLen());

        //cipher suits
        cipherSuits = new CipherSuits();
        cipherSuits.setCipherSuitsLen(Integer.parseInt(data[c++] + data[c++],16 ));
        for (int i = c;i - c < cipherSuits.getCipherSuitsLen() / 2;i += 2) cipherSuits.setCipherSuits(data[i] + data[ i + 1]);
        c += cipherSuits.getCipherSuitsLen();
        System.out.println(cipherSuits.getCipherSuitsLen());

        // Compression methods
            //...
        // Extensions
            //...
        switch (handshakeType){
            case HANDSHAKETYPE_HELLO_REQUEST:
                System.out.println("Hello Request");
                break;
            case HANDSHAKETYPE_CLIENT_HELLO:
                System.out.println("Client Hello");
                break;
            case HANDSHAKETYPE_SERVER_HELLO:
                // We are the server
                break;
            case HANDSHAKETYPE_CERTIFICATE:
                break;
            case HANDSHAKETYPE_SERVER_KEY_EXCHANGE:
                break;
            case HANDSHAKETYPE_CERTIFICATE_REQUEST:
                break;
            case HANDSHAKETYPE_SERVER_HELLO_DONE:
                break;
            case HANDSHAKETYPE_VERIFY:
                break;
            case HANDSHAKETYPE_CLIENT_KEY_EXCHANGE:
                break;
            case HANDSHAKETYPE_FINISHED:
                break;
                default:
                    break;
        }

    }

    public void setHandshakeType(int handshakeType) {
        this.handshakeType = handshakeType;
    }

    public int getHandshakeType() {
        return handshakeType;
    }

    /**
     * Random
     *
     *
     */

    static class Random{
        private String unixTime;
        private String randomHex;

        public Random(String unixTime,String randomHex){
            this.randomHex = randomHex;
            this.unixTime = unixTime;
        }

        public void setRandomHex(String randomHex) {
            this.randomHex = randomHex;
        }

        public void setUnixTime(String unixTime) {
            this.unixTime = unixTime;
        }

        public String getUnixTime() {
            return unixTime;
        }

        public String getRandomHex() {
            return randomHex;
        }
    }

    /***
     *
     * Session
     *
     */
    static class Session{
        private int sessionIdLen;
        private String sessionId;

        public Session(){

        }

        public Session(int sessionIdLen,String sessionId){
            this.sessionId = sessionId;
            this.sessionIdLen = sessionIdLen;
        }

        public void setSessionIdLen(int sessionIdLen) {
            this.sessionIdLen = sessionIdLen;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getSessionIdLen() {
            return sessionIdLen;
        }

        public String getSessionId() {
            return sessionId;
        }
    }

    /**
     * Cipher
     */
    static class CipherSuits{
        private List<String> cipherSuits = new ArrayList<>();
        private int cipherSuitsLen;

        public CipherSuits(){
            cipherSuits.clear();
        }

        public void setCipherSuits(String s) {
            this.cipherSuits.add(s);
        }

        public void setCipherSuitsLen(int cipherSuitsLen) {
            this.cipherSuitsLen = cipherSuitsLen;
        }

        public int getCipherSuitsLen() {
            return cipherSuitsLen;
        }

        public List<String> getCipherSuits() {
            return cipherSuits;
        }
    }
}
