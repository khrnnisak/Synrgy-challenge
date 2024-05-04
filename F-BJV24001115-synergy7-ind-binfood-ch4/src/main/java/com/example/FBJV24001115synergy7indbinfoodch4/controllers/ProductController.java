package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.services.ProductService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.ProductView;


@Controller

public class ProductController {

    @Autowired ProductService productService;
    @Autowired ProductView productView;
    @Autowired MainView mainView;

    public List<Product> showAllProducts(){
        return productService.getAllProduct();
        // products.forEach(product -> System.out.println(product.getName() + "||" + product.getPrice()));
    }

    public void createProduct(Product product){
        productService.createdProduct(product);
    }

    public void updateProduct(String product_name, Double price){
        showAllProducts();
        UUID product_id = productService.getIdProduct(product_name);
        productService.updateProduct(product_id, price);
        showAllProducts();
    }

    public void deleteProduct(String product_name){
        showAllProducts();
        UUID product_id = productService.getIdProduct(product_name);
        productService.deleteProduct(product_id);
        showAllProducts();
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
                System.out.println("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }


}
