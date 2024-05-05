package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MerchantView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.ProductView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.UserView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class HomeController {
    @Autowired UserView userview;
    @Autowired ProductView productView;
    @Autowired MerchantView merchantView;
    @Autowired OrderView orderView;
    @Autowired MainView mainView;

    Scanner input = new Scanner(System.in);

    public void pageMenu(int choice){
        switch (choice) {
            case 1:
                userview.displayMainMenu();
                break;
            case 2:
                productView.displayMainMenu();
                break;
            case 3:
                merchantView.displayMainMenu();
                break;
            case 4:
                orderView.displayMainMenu();
                break;
            case 0:
                System.out.print("Apakah anda yakin untuk keluar? (Y/N)");
                String confirm = input.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    System.exit(0);
                }
                mainView.displayView();
                break;
            default:
                log.error("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }

    }

    public void home(){
        mainView.displayView();
    }

}
