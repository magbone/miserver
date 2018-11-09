package server.utils.websocket.util.protocols.handshake;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * WebSocket Protocol
 */

public class Protocol {



    public static final int CONNECTING = 0;
    public static final int OPEN = 1;
    public static final int CLOSING = 2;
    public static final int CLOSED = 3;

    // The magic code
    private static final String CODE = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private StringBuffer sb = new StringBuffer();
    private Map<String,Object> objectMap = new HashMap<>();
    public Protocol(Map<String,Object> objectMap){
        this.objectMap = objectMap;
        sb.append("HTTP/1.1 101 Switching Protocol\n");
        sb.append("Upgrade:websocket\n");
        sb.append("Connection:Upgrade\n");
        sb.append("Sec-WebSocket-Accept:"+ secWebSocketAccept() + "\n");
        sb.append("\r\n");
    }

    @Override
    public String toString() {
        return sb.toString();
    }


    private String secWebSocketAccept() {
        String guid = (String)this.objectMap.get("Sec-WebSocket-Key") + CODE;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode(string2Sha1(guid));
        return new String(bytes);
    }

    public static boolean isWebSocketProtocol(String s){
        String[] s1 = s.split("\\s");
        int  i = 0;
        for (String s2:s1){
            if (s2.contains("websocket")) i++;
            if (s2.contains("Sec-WebSocket-Key")) i++;
        }
        return i == 2;
    }


    /**
     * Make String object to Sha1 byte
     * @param str
     * @return
     */
    private byte[] string2Sha1(String str){
        if (str == null|| str.length() == 0)
            return null;
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(str.getBytes("UTF-8"));

            byte[] mds = md.digest();
            int j = mds.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0;i < j; i++){
                byte b = mds[i];
                buf[k++] = hexDigits[b >>> 4 & 0xf];
                buf[k++] = hexDigits[b & 0xf];
            }
            return parseHexStr2Byte(new String(buf));
        }catch (NoSuchAlgorithmException|UnsupportedEncodingException e){
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Hex to binary
     * @param s
     * @return
     */
    private byte[] parseHexStr2Byte(String s){
        if (s == null || s.length() == 0)
            return null;
        byte[] result = new byte[s.length() / 2];

        for (int i = 0;i < s.length() / 2;i++){
            int high = Integer.parseInt(s.substring(i * 2,i * 2 +1),16);
            int low = Integer.parseInt(s.substring(i * 2 + 1,i * 2 + 2),16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}
