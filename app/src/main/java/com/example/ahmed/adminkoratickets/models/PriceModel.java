package com.example.ahmed.adminkoratickets.models;
public class PriceModel{

    String ticket_class , ticket_price , match_id;

    public PriceModel(String ticket_class, String ticket_price, String match_id) {
        this.ticket_class = ticket_class;
        this.ticket_price = ticket_price;
        this.match_id = match_id;
    }

    public String getTicket_class() {
        return ticket_class;
    }

    public void setTicket_class(String ticket_class) {
        this.ticket_class = ticket_class;
    }

    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}