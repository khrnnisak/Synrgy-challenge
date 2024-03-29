package org.example.Views;

import java.util.Scanner;
import org.example.Controllers.OrderController;
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

}
