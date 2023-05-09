package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Transaction {

    private int transactionId;
    private int senderId;
    private int receiverId;
    @Positive
    private double amountTransferred;
    private String status;

    public Transaction(int transactionId, int senderId, int receiverId, double amountTransferred, String status) {
        this.transactionId = transactionId;
        this.senderId= senderId;
        this.receiverId = receiverId;
        this.amountTransferred = amountTransferred;
        this.status = status;
    }

    public Transaction() {

    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;}

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiver) {
        this.receiverId = receiver;
    }

    public double getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(double amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
