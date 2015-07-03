package helper;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.json.*;

/**
 * 
 * @author Felix Brix
 * @author Paul Kujawa
 */
public class JSONHelper {
    private final String contentType;
    
    public JSONHelper() {
        contentType = "application/json;charset=UTF-8";
    }
    
    public void render(HttpServletResponse response, JSONArray array) throws SharkKBException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType(contentType);
        
        response.getWriter().println(array);
    }
    
    public void render(HttpServletResponse response, Object object) throws SharkKBException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType(contentType);
        
        response.getWriter().println(convertToJson(object));
    }
    
    /**
     * Renders any error responses
     * @param response
     * @param ErrorCode
     * @throws SharkKBException
     * @throws IOException 
     */
    public void renderError(HttpServletResponse response, int ErrorCode) throws SharkKBException, IOException {
        JSONArray JSONresponse = new JSONArray();
        
        switch (ErrorCode) {
            case 400:
                response.setHeader("Bad Request", "400");
                JSONresponse.put( new JSONObject() );
                break;
            case 424:
                response.setHeader("Failed Dependency", "424");
                JSONresponse.put( new JSONObject() );
                break;
            default:
                response.setHeader("Internal Server Error", "500");
                JSONresponse.put( new JSONObject() );
        }
        
        response.setContentType(contentType);
        response.getWriter().println( convertToJson(JSONresponse) );
    }
            
            
            
    public JSONObject convertToJson(Object object) throws SharkKBException {        
        return new JSONObject(object);
    }
}
