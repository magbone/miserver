package server.request;

public class RequestUtil {

    public final static String CONTENT_TYPE = "Content-Type";
    // Some Content-Type
    public final static String URLENCODE = "application/x-www-form-urlencoded";

    public final static String FORM_DATA = "multipart/form-data";


    public static int getContentType(String[] s,String pattern){
        for (int i = 0;i < s.length;i++){
            if (s[i].contains(pattern)){
                return i;
            }
        }
        return -1;
    }

}
