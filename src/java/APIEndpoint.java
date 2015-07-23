import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import net.sharkfw.apps.sharknet.SharkNet;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.apps.sharknet.j2se_android.SharkNetEngine;
import net.sharkfw.knowledgeBase.SharkKBException;;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.system.SharkSecurityException;


/**
 *
 * @author felixbrix
 * @author Paul Kujawa
 */
class APIEndpoint extends HttpServlet {
    final SharkNet sharkNet;
    final InMemoSharkKB sharkKB;
    
    public APIEndpoint() throws SharkKBException, SharkNetException, SharkSecurityException {
        sharkNet = SharkNetEngine.createSharkNet("db");
        sharkKB = new InMemoSharkKB();
    }
    
        
    /**
     * Collects you an array of chosen parameter via name
     * @param paramName
     * @param request
     * @return 
     */
    protected String[] generateParamArray(String paramName, HttpServletRequest request)
    {
        List<String> params = new ArrayList<>();
        String singleSI = request.getParameter(paramName);
        
        if (singleSI != null && !singleSI.isEmpty()) {
            params.add(singleSI);
        } else {
            String nextParam = request.getParameter(paramName+"[0]");
            int i = 0;

            while (nextParam != null && !nextParam.isEmpty()) {
                params.add(nextParam);
                i++;
                nextParam = request.getParameter(paramName+"["+i+"]");
            }
        }   
        String[] si = new String[params.size()];
        si = params.toArray(si);
        
        return si;
    }
    
    /**
     * 
     * @param name
     * @param si
     * @param addresses
     * @return 
     */
    protected PeerSemanticTag createPeerST(String name, String[] si, String[] addresses)
    {
        if (name.isEmpty() || si.length == 0 || addresses.length == 0) {
            return null;
        }
        
        try {
            return sharkKB.createPeerSemanticTag(name, si, addresses);
        } catch (SharkKBException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
