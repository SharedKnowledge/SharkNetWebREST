import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;

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
        
        try {
            // Example profiles
            PeerSemanticTag peerST1 = sharkNet.createPeerSemanticTag(
                    "paul",
                    new String[] { "paul.de" },
                    new String[] { "test@paul.de" }
            );
            
            PeerSemanticTag peerST2 = sharkNet.createPeerSemanticTag(
                    "jan",
                    new String[] { "jan.de" },
                    new String[] { "jan@email.de" }
            );
            
            SharkNetPeerProfile peerProfile1 = sharkNet.createPeerProfile(peerST1);
            SharkNetPeerProfile peerProfile2 = sharkNet.createPeerProfile(peerST2);

            JSONArray profileList = new JSONArray();
            profileList.put(new JSONObject(peerProfile1));
            profileList.put(new JSONObject(peerProfile2));

            // Render profiles as JSON string
            jsonHelper.render(response, profileList);
            
        } catch (SharkKBException ex) {
            Logger.getLogger(Profiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
