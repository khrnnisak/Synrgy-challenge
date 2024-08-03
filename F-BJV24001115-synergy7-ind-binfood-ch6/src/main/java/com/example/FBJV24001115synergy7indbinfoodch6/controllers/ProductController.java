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

import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductUpdateDTO;
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
    public ResponseEntity<Map<String, Object>>showAllProducts(){
        Map<String, Object> response = new HashMap<>();
        List<ProductDTO> products = productService.getAllProduct();
        response.put("status", "success");
        if (products.isEmpty()) {
            response.put("data", null);
            response.put("message", "Product is empty");
        }else{
            response.put("data", products);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductCreateDTO productCreateDTO){
        ProductDTO productDTO = productService.createdProduct(productCreateDTO);
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
        response.put("status", "success");
        response.put("data", product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<ProductDTO> geProductById(@PathVariable("id") UUID id){
        ProductDTO product =  productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("by-merchant/{merchant_id}")
    public ResponseEntity<Map<String, Object>> getProductByMerchant(@PathVariable("merchant_id") UUID merchantId){
        Map<String, Object> response = new HashMap<>();
        List<ProductDTO> products = productService.getByMerchant(merchantId);
        response.put("status", "success");
        if (products.isEmpty()) {
            response.put("data", null);
            response.put("message", "Product is empty");
        }else{
            response.put("data", products);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
