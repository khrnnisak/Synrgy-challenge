package org.example.Services;

import java.util.List;

import org.example.Models.Payment;

public interface PaymentService {
    List<Payment> getPaymentList();
    Payment getPaymentById(Long id);

    void create(Payment item);
    void delete(Long id);
    void update(Long id, Payment item);
}
