/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.Random;

/**
 *
 * @author Sono
 */
public class BidderAgent extends Agent {

    int Arglenght = this.getArguments().length;

    public void setup() {
        if (Arglenght <= 0) {
            AgentState ags = new AgentState();
            ags.setMyAid(this.getAID());
            ags.setMyname(this.getLocalName());
            ags.setMymoney(randomnumber(10000, 50000));
            System.out.print(ags.getMymoney());
            ACLMessage acl = blockingReceive();
            
        }
    }

    public int randomnumber(int max, int min) {
        Random r = new Random();
        int i1 = r.nextInt(max - min) + min;
        return i1;
    }
    
    public void getregister(String name, AID myaid){
    AgentMain am = new AgentMain();
    
    
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
