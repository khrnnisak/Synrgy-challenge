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
@Table(name = "product")
@SQLDelete(sql = "update product set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class Product extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private CategoryProduct category;
    public enum CategoryProduct{
        BAVERAGES,
        DESSERTS,
        FOODS
    }

    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
