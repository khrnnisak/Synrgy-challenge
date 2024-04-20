package org.example.Views;

import java.util.List;
import java.util.Scanner;
import org.example.Models.Payment;
import org.example.Utils.*;

public class PaymentView {
    static Scanner input = new Scanner(System.in);

    public static void displayPaymentMenu(List<Payment> payments) {
        displayHeader();
        payments.forEach(payment -> displayPayment(payment));
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
