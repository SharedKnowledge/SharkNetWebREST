import helper.APIResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.apps.sharknet.SharkNetPeerProfile;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;
import org.json.JSONObject;

/**
 *
 * @author felixbrix
 * @author Paul Kujawa
 */
@WebServlet(urlPatterns = {"/profiles"})
public class Profile extends APIEndpoint {
    public Profile() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
    /**
     * Works with name as string, si and addresses as string or arrays
     * Tested with curl -X POST url -d "name=n&si[0]=s1&si[1]=s2&addresses=a"
     * To create or update an entry
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {    
        String[] si = generateParamArray("si", request);
    
        try {
            PeerSemanticTag peerST = sharkKB.getPeerSemanticTag(si);
            SharkNetPeerProfile profile = sharkNet.createPeerProfile(peerST);
            APIResponse.render(response, new JSONObject(profile.getPeer()));     
        } catch (SharkKBException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
