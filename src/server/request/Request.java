package server.request;

import com.sun.istack.internal.NotNull;
import server.AbstractServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Request {
    private String url;
    private String action;
    private StringBuffer httpRequest;

    private Map<String,Object> getParams = new HashMap<>();
    private Map<String,Object> requestHeaders = new HashMap<>();

    private InputStream inputStream;
    private OnRequest onRequest;
    public Request(@NotNull InputStream inputStream){
        this.inputStream = inputStream;
    }

    public final void doRequest() throws IOException{
        onRequest.onStart();
        httpRequest = new StringBuffer();
        byte[] bytes = new byte[1024 * 1024];
        int len = 0;
        if ((len = inputStream.read(bytes)) > 0){
            httpRequest.append(new String(bytes,0,len));
        }
        if (httpRequest.toString().length() <= 0) throw new NoRequestException();
        String httpHead = httpRequest.toString().split("\n")[0];
        action = httpHead.split("\\s")[0];
        //System.out.println(action);
        System.out.println(httpRequest.toString());
        getRequestHead(httpRequest.toString());
        if (action.equals(AbstractServer.GET)){
            getGetParams(httpHead);
        }
        else if (action.equals(AbstractServer.POST)){
            getPostParams(httpRequest.toString());
        }
        onRequest.doRequest();
    }
    private void getGetParams(String s){
        getParams.clear();
        String url = s.split("\\s")[1];
        //url likes "/login?token=123&limit=123"
        if(url.contains("?")){
            this.url = url.split("\\?")[0];
            String afterUrlCut = url.substring(url.indexOf("?") + 1);
            // afterUrlCut likes "token=123&limit123"
            // This rule is too simple;
            String[] elParams = afterUrlCut.split("&");
            for (String s1:elParams){
                // s1 likes "token=123"
                String[] s2 = s1.split("=");
                // s2 likes "token 123"
                String[] params = new String[2];
                for (int i = 0;i < s2.length;i++){
                    params[i] = RequestUtil.UrlDecode(s2[i]);
                }
                getParams.put(params[0],params[1]);
            }
        }else{
            this.url = url;
        }
    }
    private void getPostParams(String s){
        getParams.clear();
        // like '/loginIn'
        this.url = s.split("\\s")[1];
        String[] params = s.split("\\s");
        // get the ContentType index
        int contentTypeIndex = RequestUtil.getContentType(params,RequestUtil.CONTENT_TYPE);
        String contentType = params[contentTypeIndex + 1];
        if (contentType.contains(RequestUtil.URLENCODE)){
            String postContent = params[params.length - 1];
            String[] elParams = postContent.split("&");
            for (String s1:elParams){
                // s1 likes "token=123"
                String[] s2 = s1.split("=");
                // s2 likes "token 123"
                String[] s3 = new String[2];
                for (int i = 0;i < s2.length;i++){
                    s3[i] = RequestUtil.UrlDecode(s2[i]);
                }
                getParams.put(s3[0],s3[1]);
            }
        }


    }
    private void getRequestHead(String html)throws IndexOutOfBoundsException{
       String[] s = html.split("\\\r\\\n\\\r\\\n")[0].split("\\\r\\\n");
       for(String s1: s){
           int first = s1.indexOf(":");
           if (first == -1) continue;
           if (!s1.contains(AbstractServer.GET ) || !s1.contains(AbstractServer.POST)){
               requestHeaders.put(s1.substring(0,first).trim(),s1.substring(first + 1).trim());
           }
       }

    }
    public String getParam(String key){
        if (getParams.size() == 0)
            return null;
        return (String)getParams.get(key);
    }

    public String getHead(String key){
        if (requestHeaders.size() == 0) return null;
        return (String)requestHeaders.get(key);
    }
    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return httpRequest.toString();
    }

    public void addOnRequest(OnRequest onRequest){
        this.onRequest = onRequest;
    }
    public interface OnRequest {
        void onStart();
        void doRequest();
    }
}
