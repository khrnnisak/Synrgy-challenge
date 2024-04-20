package org.example.Services;

import java.util.List;

import org.example.Models.Payment;

public interface PaymentService {
    List<Payment> getPaymentList();
    Payment getPaymentById(Long id);

    String create(Payment item);
    String delete(Long id);
    String update(Long id, Payment item);
}
