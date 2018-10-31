package server.response.parser;



import server.response.parser.util.SyntaxUtil;

import java.util.HashMap;
import java.util.Map;

class Analyzer {

    final static String END_IF = "endif";
    final static String END_FOR = "endfor";
    final static String IF = "if";
    final static String FOR = "for";

    private String html;
    //
    private static Map<Integer,String> anMap = new HashMap<>();
    private Analyzer(String html){
        this.html = html;
    }
    static Analyzer preProcess(String ss){
        int firstAnIndex = ss.indexOf(SyntaxUtil.HEAD_AN);
        int lastAnIndex = ss.indexOf(SyntaxUtil.END_AN);
        if (firstAnIndex == -1 || lastAnIndex == -1){
            return new Analyzer(ss);
        }
        anMap.clear();
        StringBuffer afterReplace = new StringBuffer(ss);
        while (lastAnIndex != -1 && firstAnIndex != -1){
            afterReplace = afterReplace.replace(firstAnIndex,lastAnIndex + SyntaxUtil.END_AN.length(),"");
            String s1 = afterReplace.substring(firstAnIndex,lastAnIndex + SyntaxUtil.END_AN.length());
            anMap.put(firstAnIndex,s1);
            firstAnIndex = afterReplace.toString().indexOf(SyntaxUtil.HEAD_AN);
            lastAnIndex = afterReplace.toString().indexOf(SyntaxUtil.END_AN);
        }
        return new Analyzer(afterReplace.toString());

    }

    public void toDOM(){
        if (html == null) throw new NullPointerException();
        if (html.indexOf('{') == -1 && html.indexOf('}') == -1) return;
        else if (html.indexOf('{') != -1 && html.indexOf('}') == -1) throw new IllegalArgumentException("lack the '}' in the end");
        else if (html.indexOf('{') == -1 && html.indexOf('}') != -1) throw new IllegalArgumentException("lack the '{' in the first");

        int firstHeadIndex = html.indexOf('{');
        int firstEndIndex = html.indexOf('}');

        String firstElement = html.substring(firstHeadIndex,firstEndIndex + 1);
        // the element like this
        /*
        * { hello }
        * { for sb in sb }
        * { if sb }
        * { if sb = 0}
        * { endif}
        * { endfor }
         */
        validElement(firstElement);
        switch (getElementType(firstElement)){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }

    }

    private void validElement(String s){
        if (s.contains("{") || s.contains("}")) throw new IllegalArgumentException("element could not contain '{' or '}'");
    }

    private int getElementType(String s){
        //{ endif }
        if (s.equals(END_IF)) return 0;
        //{ endfor }
        else if (s.equals(END_FOR)) return 1;
        else if (s.equals(IF)){
            String s1 = s.split("\\s")[0];
            if (s1.trim().equals(IF))
                //{ if hello}
                return 2;
            else return 4;
            //{ hello }
        }else if (s.equals(FOR)){
            String s1 = s.split("\\s")[0];
            if (s1.trim().equals(FOR))
                // { for value in values }
                return 3;
            // { hello }
            else return 4;
        }
        else {
            // { hello }
            return 4;
        }

    }
    //private void
    @Override
    public String toString() {
        return html;
    }
}
