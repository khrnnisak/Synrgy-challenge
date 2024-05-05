package com.example.FBJV24001115synergy7indbinfoodch4.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{

    Product findByName(String product_name);
    List<Product> findByMerchant(Merchant merchant, Pageable pageable);
}
