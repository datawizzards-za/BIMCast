package com.itechhubsa.bimanage.Pojos;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
    private String message;
    private String user;
    private Long date;

    public Message(String message, String user) {
        this.message = message;
        this.user = user;
        setDate();
    }

    public Message(String message) {
        this.message = message;
        setDate();
    }

    public Message() {
        setDate();
    }

    public void setDate() {
        this.date = new Date().getTime();
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

    public Long getMessageDate() {
        return date;
    }
}
