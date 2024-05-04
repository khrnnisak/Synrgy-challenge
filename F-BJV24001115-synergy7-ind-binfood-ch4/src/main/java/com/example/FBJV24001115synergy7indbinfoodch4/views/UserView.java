package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.UserController;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class UserView {
    @Autowired UserController userController;
    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayOrderMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman User"));
    }
    public void displayOrderMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah User");
        System.out.println("2. Perbarui Password User");
        System.out.println("3. Hapus User");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        userController.userMenuSelection(choice);
    }

    public void createView(){

    }

    public void updateView(){
        
    }

    public void deleteView(){
        
    }

    public void displayUsers(){
        
    }
}
