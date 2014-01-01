/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import static jadeacutonsystem.AgentMain.mainagentaid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rizwan
 */
public class EnviormentalStates implements Serializable{

    private int MaxAuctionBid;
    private String MaxBider;
    private List BidderList;
    private String parentname;
    private AID ParentAID;
    private AID cloneID;
    private static Map<String, List> AgentRegister = new HashMap<String, List>();

    /**
     * @return the MaxAuctionBid
     */
    public int getMaxAuctionBid() {
        return MaxAuctionBid;
    }

    /**
     * @param MaxAuctionBid the MaxAuctionBid to set
     */
    public void setMaxAuctionBid(int MaxAuctionBid) {
        this.MaxAuctionBid = MaxAuctionBid;
    }

    /**
     * @return the MaxBider
     */
    public String getMaxBider() {
        return MaxBider;
    }

    /**
     * @param MaxBider the MaxBider to set
     */
    public void setMaxBider(String MaxBider) {
        this.MaxBider = MaxBider;
    }

    /**
     * @return the BidderList
     */
    public List getBidderList() {
        return BidderList;
    }

    /**
     * @param BidderList the BidderList to set
     */
    public void setBidderList(List BidderList) {
        this.BidderList = BidderList;
    }

    /**
     * @return the parentname
     */
    public String getParentname() {
        return parentname;
    }

    /**
     * @param parentname the parentname to set
     */
    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    /**
     * @return the ParentAID
     */
    public AID getParentAID() {
        return ParentAID;
    }

    /**
     * @param ParentAID the ParentAID to set
     */
    public void setParentAID(AID ParentAID) {
        this.ParentAID = ParentAID;
    }

    /**
     * @return the AgentRegister
     */
    public Map<String, List> getAgentRegister() {
        return AgentRegister;
    }

    /**
     * @param aAgentRegister the AgentRegister to set
     */
    public void setAgentRegister(Map<String, List> aAgentRegister) {
        AgentRegister = aAgentRegister;
         
    }

    public void setRegisterAgent(String s, List agentdetails) {
        this.AgentRegister.put(s, agentdetails);
    }

    public List getRegisterAgent(Object Key) {
        List temp = this.AgentRegister.get(Key);
        return temp;
    }

    /**
     * @return the cloneID
     */
    public AID getCloneID() {
        return cloneID;
    }

    /**
     * @param cloneID the cloneID to set
     */
    public void setCloneID(AID cloneID) {
        this.cloneID = cloneID;
    }

}
