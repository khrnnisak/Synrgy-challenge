package org.example.Controllers;

import org.example.Services.OrderService;
import org.example.Services.OrderServiceImpl;
import org.example.Views.MainView;
import org.example.Views.OrderView;

public class MainController {
    public void displayMenu(){
        MainView mv = new MainView();
        mv.displayMainMenu();
    }
    public void mainSelection(int choice){

        MainView mv = new MainView();
        OrderView ov = new OrderView();
        OrderService o_serv = new OrderServiceImpl();

        if (choice >= 1 && choice <= 5) {
            mv.displayAskQty((long) choice);
        } else if (choice == 99) {
            if (o_serv.getOrderList().isEmpty()) {
                // System.out.println(RED+"Anda belum memesan makanan"+RESET);
            }else{
                ov.displayReceipt();
            }
        }
    }

}