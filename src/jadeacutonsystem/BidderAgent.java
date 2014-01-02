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
import java.nio.file.attribute.AclEntry;
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
      //  System.out.println(this.getLocalName());
        boolean flagloop = true;
        AID childaid;
        String cloneagentname;
        int Arglenght = -1;
        
        if(this.getArguments()!=null)
         Arglenght = this.getArguments().length;
        cloneagentname = "Clone-" + this.getLocalName();
        if (Arglenght <= 0) {
            AgentState ags = new AgentState();
            ags.setMyAid(this.getAID());
            ags.setMyname(this.getLocalName());
            ags.setMainagent(AgentMain.mainagentaid);
            ags.setMymoney(randomnumber(10000, 5000));
            System.out.println(this.getLocalName()+"  has money $  "+ags.getMymoney());
            Object[] StatesArgumetns = new Object[10];
            StatesArgumetns[0] = (Object) ags;
            try {
                // Create clone agent
                this.getContainerController().createNewAgent(cloneagentname, "jadeacutonsystem.CloneBidderAgnet", StatesArgumetns).start();
            } catch (StaleProxyException ex) {
                System.out.println(ex);
            }
            /////////////////////////////////////////////////////////////////// RECEIVE FOR CLONED MSG REPLY...[1C]
            jade.lang.acl.ACLMessage receiveingacl = blockingReceive();
            if (receiveingacl.getContent().equals("ChildAid")) {
                childaid = receiveingacl.getSender();
                ags.setCloneAid(childaid);
                try {
                    // registring with main agent
                 //   System.out.println("registering "+ this.getLocalName()+ "       "+EnviormentalStates.getAgentRegister());
                    getregister1(ags.getMyname(), ags.getMyAid(), ags.getCloneAid());
                    // System.out.println("localizing "+ this.getLocalName()+ "       "+EnviormentalStates.getAgentRegister());
                } catch (IOException ex) {
                    Logger.getLogger(BidderAgent.class.getName()).log(Level.SEVERE, null, ex);
                }

                // System.out.println(childaid.toString());
            } else {
            }
            /////////////////////////////////////////////////////////////////// CLONE AND ORGINAL ADJUENCY COMPLETED*********
            if (AgentMain.founderflag == -1) {
                //if its first bidder .......................... [2m]
                AgentMain.founderflag=0;
                ACLMessage ac = new ACLMessage(ACLMessage.INFORM);
                ac.setContent("thisahfkjdsf");
                ac.addReceiver(ags.getMainagent());
                send(ac);
            }
            while (flagloop) {
                // Again listen from main agent to bid................. [3m]
                System.out.println(this.getLocalName()+ "waiting for main agent to send invitation");
                ACLMessage acl = blockingReceive();
                if (acl.getPerformative() == ACLMessage.INFORM) {
                    if (acl.getContent().equals("BID")) {
                        int mybid = calculatebid(ags.getMymoney(), EnviormentalStates.getMaxAuctionBid());
                        if (mybid != -1 & mybid != -255 & !(mybid <= 0) & mybid != -101) {
                            System.out.println(this.getLocalName()
                                    + "::my bid is $" + mybid);
                            

                            ACLMessage reply = acl.createReply();
                            // bid reply
                            reply.setPerformative(ACLMessage.INFORM_IF);
                            reply.setContent(new Integer(mybid).toString());// will uuse informs for knowing type of msga and then use it to exchange data
                            send(reply);
                            ACLMessage acltemp = new ACLMessage(ACLMessage.INFORM);
                            try {
                                acltemp.setContentObject(ags);
                                acltemp.addReceiver(ags.getCloneAid());
                                send(acltemp);
                            } catch (IOException ex) {
                                Logger.getLogger(BidderAgent.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        } else if (mybid == -255) {
                            ACLMessage reply = acl.createReply();
                            // conformed IM winner
                            reply.setPerformative(ACLMessage.CONFIRM);
                            reply.setContent(this.getLocalName());
                            send(reply);
                        } else if (mybid == -101) {
                            //System.out.println(this.getLocalName() + " I donot have enough money I quit");
                            //    flag = false;
                            ACLMessage reply = acl.createReply();
                            reply.setPerformative(ACLMessage.DISCONFIRM);
                            reply.setContent(this.getLocalName());
                            send(reply);
                            ACLMessage clonedc = new ACLMessage(ACLMessage.DISCONFIRM);
                            clonedc.setContent("KILL");
                            clonedc.addReceiver(ags.getCloneAid());
                            send(clonedc);
                           // unregister1(this.getLocalName());
                          //this.doDelete();
                            

                        }
                    }
                }
            }
            //this.doDelete();
        }
    }

    public int randomnumber(int max, int min) {
        Random r = new Random();
        int i1 = r.nextInt(max - min) + min;
        return i1;
    }

    public void getregister1(String name, AID myaid, AID Cloneaid) throws IOException {
        AgentMain am = new AgentMain();
        am.setregesterAgent(name, myaid, Cloneaid);

    }
    public void unregister1(String key){
     AgentMain am = new AgentMain();
        am.unregisteragent(key);
    }

    public int calculatebid(int moneyinhand, int higestbid) {
        int mybid = -1;
        if (higestbid < moneyinhand & higestbid != 0) {
            mybid = (int) 2 * higestbid;
            mybid = mybid + higestbid;
            if (mybid > moneyinhand) {
                mybid = (int) 0.5 * higestbid;
                mybid = mybid + moneyinhand;
                if (mybid > moneyinhand) {
                    mybid = -1;
                }
            }
        } else if (higestbid == 0) {
            mybid = 10;
        } else if (moneyinhand <= higestbid) {
            mybid = -101;
        } else if (mybid >= higestbid) {
            mybid = -255;
        }
        return mybid;
    }

}


//            ACLMessage msg;// = new ACLMessage(ACLMessage.INFORM);
//        boolean flag = true;
//        while (flag) {
//            msg = blockingReceive();
//           // System.out.println("The msg is   " + msg.getContent());
//            if (msg.getContent().equals("BID")) {
//
//
//                mybid = analyzeandbid();
//                if (mybid != -1 & mybid != -255) {
//                    System.out.println(agentname + "::my bid is $" + mybid);
//                    ACLMessage reply = msg.createReply();
//                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("DONE");
//                    send(reply);
//                } else if (mybid == -255) {
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

