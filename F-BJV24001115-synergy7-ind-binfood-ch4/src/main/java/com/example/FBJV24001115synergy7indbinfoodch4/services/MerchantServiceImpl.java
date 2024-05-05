package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.MerchantRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class MerchantServiceImpl implements MerchatService{
    @Autowired 
    MerchantRepository merchantRepository;

    @Override
    public void createMerchant(Merchant merchant) {
        try {
            Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant.getName()));
            if (existMerchant.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
            }else{
                merchantRepository.save(merchant);
                log.info(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        }        
    }

    @Override
    public void updateMerchant(UUID id, String location) {
        try {
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant choosenMerchant = existMerchant.get();
                choosenMerchant.setMerchant_location(location);
                merchantRepository.save(choosenMerchant);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToEditMessage());
        }   
    }

    @Override
    public void deleteMerchant(UUID id) {
        try {
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant choosenMerchant = existMerchant.get();
                merchantRepository.delete(choosenMerchant);
                System.out.println(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToDeleteMessage());
        }  
       
    }

    @Override
    public List<Merchant> getAllMerchant() {
        return merchantRepository.findAll();
    }

    @Override
    public List<Merchant> getOpenedMerchant() {
        return merchantRepository.findOpenedMerchant();
    }

    @Override
    public List<Merchant> getClosedMerchant() {
        return merchantRepository.findClosedMerchant();
    }

    @Override
    public UUID getMerchantID(String merchant_name) {
        try {
            Optional<String> merOptional = Optional.ofNullable(merchant_name);
            if (!merOptional.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant_name));
            if (!existMerchant.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant merchant = existMerchant.get();
                return merchant.getId();
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.notFoundMessage());
        }
        return null;  
    }

    @Override
    public void switchMerchant(String merchant_name) {
        try {
            Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant_name));
            if (!existMerchant.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{

                Merchant merchant = existMerchant.get();
                if (Boolean.TRUE.equals(merchant.getIsOpened())) {
                    merchant.setIsOpened(false);
                }else{
                    merchant.setIsOpened(true);
                }
                merchantRepository.save(merchant);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToEditMessage());
        }
        
    }

    @Override
    public Merchant getMerchantById(UUID id) {
        Optional<UUID> merOptional = Optional.ofNullable(id);
        if (!merOptional.isPresent()) {
            System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
        }
        Optional<Merchant> merchant = merchantRepository.findById(id);
        return merchant.get();
    }

    
}
