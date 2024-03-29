package org.example.Controllers;

import org.example.Services.ItemService;
import org.example.Services.ItemServiceImpl;
import org.example.Services.OrderService;
import org.example.Services.OrderServiceImpl;
import org.example.Utils.FormatMessageUtil;
import org.example.Views.MainView;
import org.example.Views.OrderView;

public class MainController {
    public void displayMenu() {
        MainView mv = new MainView();
        mv.displayMainMenu();
    }

    public void mainSelection(int choice) {

        MainView mv = new MainView();
        OrderView ov = new OrderView();
        OrderService o_serv = new OrderServiceImpl();
        ItemService i_serv = new ItemServiceImpl();

        if (choice >= 1 && choice <= i_serv.getItemList().size()) {
            mv.displayAskQty((long) choice);
        } else if (choice == 99) {
            if (o_serv.getOrderList().isEmpty()) {
                FormatMessageUtil.errorMessageFormat("Anda belum memilih menu");
                    mv.displayMainMenu();
            } else {
                ov.displayReceipt();
            }
        } else {
            System.out.println("Pilihan tidak valid.");
            mv.displayMainMenu();
        }
    }

}