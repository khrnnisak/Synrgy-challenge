package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch4.services.ProductService;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.ProductView;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j

public class ProductController {

    @Autowired ProductService productService;
    @Autowired ProductView productView;
    @Autowired MainView mainView;
    @Autowired MerchantController merchantController;

    public List<Product> showAllProducts(){
        return productService.getAllProduct();
    }

    public void createProduct(String nama, int harga, CategoryProduct kategori, String merchant){
        UUID id = merchantController.getMerchantId(merchant.toLowerCase());
        Merchant choosenMerchant = merchantController.getMerchantById(id);

        Product product = Product.builder()
                .name(nama)
                .price((double) harga)
                .category(kategori)
                .merchant(choosenMerchant)
                .build();
        
        productService.createdProduct(product);
        productView.displayMainMenu();

    }

    public void updateProduct(String nama, int harga){
        
        UUID id = getProductId(nama);
        productService.updateProduct(id, (double) harga);
        productView.displayMainMenu();

    }

    public void deleteProduct(String nama){
        
        UUID id = getProductId(nama);
        productService.deleteProduct(id);
        productView.displayMainMenu();

    }

    public UUID getProductId(String productName){
        return productService.getIdProduct(productName);
    }

    public void productMenuSelection(int choice){
        switch (choice) {
            case 1:
                productView.createView();
                break;
            case 2:
                productView.updateView();
                break;
            case 3:
                productView.deleteView();
                break;
            case 4:
                productView.displayProductMerchant();
                break;
            case 0:
                mainView.displayView();
                break;
            default:
                log.error("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }

    public int getCountProduct(){
        return productService.countProduct();
    }

    public Product geProductById(UUID id){
        return productService.getProductById(id);
    }

    public List<Product> getProductByMerchant(String merchant){
        UUID merchant_id = merchantController.getMerchantId(merchant);
        Merchant choosenMerchant = merchantController.getMerchantById(merchant_id);
        List<Product> products = productService.getByMerchant(choosenMerchant);
        return products;
    }

}
