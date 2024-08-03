package com.example.FBJV24001115synergy7indbinfoodch6.models;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.FBJV24001115synergy7indbinfoodch6.models.Product.CategoryProduct;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
@SQLDelete(sql = "update order_detail set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class OrderDetail extends BaseModel{  
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer quantity;
    private Double totalPrice;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

}
