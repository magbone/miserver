package server.response.parser;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import server.app.OnRenderListener;


import java.io.*;
import java.util.Map;

public class Parser {
    private String html;

    private Map<String,Object> values;
    private OnRenderListener listener;

    public Parser(String html, Map<String,Object> values){
        this.html = html;
        this.values = values;

    }
    public void doRender(){
        render(html,values);
    }
    private void render(String html,Map<String,Object> values){
        //Velocity.init();
        String templateHtml = this.html;
        if (values == null){
            try {
                FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "/src/" + html);
                StringBuilder sb = new StringBuilder();
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) != -1){
                    sb.append(new String(buf,0,len));
                }
                this.html = sb.toString();
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }
        listener.beforeRender(templateHtml);
        // run the templates
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        listener.onRender(templateHtml);
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String,Object> entry:values.entrySet())
        context.put(entry.getKey(), entry.getValue());
        StringWriter writer = new StringWriter();
        Template template = engine.getTemplate(html);
        template.merge(context, writer);
        listener.afterRender(templateHtml,writer.toString());
        this.html = writer.toString();
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public String toHTML(){
        return this.html;
    }

    public void setOnRenderListener(OnRenderListener listener){
        this.listener = listener;
    }
}
