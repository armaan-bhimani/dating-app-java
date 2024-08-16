package com.example.date_app.model;

public class MessageModel {
    private String senderId;
    private String message;
    private String currentTime;
    private String currentDate;

    public MessageModel() {

    }

    public MessageModel(String senderId, String message, String currentTime, String currentDate) {
        this.senderId = senderId;
        this.message = message;
        this.currentTime = currentTime;
        this.currentDate = currentDate;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}

