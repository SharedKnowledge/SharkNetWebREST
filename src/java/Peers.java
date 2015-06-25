import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
     * Tested with curl -X POST http://localhost:8080/WEB-INF/peers 
     * -d "name=aName&si[0]=si1&si[1]=si2&addresses=justOneAddress"
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
        JSONArray peerList  = new JSONArray();
        
        if (si.length == 0 || addresses.length == 0) {
            peerList.put(400);
        } else {
            JSONObject peer = generatePeer(name, si, addresses);
            peerList.put(peer);
        }
        
        try {
            jsonHelper.render(response, peerList);
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Collects you an array of chosen parameter name
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
     * @param adresses
     * @return 
     */
    protected JSONObject generatePeer(String name, String[] si, String[] adresses)
    {
        try {
            PeerSemanticTag peerST1 = sharkNet.createPeerSemanticTag(
                name,
                si,
                adresses
            );    
            return new JSONObject(peerST1);
            
        } catch (SharkKBException ex) {
            Logger.getLogger(Peers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
