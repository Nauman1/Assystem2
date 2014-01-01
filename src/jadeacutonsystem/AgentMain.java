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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rizwan
 */
public class AgentMain extends Agent {

    EnviormentalStates evs = new EnviormentalStates();
    static AID mainagentaid = null;
    static int founderflag = -1;

    @Override
    public void setup() {
        AID childaid = null;
        Object[] ob = new Object[10];
        ob = this.getArguments();
        mainagentaid = this.getAID();
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
            jade.lang.acl.ACLMessage ChildInitmsg = blockingReceive();
            System.out.println("Receive msg");
            if (ChildInitmsg.getContent().equals("ChildAid")) {
                childaid = ChildInitmsg.getSender();
                evs.setCloneID(childaid);
                try {
                    updaterequest(evs, evs.getCloneID());
                } catch (IOException ex) {
                    Logger.getLogger(AgentMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(childaid.toString());
            }

        } else {
            System.out.println("I am else part");
        }
        //  while(evs.getAgentRegister().size()<=0){}
        jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
        founderflag = 0;

        System.out.println("Receive msg2222222" + evs.getAgentRegister().get("b"));
    }

    public void createcvsagents() {
    }
    /*
     * parm cloneaid is id of clone of registering agent while uodateinclone is id clone of agent in this case clone of main
     * agent
     *  
     */

    public void setregesterAgent(String Name1, AID AgentAid, AID cloneaid) throws IOException {
        List<AID> temp = new ArrayList<AID>();
        temp.add(0, AgentAid);
        temp.add(1, cloneaid);
        Map<String, List> agenttoregister = new HashMap<String, List>();
        agenttoregister.put(Name1, temp);
        evs.setAgentRegister(agenttoregister);
        //  updaterequest(evs, evs.getCloneID());
    }

    public boolean updaterequest(EnviormentalStates es, AID childaid) throws IOException {
        jade.lang.acl.ACLMessage tempacl = new ACLMessage(ACLMessage.INFORM_REF);
        tempacl.setContentObject(es);
        tempacl.addReceiver(childaid);
        send(tempacl);
        return true;
    }

    public boolean updaterequest(Object ob, AID childaid) {

        return true;
    }
}
