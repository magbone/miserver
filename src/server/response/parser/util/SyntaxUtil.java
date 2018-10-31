package server.response.parser.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxUtil {

    public final static String VALUE = "(?<=\\{\\{)(.+?)(?=\\}\\})";

    public final static String START_IF = "(\\{ if .+? \\})";

    public final static String END_IF = "\\{ endif \\}";

    public final static String GREATER_THAN = "(.*?) > (.*?)";

    public final static String GREATER_THAN_EQUAL = "(.*?) >= (.*?)";

    public final static String EQUAL = "(.*?) == (.*?)";

    public final static String LESS_THAN_EQUAL = "(.*?) <= (.*?)";

    public final static String LESS_THAN = "(.*?) < (.*?)";

    public final static String START_FOR = "\\{ for .* \\}";

    public final static String END_FOR = "\\{ endfor \\}";

    public final static String VALUE_INDEX = "\\( (.*?),(.*?)\\)";

    public final static String IF = "(\\{ if (\\w)(.*)\\} .* \\{ endif \\})";

    public final static String FOR = "\\{ for (\\w)(.*)\\} .* \\{ endfor \\}";

    public final static String ELEMENT = "\\{ .* \\}";


    public final static String MORE = ">";
    public final static String LESS = "<";
    public final static String MORE_EQUAL = ">=";
    public final static String LESS_EQUAL = "<=";


    public final static String HEAD_AN = "<!--";
    public final static String END_AN = "-->";

    public static List<String> getKey(String s,String pattern){
        List<String> list = new ArrayList<>();
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(s);
        int i = 0;
        while (matcher.find()){
            if (matcher.group(i).length() == 0) throw new EmptyPatternException("the key is null in " + i);
            list.add(matcher.group(i));
        }
        return list;
    }

}
