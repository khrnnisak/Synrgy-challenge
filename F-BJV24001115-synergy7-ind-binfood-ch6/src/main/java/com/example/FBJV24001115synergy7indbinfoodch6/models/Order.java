package com.example.FBJV24001115synergy7indbinfoodch6.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SQLDelete(sql = "update orders set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class Order extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime orderTime;
    private String destination;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    public enum OrderStatus{
        SELESAI,
        BELUM,
        PROSES,
        BATAL
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    
}
