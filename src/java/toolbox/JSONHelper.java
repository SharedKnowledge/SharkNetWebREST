package toolbox;

import java.util.HashMap;
import net.sharkfw.apps.sharknet.SharkNetPeerProfile;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.json.*;

public class JSONHelper {
    private final HashMap<String, Object> body;
    
    public JSONHelper() {
        body = new HashMap<>();
    }
    
    public JSONHelper(String key, Object value) {
       body = new HashMap<>();
       body.put(key, value);
    }
    
    public JSONObject convertToJson(SharkNetPeerProfile peerProfile) throws SharkKBException {        
        return new JSONObject(peerProfile);
    }
    
    public String convertToString(SharkNetPeerProfile peerProfile) throws SharkKBException {
        return convertToJson(peerProfile).toString();
    }
}
