package helper;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.json.*;

/**
 * 
 * @author Paul Kujawa
 */
public class APIResponse {
    public static final String ctJSON = "application/json;charset=UTF-8";
    
    /**
     * 
     * @param response
     * @param ErrorCode
     * @throws SharkKBException
     * @throws IOException 
     */
    public static void render(HttpServletResponse response, int ErrorCode) 
        throws SharkKBException, IOException {   
        response.setStatus(ErrorCode);
        render(response, null);
    }
    
    /**
     * 
     * @param response
     * @param json
     * @throws SharkKBException
     * @throws IOException 
     */
    public static void render(HttpServletResponse response, JSONObject json) 
        throws SharkKBException, IOException {   
        response.setContentType(ctJSON);
        response.getWriter().println(json);
    }
}
