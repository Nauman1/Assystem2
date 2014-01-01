/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jadeacutonsystem;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Sono
 */
public class CloneBidderAgnet extends Agent {
    @Override
    public void setup(){
    Object Arguments = new Object();
   Arguments  = (Object)this.getArguments()[0];
   AgentState as = new AgentState();
   as = (AgentState) Arguments;
   // msg sending child AID.......... [1]
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("ChildAid");
        msg.addReceiver(as.getMyAid());
        send(msg);
        /////// wait for update from cloned 
        msg = blockingReceive();
    }
}
