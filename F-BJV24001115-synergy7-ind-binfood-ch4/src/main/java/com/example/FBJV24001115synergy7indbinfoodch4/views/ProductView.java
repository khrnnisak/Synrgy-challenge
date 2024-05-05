package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.MerchantController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.ProductController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class ProductView {
    @Autowired ProductController productController;
    @Autowired MerchantController merchantController;

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

        UUID id = merchantController.getMerchantId(merchant.toLowerCase());
        Merchant choosenMerchant = merchantController.getMerchantById(id);

        Product product = Product.builder()
                .name(nama)
                .price((double) harga)
                .category(kategori)
                .merchant(choosenMerchant)
                .build();
        
        productController.createProduct(product);
        displayMainMenu();
    }

    public void updateView(){
        System.out.println("=====================");
        System.out.print("Masukkan nama produk : ");
        input.nextLine();
        String nama = input.nextLine();
        System.out.print("Masukkan harga terbaru");
        int harga = input.nextInt();
        
        UUID id = productController.getProductId(nama);
        productController.updateProduct(id, (double) harga);
        displayMainMenu();
    }

    public void deleteView(){
        System.out.println("=====================");
        System.out.print("Masukkan nama produk : ");
        input.nextLine();
        String nama = input.nextLine();
        UUID id = productController.getProductId(nama);
        productController.deleteProduct(id);
        displayMainMenu();
    }

    public void displayProducts(){
        List<Product> products = productController.showAllProducts();
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i+1 + ". " + products.get(i).getName() 
                + " | " + products.get(i).getPrice() + " | "
                + " | " + products.get(i).getCategory() + " | "
                );
        }
        
    }

    public void displayProductMerchant(){
        
    }

}