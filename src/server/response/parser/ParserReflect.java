package server.response.parser;

import java.util.ArrayList;
import java.util.List;

public class ParserReflect {
    private Object object;

    private List<String> methodNames = new ArrayList<>() ;

    private ParserReflect(){

    }
    public ParserReflect(Object object){
        this.object = object;
        methodNames.clear();
    }


    protected void setMethodNames(){

    }

}
