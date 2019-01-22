package server.utils.ssl.protocols.record;


import server.utils.ssl.protocols.Bytes;
import server.utils.ssl.protocols.ProtocolVersion;
import server.utils.ssl.protocols.handshake.TSLHandshake;
import server.utils.ssl.protocols.handshake.TSLHandshakehandler;

import javax.xml.ws.ProtocolException;

public class TSLRecord implements Bytes{

    // Set final values
    //ContentType
    public static final int CONTENTTYPE_CHANGE_CIPHER_SPEC = 20; // change cipher spec
    public static final int CONTENTTYPE_ALERT = 21; // alert
    public static final int CONTENTTYPE_HANDSHAKE = 22; // handshake
    public static final int CONTENTTYPE_APPLICATION_DATA = 23; // application data

    private byte[] bytes;

    public TSLRecord(byte[] bytes){
        this.bytes = bytes;
        doRecord();
    }

    private void doRecord() {
        int index = 0;

        switch (bytes[index++]) {
            case CONTENTTYPE_HANDSHAKE:
                doHandshake(index);
                break;
            case CONTENTTYPE_ALERT:
                break;
            case CONTENTTYPE_CHANGE_CIPHER_SPEC:
                break;
            case CONTENTTYPE_APPLICATION_DATA:
                break;
            default:
                throw new ProtocolException("No such content type");
        }
    }

    public void doHandshake(int index) {
        ProtocolVersion verision = new ProtocolVersion(bytes[index++],bytes[index++]);
        index = index + 2;
        switch (bytes[index++]){
            case TSLHandshake.HANDSHAKETYPE_CLIENT_HELLO:
                TSLHandshakehandler.clientHello(bytes,index);
                break;
        }
    }

    public void doAlert(int index) {

    }

    public void doChangeCipherSpec(int index) {

    }

    public void doApplictionData(int index) {

    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
