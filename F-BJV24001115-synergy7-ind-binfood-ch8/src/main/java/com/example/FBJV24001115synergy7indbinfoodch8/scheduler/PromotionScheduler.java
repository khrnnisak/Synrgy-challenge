package com.example.FBJV24001115synergy7indbinfoodch8.scheduler;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.notification.NotificationRequest;
import com.example.FBJV24001115synergy7indbinfoodch8.services.FCMService;

@Component
public class PromotionScheduler {
    @Autowired FCMService fcmService;

    @Scheduled(cron = "0 0 12 * * *")
    public void cronJob() throws ExecutionException, InterruptedException {
        NotificationRequest request = new NotificationRequest();
        request.setTitle("Promo Siang Binarfud");
        request.setBody("Silakan order pada Pukul 12.00 - 13.00 untuk mendapatakan diskon 20%");
        request.setToken("cMUIk9-hccUxbA-Uznl7LC:APA91bHdARuGyr517ZpGZjt8QsKVKP-zzl3-wKpOsTsvA8pTBvrXdaFJmeBhrQBpCixs5O3bo6nZ-o8U74khj1kf8ZjxyHbugfngNuhmsd6Nhs3xVja23IAlmliOwQYIR9TmS2fXIkV3");
        fcmService.sendMessageToToken(request);
    }
}
