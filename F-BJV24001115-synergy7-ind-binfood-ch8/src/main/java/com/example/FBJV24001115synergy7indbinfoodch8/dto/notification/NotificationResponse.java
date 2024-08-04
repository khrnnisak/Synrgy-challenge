package com.example.FBJV24001115synergy7indbinfoodch8.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private int status;
    private String message;
}
