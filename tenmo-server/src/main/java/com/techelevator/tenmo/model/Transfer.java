package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transfer {
    private User sender;
    private User receiver;
    private BigDecimal amount;
    private Date date;
    private String status;

    public Transfer(User sender, User receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = null;
        this.status = "pending";
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
