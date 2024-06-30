package com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant;

import java.util.UUID;

import lombok.Data;

@Data
public class MerchantDTO {
    private UUID id;
    private String name;
    private String location;
    private boolean is_opened;
}
