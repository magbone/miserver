package server.utils.ssl.protocols;

import server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TSLRecord {

    // Set final values
    //ContentType
    public static final int CONTENTTYPE_CHANGE_CIPHER_SPEC = 20; // change cipher spec
    public static final int CONTENTTYPE_ALERT = 21; // alert
    public static final int CONTENTTYPE_HANDSHAKE = 22; // handshake
    public static final int CONTENTTYPE_APPLICATION_DATA = 23; // application data


    private byte[] bytes;
    private String[] data;
    //TSLPlaintext;
    /****
     * ContentType type
     * ProtocolVersion version
     * uint16 length
     * opaque fragment[TLSPlaintext.length]
     */



    private int contentType;
    private ProtocolVersion protocolVersion;
    private int length;


    //response as TSLRecord
    private List<Byte> bytesRe = new ArrayList<>();
    //this constructor to createTSLRecord
    private TSLRecord(){
        this.bytesRe.clear();
    }

    //this constructor to getTSLRecord
    public TSLRecord(byte[] bytes){
        System.out.println(Utils.bytes2String(bytes));
        data = Utils.bytes2String(bytes).split(",");
        doRecord(data);
    }

    private void doRecord(String[] data){
        int index = 0;
        contentType = Integer.parseInt(data[index++],16);
        protocolVersion = new ProtocolVersion(Integer.parseInt(data[index++],16),Integer.parseInt(data[index++],16));
        System.out.println(protocolVersion.toString());

        length = Integer.parseInt(data[index++]+data[index++],16);
        System.out.println(length);
        switch (contentType){
            case CONTENTTYPE_HANDSHAKE:
                TSLHandshake tslHandshake = new TSLHandshake(data,index);
                break;
            case CONTENTTYPE_ALERT:
                break;
            case CONTENTTYPE_APPLICATION_DATA:
                break;
            case CONTENTTYPE_CHANGE_CIPHER_SPEC:
                System.out.println("22la");
                break;
            default:
                break;

        }

    }
    public static TSLRecord createRecord(){
        return new TSLRecord();
    }

    public void setContentType(int handShake){
        this.bytesRe.add((byte) handShake);
    }

    public void setProtocolVersion(int major,int minor){
        this.bytesRe.add((byte) major);
        this.bytesRe.add((byte) minor);
    }

    public void setLength(int length){
        byte[]bytes = Utils.int2Bytes2(length);
        for (byte b:bytes) this.bytesRe.add(b);
    }

    public void setHandshake(TSLHandshake handshake){
        for (byte b: handshake.getBytes())
        this.bytesRe.add(b);
    }

    public byte[] getBytes(){
        byte[] bytes = new byte[bytesRe.size()];
        for (int i = 0;i < bytes.length;i++) bytes[i] = bytesRe.get(i) ;
        return bytes;
    }
    static class ProtocolVersion{
        /***
         * TLS 1.2 major = 3 minor = 3
         * TLS 1.0 major = 3 minor = 0
         */
        private int major;
        private int minor;

        public ProtocolVersion(int major,int minor){
            this.major = major & 0x00ff;
            this.minor = minor & 0x00ff;
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public void setMajor(int major) {
            this.major = major & 0x00ff;
        }

        public void setMinor(int minor) {
            this.minor = minor & 0x00ff;
        }
        @Override
        public String toString() {
            return "major:" + major +",minor:" + minor;
        }
    }
}
