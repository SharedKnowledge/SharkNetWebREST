package toolbox;

import java.util.HashMap;
import org.json.*;

public class JSON {
    private final HashMap<String, Object> body;
    
    public JSON(String key, Object value) {
       body = new HashMap<>();
       body.put(key, value);
    }
    
    public void add(String key, Object value) {
        body.put(key, value);
    }
    
    public String toJSONString() {
        return getBody().toString();
    }
    
    public JSONObject getBody() {
        return new JSONObject(body);
    }
}
