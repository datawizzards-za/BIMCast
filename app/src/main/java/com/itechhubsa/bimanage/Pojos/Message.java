package com.itechhubsa.bimanage.Pojos;

import java.util.Date;

public class Message {
    private String message;
    private String user;
    private Long date;

    public Message(String message, String user) {
        this.message = message;
        this.user = user;
        date = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
