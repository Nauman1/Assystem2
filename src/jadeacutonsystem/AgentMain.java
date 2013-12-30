/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                System.out.println("Created clone");
            } catch (StaleProxyException ex) {
                System.out.println(ex);
            }
            //////////////////////////////////////////////// RECEIVE FOR CLONED MSG REPLY
            jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
            System.out.println("Receive msg");
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

    public void setregesterAgent(String Name1, AID AgentAid, AID cloneaid) throws IOException {
        List temp = null;
        temp.set(0, AgentAid);
        temp.set(1, cloneaid);
        Map<String, List> agenttoregister = new HashMap<String, List>();
        evs.setAgentRegister(agenttoregister);
        updaterequest(evs);
    }

    public boolean updaterequest(EnviormentalStates es) throws IOException {
        Object tempab = new Object();
        tempab = (Object) es;
        jade.lang.acl.ACLMessage tempacl = new ACLMessage(ACLMessage.INFORM_REF);
        tempacl.setContentObject(es);
        return true;
    }
}
