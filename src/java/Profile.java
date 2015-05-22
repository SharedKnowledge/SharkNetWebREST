import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sharkfw.apps.sharknet.SharkNet;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.apps.sharknet.SharkNetPeerProfile;
import net.sharkfw.apps.sharknet.j2se_android.SharkNetEngine;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;
import org.json.JSONObject;

import toolbox.*;

@WebServlet(urlPatterns = {"/profile"})
public class Profile extends HttpServlet {
    
    private final SharkNet sharkNet;
    private final JSON jsonHelper;
    
    public Profile() throws SharkKBException, SharkNetException, SharkSecurityException {
        sharkNet = SharkNetEngine.createSharkNet("/Users/felixbrix/Documents/Studium/6. Semester/Entwicklung sozialer Anwendungen/SharkNetInterface/SharkNetAPI/db");
        jsonHelper = new JSON();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws net.sharkfw.knowledgeBase.SharkKBException
     * @throws net.sharkfw.apps.sharknet.SharkNetException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SharkKBException, SharkNetException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            // Creates example profile
            PeerSemanticTag peerST = sharkNet.createPeerSemanticTag(
                "felix",
                new String[] { "https://github.com/fbrix", "http://brx-online.de" },
                new String[] { "test@email.de", "tcp:test.de:7070" }
            );
                        
            SharkNetPeerProfile peerProfile = sharkNet.createPeerProfile(peerST);
            
            // Export profile as JSON string
            out.println(jsonHelper.convertToString(peerProfile));
        }
    }
    
    /**
     * TODO dummy test method
     * @return peer profile
     * @throws SharkKBException
     */
    private void postProfile(String name, String[] si, String[] addr) throws SharkKBException, SharkNetException, SharkSecurityException
    {
        PeerSemanticTag peerST = sharkNet.createPeerSemanticTag(name, si, addr);
        sharkNet.createPeerProfile(peerST);
    }

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
            processRequest(request, response);
        } catch (SharkKBException | SharkNetException ex) {
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
        try {
            processRequest(request, response);
        } catch (SharkKBException | SharkNetException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
