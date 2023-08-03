package org.example.model.GameInfo;

import java.util.ArrayList;

public class Trade {

    public static ArrayList<Trade> trades = new ArrayList<>();
    private Government sender;
    private Government receiver;
    private String type;
    private int id = 0;
    private Double amount;
    private Double price;
    private String message;
    private boolean flag = false;
    private String message2;

    public void setReceiver(Government receiver) {
        this.receiver = receiver;
    }

    public void setSender(Government sender) {
        this.sender = sender;
    }

    public Trade(Government user, String type, Double amount, Double price, String message) {
        if (price > -0.01 && price < 0.01) {
            this.sender = user;
        } else {
            this.receiver = user;
        }
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.message = message;
        trades.add(this);
        id = trades.size();
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public Government getReceiverOrSender() {
        if (sender != null)
            return sender;
        else
            return receiver;
    }

    public static Trade getTradeById(int id) {
        if (trades.size() == 0) return null;
        else {
            for (Trade trade : trades) {
                if (trade.getId() == id) return trade;
            }
            return null;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean accepted() {
        if (receiver != null && sender != null) return true;
        else return false;
    }
}
