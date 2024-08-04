package com.example.FBJV24001115synergy7indbinfoodch8.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.mapper.MerchantMapper;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch8.repositories.MerchantRepository;
import com.example.FBJV24001115synergy7indbinfoodch8.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class MerchantServiceImpl implements MerchatService{
    @Autowired MerchantRepository merchantRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired MerchantMapper merchantMapper;

    @Override
    public MerchantResponseDTO createMerchant(MerchantDTO merchantCreateDTO) {
        try {
            Optional<Merchant> existMerchant = Optional
                .ofNullable(merchantRepository
                    .findByNameAndLocation(merchantCreateDTO.getName().toLowerCase(), 
                            merchantCreateDTO.getLocation().toLowerCase()));
            if (existMerchant.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant Already Exist");
            }else{
                Merchant merchant = Merchant.builder()
                        .name(merchantCreateDTO.getName().toLowerCase())
                        .location(merchantCreateDTO.getLocation().toLowerCase())
                        .build();
                merchantRepository.save(merchant);
                merchant.setId(merchant.getId());
                log.info(FormatMessageUtil.succesToAddMessage());
                return merchantMapper.toMerchantResponseDTO(merchant);
            }
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public MerchantResponseDTO updateMerchant(UUID id, MerchantUpdateDTO merchantUpdateDTO) {
        try {
            Merchant existMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));
            existMerchant.setName(merchantUpdateDTO.getName());
            existMerchant.setLocation(merchantUpdateDTO.getLocation());
            merchantRepository.save(existMerchant);
            log.info(FormatMessageUtil.succesToEditMessage());
            return merchantMapper.toMerchantResponseDTO(existMerchant);
        }
        catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public void deleteMerchant(UUID id) {
        try {
            Merchant existMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));
            merchantRepository.delete(existMerchant);
            log.info(FormatMessageUtil.succesToDeleteMessage());
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToDeleteMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
       
    }

    @Override
    public List<MerchantResponseDTO> getAllMerchant() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantResponseDTO> merchantList = merchants
                .stream()
                .map(merchant -> merchantMapper.toMerchantResponseDTO(merchant))
                .collect(Collectors.toList());
        return merchantList;
    }

    @Override
    public List<MerchantResponseDTO> getOpenedMerchant() {
        List<Merchant> merchants = merchantRepository.findOpenedMerchant();
        List<MerchantResponseDTO> merchantList = merchants
                .stream()
                .map(merchant -> merchantMapper.toMerchantResponseDTO(merchant))
                .collect(Collectors.toList());
        return merchantList;
    }

    @Override
    public List<MerchantResponseDTO> getClosedMerchant() {
        List<Merchant> merchants = merchantRepository.findClosedMerchant();
        List<MerchantResponseDTO> merchantList = merchants
                .stream()
                .map(merchant -> merchantMapper.toMerchantResponseDTO(merchant))
                .collect(Collectors.toList());
        return merchantList;
    }

    @Override
    public MerchantResponseDTO switchMerchant(UUID id) {
        try {
            Merchant existMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));
            if (Boolean.TRUE.equals(existMerchant.isOpened())) {
                existMerchant.setOpened(Boolean.FALSE);
            }else{
                existMerchant.setOpened(Boolean.TRUE);
            }
            merchantRepository.save(existMerchant);
            log.info(FormatMessageUtil.succesToEditMessage());
            return merchantMapper.toMerchantResponseDTO(existMerchant);
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
    @Override
    public MerchantResponseDTO getMerchantById(UUID id) {
        try {
            Merchant existMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));
            return merchantMapper.toMerchantResponseDTO(existMerchant);
            
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
    
    
}
