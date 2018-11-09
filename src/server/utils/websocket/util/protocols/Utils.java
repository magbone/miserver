package server.utils.websocket.util.protocols;



public class Utils {
    public static byte[] sbBytes(byte[] src,int begin,int count){
        byte[] bs = new byte[count];
        System.arraycopy(src,begin,bs,0,count);
        return bs;
    }

    public static byte[] copyByte(byte[] bytes1,byte[] bytes2){
        byte[] bs = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1,0,bs,0,bytes1.length);
        System.arraycopy(bytes2,0,bs,bytes1.length, bytes2.length);
        return bs;
    }
}
