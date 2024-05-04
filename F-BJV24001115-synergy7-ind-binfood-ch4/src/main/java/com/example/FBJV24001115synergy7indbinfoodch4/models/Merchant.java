package com.example.FBJV24001115synergy7indbinfoodch4.models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant")
@SQLDelete(sql = "update merchant set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class Merchant extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String merchant_location;
    private Boolean isOpened;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<Product> products;
}
