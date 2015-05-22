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

@WebServlet(urlPatterns = {"/profile/*"})
public class Profile extends APIEndpoint {

    public Profile() throws SharkKBException, SharkNetException, SharkSecurityException { }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            
            // Creates example profile
            PeerSemanticTag peerST = sharkNet.createPeerSemanticTag(
                "felix",
                new String[] { "https://github.com/fbrix", "http://brx-online.de" },
                new String[] { "test@email.de", "tcp:test.de:7070" }
            );

            SharkNetPeerProfile peerProfile = sharkNet.createPeerProfile(peerST);

            // Render profile as JSON string
            jsonHelper.render(response, peerProfile);
            
        } catch (SharkKBException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
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
    }
}
