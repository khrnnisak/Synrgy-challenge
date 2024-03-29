package org.example.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.example.Models.Item;
import org.example.Models.Order;
import org.example.Models.Payment;
import org.example.Services.OrderService;
import org.example.Services.OrderServiceImpl;
import org.example.Services.PaymentService;
import org.example.Services.PaymentServiceImpl;
import org.example.Utils.AdditionalUtil;
import org.example.Utils.FormatMessageUtil;
import org.example.Views.MainView;
import org.example.Views.OrderView;
import org.example.Views.PaymentView;

public class OrderController {
    public void addOrder(Item item, int quantity) {
        OrderService o_serv = new OrderServiceImpl();
        Random rand = new Random();
        long id = rand.nextLong(9999999);

        Order orderItem = new Order(id, item, quantity);
        o_serv.create(orderItem);

    }

    public void deleteOrder(String order) {
        OrderService o_serv = new OrderServiceImpl();
        long id = o_serv.getOrderByName(order);
        o_serv.delete(id);
    }

    public void updateOrder(String orderMenu, int quantity) {
        OrderService o_serv = new OrderServiceImpl();
        long id = o_serv.getOrderByName(orderMenu);
        o_serv.update(id, quantity);
    }

    public String displayReceipt() {
        OrderService o_serv = new OrderServiceImpl();
        return o_serv.getListToString();
    }

    public void menuOrderSelection(int choice) {
        MainView mv = new MainView();
        PaymentController p_cont = new PaymentController();
        PaymentView p_view = new PaymentView();
        PaymentService p_serv = new PaymentServiceImpl();
        OrderView o_view = new OrderView();
        Scanner input = new Scanner(System.in);
        switch (choice) {
            case 1:
                p_cont.displayPayment();
                int id = p_view.menuOrderSelection();
                Payment payment = p_serv.getPaymentById((long) id);
                printReceipt(payment);
                System.exit(0);
                break;
            case 2:
                o_view.updateOrder();
                mv.displayMainMenu();
                break;
            case 3:
                o_view.deleteOrder();
                mv.displayMainMenu();
                break;
            case 4:
                mv.displayMainMenu();
                break;
            case 0:
                System.out.print("Apakah anda yakin untuk keluar? (Y/N)");
                String confirm = input.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    System.exit(0);
                }
                mv.displayMainMenu();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                mv.displayMainMenu();
                break;
        }
    }

    public void printReceipt(Payment payment) {
        OrderService o_serv = new OrderServiceImpl();
        String path = AdditionalUtil.pathFormat();

        File file = new File(path);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(o_serv.getreceipt(payment.getName()));
                fileWriter.close();
                FormatMessageUtil.successMessageFormat("Nota Berhasil Dicetak");
            } else {
                FormatMessageUtil.errorMessageFormat("File already exists");
            }
        } catch (IOException e) {
            FormatMessageUtil.errorMessageFormat("Terjadi error");
            throw new RuntimeException(e);
        }
    }

    public static List<Order> getOrderList() {
        OrderService o_serv = new OrderServiceImpl();
        List<Order> o_List = o_serv.getOrderList();

        return o_List;

    }

}
