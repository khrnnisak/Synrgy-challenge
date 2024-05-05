package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.OrderController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.OrderDetailController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.ProductController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.UserController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class OrderView {
    @Autowired OrderController orderController;
    @Autowired UserController userController;
    @Autowired UserView userView;
    @Autowired ProductView productView;
    @Autowired ProductController productController;
    @Autowired OrderDetailController orderDetailController;

    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayOrderMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman Order"));
    }
    public void displayOrderMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah Order");
        System.out.println("2. Ubah Order");
        System.out.println("3. Hapus Order");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        orderController.orderMenuSelection(choice);
    }
    public void createView(){
        System.out.println("=====================================");
        userView.displayUsers();
        System.out.print("Pemesan : ");
        String pemesan = input.next();
        System.out.print("Masukkan destinasi : ");
        String destinasi = input.next();

        
        UUID user_id = userController.getUserId(pemesan.toLowerCase());
        User user = userController.getUserById(user_id);

        Order order = Order.builder()
            .destination(destinasi.toLowerCase())
            .user(user)
            .isCompleted(false)
            .order_time(LocalDate.now())
            .build();
        Order curOrder = orderController.createdOrder(order);
        orderDetailMenu(curOrder);
        displayOrderMenu();
    }

    public void updateView(){
        System.out.println("=====================================");
        displayOrders();
        System.out.print("Pemesan : ");
        String pemesan = input.next();
        System.out.print("Masukkan destinasi lama: ");
        String destinasi_lama = input.next();
        System.out.print("Masukkan destinasi baru: ");
        String destinasi_baru = input.next();

        UUID user_id = userController.getUserId(pemesan);
        User user = userController.getUserById(user_id);

        UUID order_id = orderController.getOrderId(user, destinasi_lama);
        orderController.updateOrder(order_id, destinasi_baru);

        displayMainMenu();

    }

    public void deleteView(){
        System.out.println("=====================================");
        System.out.print("Pemesan : ");
        String pemesan = input.next();
        System.out.print("Masukkan destinasi : ");
        String destinasi = input.next();

        UUID user_id = userController.getUserId(pemesan);
        User user = userController.getUserById(user_id);

        UUID order_id = orderController.getOrderId(user, destinasi);
        orderController.deleteOrder(order_id);
        displayMainMenu();

    }

    public void displayOrders(){
        System.out.println("");
    }

    public void orderDetailMenu(Order order){
        productView.displayProducts();
        System.out.println("99. Pesan dan bayar");
        System.out.println("0. Kembali ke main menu");
        System.out.print(" Masukkan nama menu: ");
        input.nextLine();
        String choice = input.nextLine();

        UUID product_id = productController.getProductId(choice);
        Product product = productController.geProductById(product_id);
        orderDetailController.menuOrderDetailSelection(order, choice, product);
        
    }

    public int askQty(){
        System.out.print("Masukkan banyak pesanan : ");
        int quantity = input.nextInt();
        return quantity;
    }

    public String getPayment(){
        System.out.println("1. BinarCash");
        System.out.println("2. BinarPay");
        System.out.println("3. BinarPayLater");
        String choice = input.next();
        return choice;
    }
}
