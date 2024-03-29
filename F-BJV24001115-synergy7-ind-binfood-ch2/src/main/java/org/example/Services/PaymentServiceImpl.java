package org.example.Services;

import java.util.List;

import org.example.Data;
import org.example.Models.Payment;

public class PaymentServiceImpl implements PaymentService{
    @Override
    public List<Payment> getPaymentList() {
        return Data.payments;
    }

    @Override
    public Payment getPaymentById(Long id) {
        for(Payment payment : getPaymentList()){
            if (payment.getId() == id) {
                return payment;
            }
        }
        return null;

    }

    @Override
    public void create(Payment payment) {
        Data.payments.add(payment);
    }

    @Override
    public void delete(Long id) {
        int index = getPaymentList().indexOf(getPaymentById(id));
        Data.payments.remove(index);
    }

    @Override
    public void update(Long id, Payment payment) {
        int index = getPaymentList().indexOf(getPaymentById(id));
        Data.payments.set(index, payment);
    }
}
