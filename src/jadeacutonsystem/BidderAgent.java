/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sono
 */
public class BidderAgent extends Agent {


    @Override
    public void setup() {
        AID childaid;
String cloneagentname;
    int Arglenght = this.getArguments().length;
        cloneagentname = "Clone-"+this.getLocalName();
        if (Arglenght <= 0) {
            AgentState ags = new AgentState();
            ags.setMyAid(this.getAID());
            ags.setMyname(this.getLocalName());
            ags.setMymoney(randomnumber(10000, 5000));
            System.out.print(ags.getMymoney());
             Object[] StatesArgumetns = new Object[10];
            StatesArgumetns[0] = (Object) ags;        
             try {
                // Create clone agent
                this.getContainerController().createNewAgent(cloneagentname, "jadeacutonsystem.CloneBidderAgnet", StatesArgumetns).start();
            } catch (StaleProxyException ex) {
                System.out.println(ex);
            }
              //////////////////////////////////////////////// RECEIVE FOR CLONED MSG REPLY
             jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
            if (receiveingacl.getContent().equals("ChildAid")) {
                childaid = receiveingacl.getSender();
                ags.setCloneAid(childaid);
                    try {
                        // registring with main agent
                    getregister1(ags.getMyname(), ags.getMyAid(), ags.getCloneAid());
                    } catch (IOException ex) {
                        Logger.getLogger(BidderAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                System.out.println(childaid.toString());
            }
            
            else {}
        
            ACLMessage acl = blockingReceive();        
        }
    }

    public int randomnumber(int max, int min) {
        Random r = new Random();
        int i1 = r.nextInt(max - min) + min;
        return i1;
    }
    
    public void getregister1(String name, AID myaid, AID Cloneaid) throws IOException{
    AgentMain am = new AgentMain();
    am.setregesterAgent(name, myaid, Cloneaid);
    
    
    }
    
//            ACLMessage msg;// = new ACLMessage(ACLMessage.INFORM);
//        boolean flag = true;
//        while (flag) {
//            msg = blockingReceive();
//           // System.out.println("The msg is   " + msg.getContent());
//            if (msg.getContent().equals("BID")) {
//
//
//                kbid = analyzeandbid();
//                if (kbid != -1 & kbid != -255) {
//                    System.out.println(agentname + "::my bid is $" + kbid);
//                    ACLMessage reply = msg.createReply();
//                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("DONE");
//                    send(reply);
//                } else if (kbid == -255) {
//                    ACLMessage reply = msg.createReply();
//                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("Winner");
//                    send(reply);
//                } else {
//                    System.out.println(agentname + " I donot have enough money I quit");
//                    flag = false;
//                    ACLMessage reply = msg.createReply();
//                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("Leaving");
//                    send(reply);
//                    doDelete();
//                }
//            }
//    public AgentState initizeagentstates(AgentState Astate) {
//        Astate.set    
//        
//        return null;
//    }

}
