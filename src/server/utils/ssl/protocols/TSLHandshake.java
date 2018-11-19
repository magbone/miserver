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


    private List<Byte> byteList = new ArrayList<>();
    //Create handshake to client
    private TSLHandshake(){
        byteList.clear();
    }

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
                // do server hello

                TSLHandshake tslHandshake = TSLHandshake.createHandshake();
                tslHandshake.setHandshakeType(HANDSHAKETYPE_SERVER_HELLO);
                tslHandshake.setLength(93);
                tslHandshake.setVersion(3,3);
                TSLRecord tslRecord = TSLRecord.createRecord();
                tslRecord.setContentType(TSLRecord.CONTENTTYPE_HANDSHAKE);
                tslRecord.setProtocolVersion(3,3);
                tslRecord.setLength(97);


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



    public int getHandshakeType() {
        return handshakeType;
    }

    //Create a TSL handshake

    public static TSLHandshake createHandshake(){
        return new TSLHandshake();
    }


    //Set the handshake type,such as server hello
    public void setHandshakeType(int handshakeType) {
        this.byteList.add((byte) handshakeType);
    }

    //Set the handshake package length
    public void setLength(int length) {
        byte[] bytes = Utils.int2Bytes3(length);
        for (byte b:bytes) this.byteList.add(b);
    }

    //Set the handshake version
    public void setVersion(int a,int b){
        this.byteList.add((byte)a);
        this.byteList.add((byte)b);
    }

    //Set Unix time

    public void setUnixtime(long unixTime){

    }
    public byte[] getBytes(){
        byte[] bytes = new byte[this.byteList.size()];
        for (int i = 0;i < bytes.length;i++) bytes[i] = this.byteList.get(i);
        return bytes;
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

        //cipher suites type
        public final static int TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = 0xc02c;
        public final static int TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = 0xc02b;
        public final static int TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = 0xc024;
        public final static int TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = 0xc023;
        public final static int TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = 0xc00a;
        public final static int TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = 0xc009;
        public final static int TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = 0xcca9;
        public final static int TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = 0xc030;
        public final static int TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = 0xc02f;
        public final static int TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = 0xc028;
        public final static int TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = 0xc027;
        public final static int TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = 0xc014;
        public final static int TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = 0xc013;
        public final static int TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = 0xcca8;
        public final static int TLS_RSA_WITH_AES_256_GCM_SHA384 = 0x009d;
        public final static int TLS_RSA_WITH_AES_128_GCM_SHA256 = 0x009c;
        public final static int TLS_RSA_WITH_AES_256_CBC_SHA256 = 0x003d;
        public final static int TLS_RSA_WITH_AES_128_CBC_SHA256 = 0x003c;
        public final static int TLS_RSA_WITH_AES_256_CBC_SHA = 0x0035;
        public final static int TLS_RSA_WITH_AES_128_CBC_SHA = 0x002f;
        public final static int TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = 0xc008;
        public final static int TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = 0xc012;
        public final static int TLS_RSA_WITH_3DES_EDE_CBC_SHA = 0x000a;

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
