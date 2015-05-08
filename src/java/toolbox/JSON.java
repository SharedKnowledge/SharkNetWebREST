package toolbox;

import java.util.HashMap;
import org.json.*;

public class JSON {
    private final HashMap<String, Object> body;
    
    public JSON(String key, Object value) {
       body = new HashMap<>();
       body.put(key, value);
    }
    
    public String toJSONString() {
        return new JSONObject(body).toString();
    }
}
