package server.response;

import com.sun.istack.internal.NotNull;
import server.ServerClose;
import server.app.OnRenderListener;
import server.response.parser.Parser;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPOutputStream;


public class Response implements ServerClose {
    private OutputStream out;

    private StringBuffer responseBuffer = new StringBuffer();
    private OnRenderListener listener;

    private String templatesDir;

    //private SocketChannel socketChannel;

    private OnResponse onResponse;

    public Response(OutputStream out){
        this.out = out;
        //this.writeHead();
    }
    // This is default head
    public void writeHead(){
        responseBuffer.append("HTTP/1.1 200 OK\n");
    }

    /**
     * write header;
     * @param s
     */
    public void writeHead(String s){
        this.writeHead();
        responseBuffer.append(s + "\n");


    }
    public void write(String s){
         responseBuffer.append("\r\n" + s);
    }
    public void write(byte[] bytes){
        responseBuffer.append("\r\n" + new String(bytes));
    }

    /**
     *
     * @param html This is relative path url;
     * @param values
     */
    public void write(@NotNull String html, Map<String,Object> values) throws IOException{
        Parser parser = new Parser(templatesDir + html,values);
        parser.setOnRenderListener(listener);
        parser.doRender();

        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        gzipOutputStream.write("hello".getBytes(StandardCharsets.UTF_8));
        //System.out.println(new String(ResponseUtil.uncompress(outputStream.toByteArray())));
        gzipOutputStream.close();
        */
        responseBuffer.append("\r\n" +parser.toHTML());
    }

    public void flush() throws IOException{

        if (out == null) return;
        this.onResponse.doOnResponse();

        out.write(responseBuffer.toString().getBytes());

        /*if (socketChannel == null) return;
        socketChannel.write(ByteBuffer.wrap(responseBuffer.toString().getBytes()));*/
    }

    public final void setTemplatesDir(String templatesDir){
        this.templatesDir = templatesDir;
    }
    @Override
    public String toString(){
        return responseBuffer.toString();
    }
    @Override
    public void close() throws IOException{
        this.onResponse.onDestroy();
        out.close();

    }

    public void addOnResponse(OnResponse onResponse){
        this.onResponse = onResponse;
    }
    public interface OnResponse {
        void doOnResponse();
        void onDestroy();
    }

    public void setOnRenderListener(OnRenderListener listener){
        this.listener = listener;
    }
}
