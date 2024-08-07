package com.example.FBJV24001115synergy7indbinfoodch8.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID>{
    Merchant findByName(String name);

    Merchant findByNameAndLocation(String name, String location);

    @Query(value = "select * from merchant where opened = true and deleted_date is null", nativeQuery = true)
    List<Merchant> findOpenedMerchant();

    @Query(value = "select * from merchant where opened = false and deleted_date is null", nativeQuery = true)
    List<Merchant> findClosedMerchant();

}
