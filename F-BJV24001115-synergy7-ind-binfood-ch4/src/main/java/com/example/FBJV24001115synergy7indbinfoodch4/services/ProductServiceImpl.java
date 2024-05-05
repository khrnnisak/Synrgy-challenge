package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.ProductRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public void createdProduct(Product product) {
        try {
            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product.getName()));
            if (existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.duplicateMessage());
            }else{
                productRepository.save(product);
                System.out.println(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 
    }

    @Override
    public void updateProduct(UUID id, Double price) {
        try {
            Optional<Product> existProduct = productRepository.findById(id);
            if (!existProduct.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                choosenProduct.setPrice(price);
                productRepository.save(choosenProduct);
                log.info(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 
        
    }

    @Override
    public void deleteProduct(UUID id) {
        try {
            Optional<Product> existProduct = productRepository.findById(id);
            if (!existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                productRepository.delete(choosenProduct);
                System.out.println(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 

    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public UUID getIdProduct(String product_name) {
        try {
            
            Optional<String> opt = Optional.ofNullable(product_name);
            if (!opt.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }

            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product_name));
            if (!existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                return choosenProduct.getId();
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 
        return null;
    }

    @Override
    public int countProduct() {
        return (int) productRepository.count();
    }

    @Override
    public Product getProductById(UUID id) {
        Optional<UUID> opt = Optional.ofNullable(id);
            if (!opt.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }
    @Override
    public List<Product> getByMerchant(Merchant merchant) {
        Optional<Merchant> opt = Optional.ofNullable(merchant);
        if (!opt.isPresent()) {
            System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
        }
        Pageable pageable = PageRequest.of(0, 2);

        List<Product> products = productRepository.findByMerchant(merchant, pageable);
        return products;
    }

    

}
