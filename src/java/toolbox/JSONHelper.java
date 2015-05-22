package toolbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
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
    
    public void render(HttpServletResponse response, SharkNetPeerProfile peerProfile) throws SharkKBException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(convertToString(peerProfile));
    }
    
    public JSONObject convertToJson(SharkNetPeerProfile peerProfile) throws SharkKBException {        
        return new JSONObject(peerProfile);
    }
    
    public String convertToString(SharkNetPeerProfile peerProfile) throws SharkKBException {
        return convertToJson(peerProfile).toString();
    }
}
