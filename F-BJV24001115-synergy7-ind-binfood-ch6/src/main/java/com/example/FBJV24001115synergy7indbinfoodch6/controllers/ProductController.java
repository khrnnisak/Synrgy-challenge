package com.example.FBJV24001115synergy7indbinfoodch6.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch6.services.ProductService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("product")
public class ProductController {

    @Autowired ProductService productService;
    @Autowired MerchantController merchantController;
    @Autowired ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>>  showAllProducts(){
        List<Product> products = productService.getAllProduct();
        List<ProductDTO> productList = products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductCreateDTO productCreateDTO){
        ResponseEntity<MerchantDTO> choosenMerchant = merchantController.getMerchantById(productCreateDTO.getMerchant_id());
        MerchantDTO merchantDTO = choosenMerchant.getBody();
        Merchant merchant = modelMapper.map(merchantDTO, Merchant.class);
        CategoryProduct category = CategoryProduct.valueOf(productCreateDTO.getCategory().toString().toUpperCase());
        Product product = Product.builder()
                .name(productCreateDTO.getName().toLowerCase())
                .price((double) productCreateDTO.getPrice())
                .category(category)
                .merchant(merchant)
                .build();

        ProductDTO productDTO = productService.createdProduct(product);
        Map<String, Object> response = new HashMap<>();

        response.put("status", "suscces");
        response.put("data", productDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable("id") UUID id, @RequestBody ProductUpdateDTO productUpdateDTO){
        Map<String, Object> response = new HashMap<>();

        ProductDTO product = productService.updateProduct(id, productUpdateDTO);
        response.put("status", "suscces");
        response.put("data", product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<ProductDTO> geProductById(@PathVariable("id") UUID id){
        ProductDTO product =  productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("by-merchant/{merchant_id}")
    public ResponseEntity<List<ProductDTO>> getProductByMerchant(@PathVariable("merchant_id") UUID merchant_id){
        ResponseEntity<MerchantDTO> choosenMerchant = merchantController.getMerchantById(merchant_id);
        MerchantDTO merchantDTO = choosenMerchant.getBody();
        Merchant merchant = modelMapper.map(merchantDTO, Merchant.class);
        List<Product> products = productService.getByMerchant(merchant);
        List<ProductDTO> productList = products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
