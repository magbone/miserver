package server.response;


import java.io.*;
import java.util.zip.GZIPInputStream;

public class ResponseUtil {

    public final static String NOT_FOUND = "404";

    public final static String OK = "200";

    public final static String FORBIDDEN = "403";


    public final static String NOT_FOUND_URL = "./templates/404.html";
    public final static String FORBIDDEN_URL = "./templates/403.html";
    public static void response404 (Response response){
        response.write("Content-Type:text/html");
        String line = null;
        try{
            BufferedReader in = new BufferedReader(new FileReader(NOT_FOUND_URL));
            line = in.readLine();
            while (line != null){
                response.write(line);
                line = in.readLine();
            }
            response.flush();
            response.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void response403(Response response){
        response.write("Content-Type:text/html");
        String line = null;
        try{
            BufferedReader in = new BufferedReader(new FileReader(FORBIDDEN_URL));
            line = in.readLine();
            while (line != null){
                response.write(line);
                line = in.readLine();
            }
            response.flush();
            response.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);            }        }
                catch (IOException e) {                }         return out.toByteArray();    }


}
