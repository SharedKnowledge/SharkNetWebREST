package toolbox;

import java.util.HashMap;
import net.sharkfw.apps.sharknet.SharkNetPeerProfile;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.json.*;

public class JSON {
    private final HashMap<String, Object> body;
    
    public JSON() {
        body = new HashMap<>();
    }
    
    public JSON(String key, Object value) {
       body = new HashMap<>();
       body.put(key, value);
    }
    
    public JSONObject convertToJson(SharkNetPeerProfile peerProfile) throws SharkKBException {
        body.clear();
        body.put("name", peerProfile.getPeer().getName());
        
        return new JSONObject(body);
    }
    
    public String convertToString(SharkNetPeerProfile peerProfile) throws SharkKBException {
        return convertToJson(peerProfile).toString();
    }
    
    public void add(String key, String value) {
        body.put(key, value);
    }
    
    public void add(String key, int value) {
        body.put(key, value);
    }
    
    public void add(String key, JSONObject value) {
        body.put(key, value);
    }
    
    public void add(String key, String[] values) {
        for (String string: values) {
            body.put(key, string);
        }
    }
    
    public String toJSONString() {
        return getBody().toString();
    }
    
    public JSONObject getBody() {
        return new JSONObject(body);
    }
}
