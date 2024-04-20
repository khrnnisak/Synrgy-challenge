package org.example;

import org.example.Controllers.MainController;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Data.initiateItems();
        Data.initiatePayments();

        MainController main_c = new MainController();
        main_c.displayMenu();
    }
}
