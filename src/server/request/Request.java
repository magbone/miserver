package server.request;

import server.AbstractServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Request {
    private String url;
    private String action;
    private StringBuffer httpRequest;

    private Map<String,Object> getParams = new HashMap<>();

    private Map<String,Object> messages = new HashMap<>();
    public Request(InputStream inputStream) throws IOException{
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
        //System.out.println(httpRequest.toString());
        if (action.equals(AbstractServer.GET)){
            getGetParams(httpHead);
        }
        else if (action.equals(AbstractServer.POST)){
            getPostParams(httpRequest.toString());
        }
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
                    params[i] = s2[i];
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
                    s3[i] = s2[i];
                }
                getParams.put(s3[0],s3[1]);
            }
        }


    }
    public String getParam(String key){
        if (getParams.size() == 0)
            return null;
        return (String)getParams.get(key);
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
}
