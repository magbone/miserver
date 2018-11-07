package server.utils.websocket.util.protocols;



public class Utils {
    public static byte[] sbBytes(byte[] src,int begin,int count){
        byte[] bs = new byte[count];
        System.arraycopy(src,begin,bs,0,count);
        return bs;
    }
}
