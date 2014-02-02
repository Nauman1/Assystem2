/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import jade.core.AID;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author rizwan
 */
public class EnviormentalStates implements Serializable {

    private static int MaxAuctionBid;
    private String MaxBider;
    private List BidderList;
    private String parentname;
    private AID ParentAID;
    private AID cloneID;
    static private  Map<String, List> AgentRegister = new ConcurrentHashMap<String, List>();
    static public final Object locker = new Object();

    /**
     * @return the MaxAuctionBid
     */
    public static int getMaxAuctionBid() {
        return MaxAuctionBid;
    }

    /**
     * @param MaxAuctionBid the MaxAuctionBid to set
     */
    public static void setMaxAuctionBid(int MaxAuctionBid) {
        EnviormentalStates.MaxAuctionBid = MaxAuctionBid;
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
    public static  Map<String, List> getAgentRegister() {
 synchronized(locker){
        return AgentRegister;
    }}
    
    public int getAgentregisterlenght(){
        synchronized(locker){
    return AgentRegister.size();
    }}

    /**
     * @param aAgentRegister the AgentRegister to set
     */
//    public void setAgentRegister(Map<String, List> aAgentRegister) {
//        EnviormentalStates.AgentRegister = aAgentRegister;
//
//    }

    public void setRegisterAgent(String s, List agentdetails) {
        synchronized(locker){
        this.AgentRegister.put(s, agentdetails);
    }}

    public List getRegisterAgent(Object Key) {
        List temp;
        synchronized(locker){
         temp = this.AgentRegister.get(Key);}
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

    public void unregisterAgent(String key) {
         synchronized(locker){
        AgentRegister.remove(key);
    }
    }
    public Set<Entry<String, List>> getentryAgentRegister(){
        synchronized(locker){
    return AgentRegister.entrySet();
    }}
}
