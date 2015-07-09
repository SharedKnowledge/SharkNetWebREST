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

/**
 *
 * @author felixbrix
 * @author Paul Kujawa
 */
@WebServlet(urlPatterns = {"/peers"})
public class Peers extends Basic {
    
    public Peers() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
    /**
     * Works with GET param si as string or array
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
            peerSTtoJSON(response, peerST, 400);            
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Works with name as string, si and addresses as string or arrays
     * Tested with curl -X POST url -d "name=n&si[0]=s1&si[1]=s2&addresses=a"
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
            peerSTtoJSON(response, peerST, 400);
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String si = request.getParameter("si");

        try {
            sharkKB.removeSemanticTag(sharkKB.getSemanticTag(si));
            jsonHelper.render(response, si);
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
