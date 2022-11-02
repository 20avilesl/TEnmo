package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transfer {
    private int sender;
    private int receiver;
    private BigDecimal amount;
    private Date date;
    private String status;
    private int id;

    public Transfer(){};
    public Transfer(int sender, int receiver, BigDecimal amount, String status, int id) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = null;
        this.status = status;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
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
