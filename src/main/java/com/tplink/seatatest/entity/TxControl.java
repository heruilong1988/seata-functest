package com.tplink.seatatest.entity;

import com.google.common.base.MoreObjects;

import java.util.Date;

public class TxControl {

    private String globalTransactionId;
    private Long branchId;
    private String branchTxStatus;
    private Date gmtCreateTime;
    private Date gmtUpdateTime;

    public String getGlobalTransactionId() {
        return globalTransactionId;
    }

    public void setGlobalTransactionId(String globalTransactionId) {
        this.globalTransactionId = globalTransactionId;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getBranchTxStatus() {
        return branchTxStatus;
    }

    public void setBranchTxStatus(String branchTxStatus) {
        this.branchTxStatus = branchTxStatus;
    }

    public Date getGmtCreateTime() {
        return gmtCreateTime;
    }

    public void setGmtCreateTime(Date gmtCreateTime) {
        this.gmtCreateTime = gmtCreateTime;
    }

    public Date getGmtUpdateTime() {
        return gmtUpdateTime;
    }

    public void setGmtUpdateTime(Date gmtUpdateTime) {
        this.gmtUpdateTime = gmtUpdateTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("globalTransactionId", globalTransactionId)
                .add("branchId", branchId)
                .add("branchTxStatus", branchTxStatus)
                .add("gmtCreateTime", gmtCreateTime)
                .add("gmtUpdateTime", gmtUpdateTime)
                .toString();
    }

    public static class Builder {
        private String globalTransactionId;
        private Long branchId;
        private String branchTxStatus;
        private Date gmtCreateTime;
        private Date gmtUpdateTime;

        public Builder globalTransactionId(String globalTransactionId) {
            this.globalTransactionId = globalTransactionId;
            return this;
        }

        public Builder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        public Builder branchTxStatus(String branchTxStatus) {
            this.branchTxStatus = branchTxStatus;
            return this;
        }

        public TxControl build() {
            TxControl  tx = new TxControl();
            tx.setBranchId(this.branchId);
            tx.setGlobalTransactionId(this.globalTransactionId);
            tx.setBranchTxStatus(this.branchTxStatus);
            return tx;
        }

    }
}
