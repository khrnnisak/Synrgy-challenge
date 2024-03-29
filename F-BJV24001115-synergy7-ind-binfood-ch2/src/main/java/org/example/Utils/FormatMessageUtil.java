package org.example.Utils;

public class FormatMessageUtil {
    private FormatMessageUtil(){};

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static void duplicateMessage(){
        errorMessageFormat("Data Sudah Tersedia");
    }

    public static void notFoundMessage(){
        errorMessageFormat("Data Tidak Ditemukan");
    }

    public static void failedToAddMessage(){
        errorMessageFormat("Gagal Menambahkan Data");
    }

    public static void failedToDeleteMessage(){
        errorMessageFormat("Gagal Menghapus Data");
    }

    public static void failedToEditMessage(){
        errorMessageFormat("Gagal Mengubah Data");
    }

    public static void succesToAddMessage(){
        successMessageFormat("Berhasil Menambahkan Data");
    }

    public static void succesToDeleteMessage(){
        successMessageFormat("Berhasil Menghapus Data");
    }

    public static void succesToEditMessage(){
        successMessageFormat("Berhasil Mengubah Data");
    }

    public static void errorMessageFormat(String message){
        System.out.println(RED+message+RESET);
    }

    public static void successMessageFormat(String message){
        System.out.println(GREEN+message+RESET);
    }

}
