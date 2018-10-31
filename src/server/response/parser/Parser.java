package server.response.parser;

import server.response.parser.util.SyntaxUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {
    private String html;
    private Map<String,Object> values;
    private Object object;

    private ParserContainer parserContainer = new ParserContainer();
    public Parser(String html, Map<String,Object> values){
        this.html = html;
        this.values = values;
        // the value
        String lockedRead = parserContainer.handleRead(html);
        List<String> key = SyntaxUtil.getKey(lockedRead,SyntaxUtil.VALUE);
        valid(key,values);
        replace(key,values);
        // the if
        /*
        List<String> ifKey = SyntaxUtil.getKey(html,SyntaxUtil.IF);
        System.out.println(ifKey.size());
        for (String s:ifKey){
            System.out.println(s);
        }
        // the for
        List<String> forKey = SyntaxUtil.getKey(html,SyntaxUtil.FOR);
        System.out.println(forKey.size());
        for (String s: forKey){
            System.out.println(s);
        }
        /*
        if (syntaxEqual(ifKey)){
            //do something
        }*/

        Analyzer analyzer = Analyzer.preProcess(this.html);
        System.out.println(analyzer.toString());
        analyzer.toDOM();
    }
    public Parser(String html,Object object){
        this.html = html;
        this.object = object;

    }

    private void valid(List<String> key,Map<String,Object> values){
        Set<String> keys = values.keySet();
        for (String s1: key){
            if (!keys.contains(s1.replace(" ",""))){
                throw new NoSuchValueException("Don't have the value:" + s1);
                //break;
            }
        }
    }
    private void replace(List<String> key,Map<String,Object> values){
        for (String s1: key){
            //s1 = s1.replace(" ","");
            String s = values.get(s1.replace(" ","")) == null ? "null" : (String)values.get(s1.replace(" ",""));
            this.html = parserContainer.handleWrite(this.html,"{{" + s1 + "}}",s);
        }
    }
    private void syntaxEqual(List<String> s){
        for (String value :s){
            if (value.contains(SyntaxUtil.MORE)){
                //The valueLeft's type will be changed same as valueRight's type
                String valueLeft = value.split(SyntaxUtil.MORE)[0];
                String valueRight = value.split(SyntaxUtil.MORE)[1];
                //To let the compare simply,I just transform them to float

            }
        }
    }
    public Map<String, Object> getValues() {
        return values;
    }

    public Object getObject() {
        return object;
    }

    public String toHTML(){
        return this.html;
    }
}
