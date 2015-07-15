import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;

/**
 *
 * @author Paul Kujawa
 */
abstract class Basic extends APIEndpoint{    
    public Basic() throws SharkKBException, SharkNetException, SharkSecurityException { }
    
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
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
