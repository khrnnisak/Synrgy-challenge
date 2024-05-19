package org.example.utils;

public class FormatMessageUtil {
    private FormatMessageUtil(){};

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static String duplicateMessage(){
        return errorMessageFormat("Data Sudah Tersedia");
    }

    public static String notFoundMessage(){
        return errorMessageFormat("Data Tidak Ditemukan");
    }

    public static String failedToAddMessage(){
        return errorMessageFormat("Gagal Menambahkan Data");
    }

    public static String failedToDeleteMessage(){
        return errorMessageFormat("Gagal Menghapus Data");
    }

    public static String failedToEditMessage(){
        return errorMessageFormat("Gagal Mengubah Data");
    }

    public static String succesToAddMessage(){
        return successMessageFormat("Berhasil Menambahkan Data");
    }

    public static String succesToDeleteMessage(){
        return successMessageFormat("Berhasil Menghapus Data");
    }

    public static String succesToEditMessage(){
        return successMessageFormat("Berhasil Mengubah Data");
    }

    public static String errorMessageFormat(String message){
        return RED+message+RESET;
    }

    public static String successMessageFormat(String message){
        return GREEN+message+RESET;
    }

}
