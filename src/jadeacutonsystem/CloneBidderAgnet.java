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
        AgentState as ;
        as = (AgentState) Arguments;
        // msg sending child AID.......... [1]
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("ChildAid");
        msg.addReceiver(as.getMyAid());
        send(msg);
        /////// wait for update from cloned 
        boolean flag = true;
        while (flag) {
            try{
            msg = blockingReceive();}
        catch (jade.core.Agent.Interrupted ex) {

            ACLMessage ac = new ACLMessage(ACLMessage.FAILURE);
                ac.setPerformative(ACLMessage.FAILURE);
                ac.setContent(this.getLocalName());
                ac.addReceiver(as.getMyAid());
                send(ac);
                msg = null;
                takeDown();
                this.doDelete();
                flag = false;
            } 
            if (msg != null && msg.getPerformative() == ACLMessage.INFORM_REF ) {
                try {
                    as = (AgentState) msg.getContentObject();
                    System.out.println("updated list Bidder Option");
                } catch (UnreadableException ex) {
                    Logger.getLogger(CloneAgentMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (msg != null && msg.getPerformative() == ACLMessage.DISCONFIRM) {
                // flag = false;     // will be exited in case bidding is ended sofar it is not using to test recreating agents

                System.out.println("exit- clone");
            } else if (msg != null && msg.getPerformative() == ACLMessage.FAILURE) {
                System.out.println(as.getMyname()   +"hello my clone I am gonna die");
                Object[] StatesArgumetns = new Object[10];
                
                  StatesArgumetns[0] = (Object) as;
                try {
                    this.getContainerController().createNewAgent(msg.getContent(), "jadeacutonsystem.BidderAgent", StatesArgumetns).start();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else {
//                            takeDown();
//                            this.doDelete();
            }

        }

    }
}
