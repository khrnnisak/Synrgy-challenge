package com.example.FBJV24001115synergy7indbinfoodch4.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class AdditionalUtil {
    private AdditionalUtil(){};

    public static String headerFormat(String teks) {
        StringBuilder header = new StringBuilder();
        header.append("\n"+"=".repeat(20)+"\n").append(teks)
                .append("\n"+"=".repeat(20)+"\n");
        String result = header.toString();
        return result;
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }

    public static String pathFormat(){
        String PATH = "E:/Kumpulankoding/java/Synrgy-challenge/F-BJV24001115-synergy7-ind-binfood-ch4/";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstu1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 12;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return PATH + randomString + ".txt";
    }


    public static String priceFormat(int harga){

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }

}
