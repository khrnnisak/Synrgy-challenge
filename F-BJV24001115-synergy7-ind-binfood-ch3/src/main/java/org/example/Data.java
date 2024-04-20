package org.example;

import java.util.ArrayList;
import java.util.List;

import org.example.Models.Item;
import org.example.Models.Order;
import org.example.Models.Payment;

public class Data {
    public static List<Item> items = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<Payment> payments = new ArrayList();

    public static void initiateItems(){
        items.add(new Item(1, "Nasi Goreng", 15000));
        items.add(new Item(2, "Mie Goreng", 13000));
        items.add(new Item(3, "Nasi + Ayam", 18000));
        items.add(new Item(4, "Es Teh Manis", 3000));
        items.add(new Item(5, "Es Jeruk", 5000));
        items.add(new Item(6, "Klepon", 3000));
    }

    public static void initiatePayments(){
        payments.add(new Payment(1, "BinarCash"));
        payments.add(new Payment(2, "BinarPay"));
        payments.add(new Payment(3, "Pinjam Dulu Seratus"));
    }

    
}
