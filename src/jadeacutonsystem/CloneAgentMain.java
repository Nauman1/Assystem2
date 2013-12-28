/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;



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
        
        
        
    }
}
