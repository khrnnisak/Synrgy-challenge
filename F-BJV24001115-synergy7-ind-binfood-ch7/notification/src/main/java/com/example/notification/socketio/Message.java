package com.example.notification.socketio;

import lombok.Data;

@Data
public class Message {
    private String from;
    private String to;
    private String content;
}