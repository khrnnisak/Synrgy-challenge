package org.example.Views;

import java.util.List;
import java.util.Scanner;

import org.example.Controllers.PaymentController;
import org.example.Models.Payment;
import org.example.Utils.*;

public class PaymentView {
    static Scanner input = new Scanner(System.in);

    public static void displayPaymentMenu(List<Payment> payment) {
        displayHeader();
        for (int i = 0; i < payment.size(); i++) {
            displayPayment(payment.get(i));
        }
    }

    public static void displayHeader() {
        System.out.println(AdditionalUtil.headerFormat("Pilih Metode Pembayaran"));
    }

    public static void displayPayment(Payment payment) {
        System.out.println(payment.getId() + ". " + payment.getName());
    }

    public int menuOrderSelection(){
        System.out.print("Pilih =>");
        int choice = input.nextInt();
        return choice;
    }
}
