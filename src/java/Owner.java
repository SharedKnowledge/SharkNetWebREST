import helper.APIResponse;
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
import org.json.JSONObject;

/**
 *
 * @author Paul Kujawas
 */
@WebServlet(urlPatterns = {"/owner"})
public class Owner extends APIEndpoint {    
    public Owner() throws SharkKBException, SharkNetException, SharkSecurityException {}
    
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
        PeerSemanticTag kbOwner = sharkKB.getOwner(); 
        try {
            if (kbOwner == null) {
                APIResponse.render(response, 424);
            } else {
                APIResponse.render(response, new JSONObject(kbOwner));        
            }
        } catch (SharkKBException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * Tested with curl -X POST http://localhost:8080/SharkNetWebREST/user 
     * -d "name=aName&si[0]=si1&si[1]=si2&addresses=justOneAddress"
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name             = request.getParameter("name");
        String[] si             = generateParamArray("si", request);
        String[] addresses      = generateParamArray("addresses", request);
        PeerSemanticTag peerST  = createPeerST(name, si, addresses);
        
        try {
            if (peerST == null) {
                APIResponse.render(response, 400);
            } else {
                sharkKB.setOwner(peerST);
                APIResponse.render(response, null);
            }
        } catch (SharkKBException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
