/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jadeacutonsystem;

import jade.core.AID;

/**
 *
 * @author Sono
 */
public class AgentState {
    private AID myAid ;
    private String Myname;
    private int mymoney;
    private int mylastbid;

    /**
     * @return the myAid
     */
    public AID getMyAid() {
        return myAid;
    }

    /**
     * @param myAid the myAid to set
     */
    public void setMyAid(AID myAid) {
        this.myAid = myAid;
    }

    /**
     * @return the Myname
     */
    public String getMyname() {
        return Myname;
    }

    /**
     * @param Myname the Myname to set
     */
    public void setMyname(String Myname) {
        this.Myname = Myname;
    }

    /**
     * @return the mymoney
     */
    public int getMymoney() {
        return mymoney;
    }

    /**
     * @param mymoney the mymoney to set
     */
    public void setMymoney(int mymoney) {
        this.mymoney = mymoney;
    }

    /**
     * @return the mylastbid
     */
    public int getMylastbid() {
        return mylastbid;
    }

    /**
     * @param mylastbid the mylastbid to set
     */
    public void setMylastbid(int mylastbid) {
        this.mylastbid = mylastbid;
    }
    
    
}