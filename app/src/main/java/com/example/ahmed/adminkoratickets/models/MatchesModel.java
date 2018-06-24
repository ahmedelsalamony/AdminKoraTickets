package com.example.ahmed.adminkoratickets.models;

/**
 * Created by ahmed on 4/10/2018.
 */

public class MatchesModel {

    String id;
    String firstTeam,secondTeam,date,time,location;
    String ticket_class;

    public MatchesModel(String id, String firstTeam, String secondTeam, String date, String time, String location) {

        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.date = date;
        this.time = time;
        this.location = location;
        this.id = id;
    }

    public MatchesModel(String firstTeam, String secondTeam, String date, String time, String ticket_class){

        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.date = date;
        this.time = time;
        this.ticket_class = ticket_class;
    }

    public String getTicket_class() {
        return ticket_class;
    }

    public void setTicket_class(String ticket_class) {
        this.ticket_class = ticket_class;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
