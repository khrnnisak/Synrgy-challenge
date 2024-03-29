package org.example.Views;

import java.util.Scanner;
import org.example.Controllers.OrderController;
import org.example.Models.Order;
import org.example.Utils.*;

public class OrderView {
    static Scanner input = new Scanner(System.in);

    public static void displayReceipt(){
        OrderController o_cont = new OrderController();
        System.out.println(AdditionalUtil.headerFormat(o_cont.displayReceipt()));
        menuOrderSelection();
    }

    public static void menuOrderSelection(){
        OrderController o_cont = new OrderController();

        System.out.println("");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Ubah Pesanan");
        System.out.println("3. Hapus Pesanan");
        System.out.println("4. Kembali ke menu utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("=>");
        int choice = input.nextInt();
        o_cont.menuOrderSelection(choice);
    }

    public static void deleteOrder(){
        OrderController o_cont = new OrderController();
        MainView mv = new MainView();
        System.out.println(AdditionalUtil.headerFormat("Hapus Pesanan"));
        for(Order order : o_cont.getOrderList()){
            displayOrders(order);
        }
        input.nextLine();
        System.out.print("Pesanan apa yang ingin anda hapus? ");
        String deleteOrder = input.nextLine();
        System.out.print("Apakah anda yakin? (Y/N) ");
        String confirm = input.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            o_cont.deleteOrder(deleteOrder);
        }
        mv.displayMainMenu();
    }
    public static void updateOrder(){
        OrderController o_cont = new OrderController();
        MainView mv = new MainView();
        System.out.println(AdditionalUtil.headerFormat("Ubah Pesanan"));
        for(Order order : o_cont.getOrderList()){
            displayOrders(order);
        }
        input.nextLine();
        System.out.print("Pesanan apa yang ingin anda ubah? ");
        String updateOrder = input.nextLine();
        System.out.print("Masukkan banyak pesanan yang baru :  ");
        int update = input.nextInt();
        System.out.print("Simpan perubahan (Y/N) ");
        String confirm = input.next();

        if (confirm.equalsIgnoreCase("y")) {
            o_cont.updateOrder(updateOrder, update);
        }
        mv.displayMainMenu();
    }

    public static void displayOrders(Order order){
        System.out.println(order.getId()+" | "+order.getItem().getName()+" | "+order.getItem().getPrice());
    }

}
