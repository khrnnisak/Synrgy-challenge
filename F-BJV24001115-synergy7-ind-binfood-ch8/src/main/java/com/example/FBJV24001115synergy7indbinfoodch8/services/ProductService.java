package com.example.FBJV24001115synergy7indbinfoodch8.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.product.ProductCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.product.ProductUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch8.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Product;

public interface ProductService {
    ProductDTO createdProduct(ProductCreateDTO product);
    ProductDTO updateProduct(UUID id, ProductUpdateDTO productUpdateDTO);
    void deleteProduct(UUID id);
    List<ProductDTO> getAllProduct();
    ProductDTO getProductById( UUID id);
    List<ProductDTO> getByMerchant(UUID merchantId);

    void updateStock(List<OrderDetail> orderDetails, String status);

}
