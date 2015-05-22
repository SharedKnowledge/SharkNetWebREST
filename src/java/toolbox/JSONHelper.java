package toolbox;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.json.*;

public class JSONHelper {
    private final String contentType;
    
    public JSONHelper() {
        contentType = "application/json;charset=UTF-8";
    }
    
    public void render(HttpServletResponse response, Object object) throws SharkKBException, IOException {
        response.setContentType(contentType);
        response.getWriter().println(convertToString(object));
    }
    
    public JSONObject convertToJson(Object object) throws SharkKBException {        
        return new JSONObject(object);
    }
    
    public String convertToString(Object object) throws SharkKBException {
        return convertToJson(object).toString();
    }
}
