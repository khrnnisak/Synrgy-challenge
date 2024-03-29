package org.example.Views;

import java.util.Scanner;

import org.example.Controllers.ItemController;
import org.example.Controllers.MainController;
import org.example.Controllers.OrderController;
import org.example.Models.Item;
import org.example.Utils.*;



public class MainView {
    static Scanner input = new Scanner(System.in);
    public static void displayMainMenu(){
        ItemController i_cont = new ItemController();
        displayHeader();
        i_cont.displayItem();
        menuSelection();
    }

    public static void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di BinarFud"));
        System.out.println("Silakan pilih Makanan : ");
    }

    public static void menuSelection() {
        MainController m_cont = new MainController();
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print("=> ");
        int choice = input.nextInt();
        m_cont.mainSelection(choice);
    }

    public static void displayAskQty(Long id){
        ItemController i_cont = new ItemController();
        OrderController o_cont = new OrderController();
        ItemView i_view = new ItemView();

        System.out.println(AdditionalUtil.headerFormat("Berapa Pesanan Anda?"));
        Item item = i_cont.displayItemById(id);
        i_view.displayItem(item);
        System.out.println("input 0 untuk kembali");
        System.out.print("qty => ");
        int quantity = input.nextInt();

        if (quantity != 0) {
            o_cont.addOrder(item,quantity);
        }
        displayMainMenu();
    }
}
