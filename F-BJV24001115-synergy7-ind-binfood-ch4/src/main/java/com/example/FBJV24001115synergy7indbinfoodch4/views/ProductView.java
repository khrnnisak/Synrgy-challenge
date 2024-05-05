package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.MerchantController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.ProductController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class ProductView {
    @Autowired ProductController productController;
    @Autowired MerchantController merchantController;
    @Autowired MerchantView merchantView;

    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayOrderMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman Product"));
    }
    public void displayOrderMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah Product");
        System.out.println("2. Perbarui Harga Product");
        System.out.println("3. Hapus Product");
        System.out.println("4. Lihat Product di merchant tertentu");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        productController.productMenuSelection(choice);
    }
    public void createView(){
        System.out.println("Tambah Product");
        System.out.print("Masukkan Nama Product : ");
        input.nextLine();
        String nama = input.nextLine();
        System.out.print("Harga : ");
        int harga = input.nextInt();
        System.out.print("Kategori : ");
        CategoryProduct kategori = CategoryProduct.valueOf(input.next().toUpperCase());
        System.out.print("Pilih Merchant : ");
        String merchant = input.next();
        productController.createProduct(nama, harga, kategori, merchant);
    }

    public void updateView(){
        System.out.println("=====================");
        System.out.print("Masukkan nama produk : ");
        input.nextLine();
        String nama = input.nextLine();
        System.out.print("Masukkan harga terbaru");
        int harga = input.nextInt();
        productController.updateProduct(nama, harga);
    }   

    public void deleteView(){
        System.out.println("=====================");
        System.out.print("Masukkan nama produk : ");
        input.nextLine();
        String nama = input.nextLine();
        
        System.out.print("Apakah anda yakin? (Y/N) ");
        String confirm = input.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            productController.deleteProduct(nama);
        }
    
    }

    public void displayProducts(){
        List<Product> products = productController.showAllProducts();
        formatDisplayProduct(products);
    }

    public void formatDisplayProduct(List<Product> products){
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i+1 + ". " + products.get(i).getName() 
                + " | " + products.get(i).getPrice() + " | "
                + " | " + products.get(i).getCategory() + " | "
                );
        }
    }

    public void displayProductMerchant(){
        merchantView.displayAllMerchant();
        System.out.print("Masukkan Merchant : ");
        String merchant = input.next();

        List<Product> products = productController.getProductByMerchant(merchant);
        formatDisplayProduct(products);
        displayMainMenu();
    }

}