package server.response;

import server.ServerClose;
import server.response.parser.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Response implements ServerClose {
    private OutputStream out;

    private StringBuffer responseBuffer = new StringBuffer();


    private String templatesDir;

    //private SocketChannel socketChannel;

    public Response(OutputStream out){
        this.out = out;
        this.writeHead();
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
        String end = s.substring(s.length());
        if(end.contains("\n")){
            String s1 = s + "\n";
            responseBuffer.append(s1);
        }else{
            responseBuffer.append(s);
        }

    }
    public void write(String s){
        String end = s.substring(s.length());
        if (end.contains("\n")){
            String s1 = s + "\n";
            responseBuffer.append(s1);
        }
        else responseBuffer.append(s);
    }
    public void write(byte[] bytes){
        String s = new String(bytes);
        String end = s.substring(s.length());
        if (end.contains("\n")){
            String s1 = s + "\n";
            responseBuffer.append(s1);
        }
        else responseBuffer.append(s);
    }

    /**
     *
     * @param html This is relative path url;
     * @param values
     */
    public void write(String html, Map<String,Object> values){
        String line = null;
        StringBuffer stringBuffer = new StringBuffer();
        try{
            BufferedReader in = new BufferedReader(new FileReader(templatesDir + html));
            line = in.readLine();
            while (line != null) {
                if (values == null)
                responseBuffer.append(line);
                stringBuffer.append(line);
                line = in.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        if (values == null) return;
        Parser parser = new Parser(stringBuffer.toString(),values);
        responseBuffer.append(parser.toHTML());
    }

    public void write(String html,Object o){

    }
    public void flush() throws IOException{

        if (out == null) return;
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
        out.close();
    }
}
