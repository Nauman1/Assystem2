/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import au.com.bytecode.opencsv.CSVReader;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
         String[] agentlist = null;
        List<String[]> agentnames = new ArrayList<>();
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
           //     System.out.println("Created clone");
            } catch (StaleProxyException ex) {
                System.out.println(ex);
            }
            //////////////////////////////////////////////// RECEIVE FOR CLONED MSG REPLY
            jade.lang.acl.ACLMessage ChildInitmsg = blockingReceive();
           // System.out.println("Receive msg");
            if (ChildInitmsg.getContent().equals("ChildAid")) {
                childaid = ChildInitmsg.getSender();
                evs.setCloneID(childaid);
                try {
                    updaterequest(evs, evs.getCloneID());
                } catch (IOException ex) {
                    Logger.getLogger(AgentMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println(childaid.toString());
            }

        } else {
            System.out.println("I am else part");
        }
        try {
            /////////////////////////////////////////////////////////////////////////////////// CLONE ORGINAL AGENT FULL ADJUCENT
            agentnames = getcsvcontents();
           agentlist = new String[agentnames.size()];
            int i = 0;
            while (i<agentnames.size()) {
               agentlist[i] = agentnames.get(i)[0];
               i++;
            }

        } catch (IOException ex) {
            Logger.getLogger(AgentMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        while (i < agentlist.length && !(agentlist[i]==null)) {

            try {
                this.getContainerController().createNewAgent(agentlist[i], "jadeacutonsystem.BidderAgent", null).start();

            } catch (Exception e) {
                System.out.println(e);
            }
            i++;
        }
            //join();
        jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
        founderflag = 0;
        bidmanager(evs);

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
        evs.setRegisterAgent(Name1, temp);
//        Map<String, List> agenttoregister = new HashMap<String, List>();
//        agenttoregister.put(Name1, temp);
//        evs.setAgentRegister(agenttoregister);
        //  updaterequest(evs, evs.getCloneID());
    }
   

    public void unregisteragent(String key) {
        evs.unregisterAgent(key);
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

    public void bidmanager(EnviormentalStates ev) {
        String nbiderkey;
        List nbider = new ArrayList();
        AID nbidderAid = new AID();
        boolean bidflag = true;
        do {
            
            if (!(EnviormentalStates.getAgentRegister().isEmpty())) {
              
                for (Map.Entry<String, List> entry : evs.getAgentRegister().entrySet()) {
                    nbiderkey = entry.getKey();
                    System.out.println(nbiderkey+"  your next turn");
                    nbider = EnviormentalStates.getAgentRegister().get(nbiderkey);
                    nbidderAid = (AID) nbider.get(0); ///// 0 index of list contains AID of agent
                    if (nbidderAid != null) {
                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        msg.setContent("BID");
                        msg.addReceiver(nbidderAid);
                        
                        send(msg);
                        
                        //wait for repluy from bidder agent
                        ACLMessage recivingmsg = blockingReceive();
                        if (recivingmsg.getPerformative() == ACLMessage.INFORM_IF) {
                             //System.out.println(this.getLocalName()+ "inform-if main");
                            if ((EnviormentalStates.getAgentRegister().size()) > 1) {
                                int temp = new Integer(recivingmsg.getContent()).intValue();
                                EnviormentalStates.setMaxAuctionBid(temp);
                            } else {
                                System.out.println("And the winner is  " + recivingmsg.getSender().getLocalName());
                                bidflag = false;
                            }
                        } // winner therezzzzzzzz
                        else if (recivingmsg.getPerformative() == ACLMessage.CONFIRM) {
                           // System.out.println(this.getLocalName()+ "conform main");

                            System.out.println("And the winner is" + recivingmsg.getContent());

                        } else if (recivingmsg.getPerformative() == ACLMessage.DISCONFIRM) {
                            
                            if ((EnviormentalStates.getAgentRegister().size()) > 1) {
                         //   System.out.println(this.getLocalName()+ "inform-dis main");
                                unregisteragent(recivingmsg.getSender().getLocalName());
                                System.out.println(recivingmsg.getContent() + "OUT OF BIDDING");
                            } else if ((EnviormentalStates.getAgentRegister().size()) == 1) {
                            ///    System.out.println(this.getLocalName()+ "outelse main");
                                System.out.println(recivingmsg.getContent() + "Winner is " + recivingmsg.getSender().getLocalName());
                                bidflag = false;
                            }

                        }

                    }
                }

            }
        } while (bidflag);
    }

    public List getcsvcontents() throws FileNotFoundException, IOException {
        //int i = 0;
        CSVReader reader = new CSVReader(new FileReader("im.csv"));
        List<String[]> nextLine = new ArrayList<String[]>();
        nextLine = reader.readAll();
        return nextLine;
    }

}
