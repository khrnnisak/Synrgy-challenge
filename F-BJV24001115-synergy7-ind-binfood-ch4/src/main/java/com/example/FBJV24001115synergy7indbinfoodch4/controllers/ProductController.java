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

    public void updateProduct(UUID id, Double price){;
        productService.updateProduct(id, price);
    }

    public void deleteProduct(UUID id){
        productService.deleteProduct(id);
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

    public int getCountProduct(){
        return productService.countProduct();
    }

    public Product geProductById(UUID id){
        return productService.getProductById(id);
    }


}
