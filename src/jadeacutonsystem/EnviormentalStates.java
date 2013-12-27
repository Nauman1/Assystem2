/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeacutonsystem;

import java.util.List;

/**
 *
 * @author rizwan
 */
public class EnviormentalStates {
    private int MaxAuctionBid;
     private String MaxBider;
      private List BidderList ;

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
}
