/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.introspection.ACLMessage;
import jade.wrapper.StaleProxyException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.awt.geom.Crossings;

/**
 *
 * @author rizwan
 */
public class AgentMain extends Agent {

    EnviormentalStates evs = new EnviormentalStates();

    @Override
    public void setup() {
        AID childaid = null;
        Object[] ob = new Object[10];
        ob = this.getArguments();
        //  if it was not clone its first time crated
        if (ob.length < 1 || ob == null) {
            String cloneaggentname = "clone-" + this.getLocalName();
            evs.setParentAID(this.getAID());
            evs.setParentname(this.getName());
            Object[] StatesArgumetns = new Object[10];
            StatesArgumetns[0] = (Object) evs;
            try {
                // Create clone agent
                this.getContainerController().createNewAgent(cloneaggentname, "jadeacutonsystem.CloneAgentMain", StatesArgumetns).start();
            } catch (StaleProxyException ex) {
                System.out.println(ex);
            }
            //////////////////////////////////////////////// RECEIVE FOR CLONED MSG REPLY
            jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
            if (receiveingacl.getContent().equals("ChildAid")) {
                childaid = receiveingacl.getSender();
                System.out.println(childaid.toString());            
            }

        } else {
            System.out.println("I am else part");
        }
    }

    public void createcvsagents() {
    }
    public void setregesterAgent(String Name, AID AgentAid){
    //evs.set
        
    }
}
