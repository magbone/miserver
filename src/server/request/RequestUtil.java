package server.request;

import java.io.UnsupportedEncodingException;

public class RequestUtil {

    public final static String CONTENT_TYPE = "Content-Type";
    // Some Content-Type
    public final static String URLENCODE = "application/x-www-form-urlencoded";

    public final static String FORM_DATA = "multipart/form-data";


    public final static String GBK_CODE = "GBK";

    public final static String UTF8_CODE = "UTF-8";
    /**
     * Get the post type
     * @param s
     * @param pattern
     * @return
     */

    public static int getContentType(String[] s,String pattern){
        for (int i = 0;i < s.length;i++){
            if (s[i].contains(pattern)){
                return i;
            }
        }
        return -1;
    }

    /**
     * URL decode
     * @param s
     * @return
     */
    public static String UrlDecode(String s){
        if (s == null)
            return "";
        try{
            return java.net.URLDecoder.decode(s,UTF8_CODE);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URL encode
     * @param s
     * @return
     */

    public static String URLEncode(String s){
        if (s == null){
            return "";
        }
        try {
            return java.net.URLEncoder.encode(s,UTF8_CODE);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
}
