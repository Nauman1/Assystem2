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
        boolean flag = true;
        Arguments  = (Object)this.getArguments()[0];
        EnviormentalStates ev = new EnviormentalStates();
        ev =(EnviormentalStates) Arguments;
        ACLMessage msg = new ACLMessage();
        msg.setContent("ChildAid");
        msg.addReceiver(ev.getParentAID());
        send(msg);
        while (flag){
            
        msg = blockingReceive();
        if (msg.getPerformative()==ACLMessage.INFORM_REF){
        try {
            ev =  (EnviormentalStates) msg.getContentObject();
            System.out.println("updated list  main  "+ ev.getCloneID().toString());
        } catch (UnreadableException ex) {
            Logger.getLogger(CloneAgentMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else if (msg.getPerformative()==ACLMessage.DISCONFIRM){
        flag =false;
         System.out.println("exit- clone");
        }
        else if (msg.getPerformative()== ACLMessage.FAILURE){
        System.out.println("i have receved msg");
        }
        else{
           System.out.println("i have receved msg else");
        }
        
        
    }
    }
}
