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
import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;
import org.json.JSONObject;

/**
 *
 * @author felixbrix
 * @author Paul Kujawa
 */
@WebServlet(urlPatterns = {"/peers"})
public class Peer extends APIEndpoint {
    public Peer() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
    /**
     * Tested with curl url?si=foo || curl url?si[0]=foo&si[1]=bar
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] si = generateParamArray("si", request);
        
        try {    
            PeerSemanticTag peerST = sharkKB.getPeerSemanticTag(si);
            
            if (peerST == null) {
                APIResponse.render(response, 404);
            } else {
                APIResponse.render(response, new JSONObject(peerST));
            }
        } catch (SharkKBException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
        String name         = request.getParameter("name");
        String[] si         = generateParamArray("si", request);
        String[] addresses  = generateParamArray("addresses", request);
    
        try {
            PeerSemanticTag peerST = sharkKB.getPeerSemanticTag(si);
            if (peerST != null) {
                peerST.setName(name);
                peerST.setAddresses(addresses);   
            } else {    
                peerST = createPeerST(name, si, addresses);
                if (peerST == null) {
                    APIResponse.render(response, 400);
                }
            }
            APIResponse.render(response, new JSONObject(peerST));
        } catch (SharkKBException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    /**
     * Tested with curl -X DELETE url?si=foo
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String si = request.getParameter("si");
        
        try {
            SemanticTag peerST = sharkKB.getSemanticTag(si);
             if (peerST == null) {
                APIResponse.render(response, 404);
            } else {
                sharkKB.removeSemanticTag(peerST);
                APIResponse.render(response, null);
            }
        } catch (SharkKBException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
