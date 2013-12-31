/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author rizwan
 */
public class CloneAgentMain extends Agent {

    @Override
    public void setup() {
        Object Arguments = new Object();
        Arguments  = (Object)this.getArguments()[0];
        EnviormentalStates ev = new EnviormentalStates();
        ev =(EnviormentalStates) Arguments;
        ACLMessage msg = new ACLMessage();
        msg.setContent("ChildAid");
        msg.addReceiver(ev.getParentAID());
        send(msg);
        msg = blockingReceive();
        if (msg.getPerformative()==ACLMessage.INFORM_REF){
        try {
            ev =  (EnviormentalStates) msg.getContentObject();
            System.out.println("updated list "+ ev.getCloneID().toString());
        } catch (UnreadableException ex) {
            Logger.getLogger(CloneAgentMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else if (msg.getPerformative()==ACLMessage.INFORM){
        
        }
        
        
    }
}
