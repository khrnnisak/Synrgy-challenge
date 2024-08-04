package com.example.notification.socketio;

import lombok.Data;

@Data
public class BinarPromoMessage {
    private String from;
    private String product;
    private String price;
}
