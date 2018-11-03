package server.app.sql;



import java.util.HashMap;
import java.util.Map;

public class DataValues {
    private Map<String,Object> map = new HashMap<>();

    public void put(String key, Object value){
        map.put(key,value);
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
