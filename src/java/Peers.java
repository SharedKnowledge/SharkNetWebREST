import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author felixbrix
 */
@WebServlet(urlPatterns = {"/peers"})
public class Peers extends APIEndpoint {
    
    public Peers() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
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

            JSONArray peerList = new JSONArray();
            peerList.put(new JSONObject(peerST1));
            peerList.put(new JSONObject(peerST2));

            // Render profiles as JSON string
            jsonHelper.render(response, peerList);
            
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Parameter entgegen nehmen: SI / [SI]
        // 2. PeerSemanticTag erstellen in der aktullen KB
        // 3. Success / Error response
    }
}
