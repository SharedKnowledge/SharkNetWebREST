import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;

/**
 *
 * @author felixbrix
 */
@WebServlet(urlPatterns = {"/profiles"})
public class Profiles extends APIEndpoint {
    
    public Profiles() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Render profiles as JSON string
        // jsonHelper.render(response, sharkNet.getPeerProfiles());
    }
}
