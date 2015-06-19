
import javax.servlet.http.HttpServlet;
import net.sharkfw.apps.sharknet.SharkNet;
import net.sharkfw.apps.sharknet.SharkNetException;
import net.sharkfw.apps.sharknet.j2se_android.SharkNetEngine;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;
import helper.JSONHelper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author felixbrix
 */
class APIEndpoint extends HttpServlet {
    final SharkNet sharkNet;
    final JSONHelper jsonHelper;
    
    public APIEndpoint() throws SharkKBException, SharkNetException, SharkSecurityException {
        sharkNet = SharkNetEngine.createSharkNet("db");
        jsonHelper = new JSONHelper();
    }
}
