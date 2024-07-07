package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.MerchantRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class MerchantServiceImpl implements MerchatService{
    @Autowired MerchantRepository merchantRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public MerchantDTO createMerchant(MerchantCreateDTO merchantCreateDTO) {
        try {
            Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByNameAndLocation(merchantCreateDTO.getName().toLowerCase(), merchantCreateDTO.getLocation().toLowerCase()));
            if (existMerchant.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
            }else{
                Merchant merchant = Merchant.builder()
                        .name(merchantCreateDTO.getName().toLowerCase())
                        .location(merchantCreateDTO.getLocation().toLowerCase())
                        .build();
                merchantRepository.save(merchant);
                log.info(FormatMessageUtil.succesToAddMessage());
                return modelMapper.map(existMerchant.get(), MerchantDTO.class);
            }
        } catch (Exception e) {
           log.error(FormatMessageUtil.failedToAddMessage());
        }   
        return null;     
    }

    @Override
    public MerchantDTO updateMerchant(UUID id, MerchantUpdateDTO merchantUpdateDTO) {
        try {
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
               log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant choosenMerchant = existMerchant.get();
                choosenMerchant.setName(merchantUpdateDTO.getName());
                choosenMerchant.setLocation(merchantUpdateDTO.getLocation());
                merchantRepository.save(choosenMerchant);
                log.info(FormatMessageUtil.succesToEditMessage());
                return modelMapper.map(choosenMerchant, MerchantDTO.class);
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
        }   
        return null;
    }

    @Override
    public void deleteMerchant(UUID id) {
        try {
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant choosenMerchant = existMerchant.get();
                merchantRepository.delete(choosenMerchant);
                log.info(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToDeleteMessage());
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
    public MerchantDTO switchMerchant(UUID id) {
        try {
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{

                Merchant merchant = existMerchant.get();
                if (Boolean.TRUE.equals(merchant.is_opened())) {
                    merchant.set_opened(Boolean.FALSE);
                }else{
                    merchant.set_opened(Boolean.TRUE);
                }
                merchantRepository.save(merchant);
                log.info(FormatMessageUtil.succesToEditMessage());
                return modelMapper.map(merchant, MerchantDTO.class);
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage());
        }
        return null;
    }

    @Override
    public MerchantDTO getMerchantById(UUID id) {
        try {
            Optional<UUID> merOptional = Optional.ofNullable(id);
            if (!merOptional.isPresent()) {
               log.error(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<Merchant> existMerchant = merchantRepository.findById(id);
            if (!existMerchant.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Merchant merchant = existMerchant.get();
                return modelMapper.map(merchant, MerchantDTO.class);
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.notFoundMessage());
        }
        return null;
    }

    
}
