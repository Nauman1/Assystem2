/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Sono
 */
public class CloneBidderAgnet extends Agent {

    @Override
    public void setup() {
        Object Arguments = new Object();
        Arguments = (Object) this.getArguments()[0];
        AgentState as = new AgentState();
        as = (AgentState) Arguments;
        // msg sending child AID.......... [1]
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("ChildAid");
        msg.addReceiver(as.getMyAid());
        send(msg);
        /////// wait for update from cloned 
        boolean flag = true;
        while (flag) {

            msg = blockingReceive();
            if (msg.getPerformative() == ACLMessage.INFORM_REF) {
                try {
                    as = (AgentState) msg.getContentObject();
                    System.out.println("updated list Bidder Option");
                } catch (UnreadableException ex) {
                    Logger.getLogger(CloneAgentMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (msg.getPerformative() == ACLMessage.DISCONFIRM) {
                flag = false;
                System.out.println("exit- clone");
            }

        }

    }
}
