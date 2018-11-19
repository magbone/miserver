package server.utils;

public class Utils {

    /**
     *
     * @param bytes
     * @return
     */
    public static short bytes2short(byte[] bytes){
       short l = 0;
       for (int i = 0;i < 2;i++){
           l <<= 8;
           l |= (bytes[i] & 0xff);
       }
       return l;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes){
        int n = 0;
        int temp = 0;
        for (int i = 0;i < bytes.length;i++){
            n <<= 8;
            temp = bytes[i] & 0xff;
            n |= temp;
        }
        return n;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String bytes2String(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < bytes.length;i++){
            sb.append(byte2String(bytes[i]));
            if ((i + i) % 2 == 0) sb.append(",");
        }
        return sb.toString();
    }

    /**
     *
     * @param b
     * @return
     */
    public static String byte2String(byte b){
        String s = Integer.toHexString(b & 0xFF);
        return s.length() == 1 ? "0" + s : s;
    }

    public static String strings2String(String[] ss,int index, int count){
       StringBuffer buffer = new StringBuffer();
       for (int i = index;i - index < count;i++){
           buffer.append(ss[i]);
       }
       return buffer.toString();
    }
}
