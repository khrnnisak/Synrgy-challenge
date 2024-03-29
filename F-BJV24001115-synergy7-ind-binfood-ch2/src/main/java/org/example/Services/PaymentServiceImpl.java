package org.example.Services;

import java.util.List;

import org.example.Data;
import org.example.Models.Payment;
import org.example.Utils.FormatMessageUtil;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public List<Payment> getPaymentList() {
        return Data.payments;
    }

    @Override
    public Payment getPaymentById(Long id) {
        for (Payment payment : getPaymentList()) {
            if (payment.getId() == id) {
                return payment;
            }
        }
        return null;

    }

    @Override
    public void create(Payment payment) {
        try {
            if (Data.payments.contains(payment)) {
                FormatMessageUtil.duplicateMessage();
            } else {
                Data.payments.add(payment);
                FormatMessageUtil.succesToAddMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToAddMessage();
            System.out.println(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (getPaymentById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {
                int index = getPaymentList().indexOf(getPaymentById(id));
                Data.payments.remove(index);
                FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToDeleteMessage();
            System.out.println(e);
        }
    }

    @Override
    public void update(Long id, Payment payment) {
        try {
            if (getPaymentById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {

                int index = getPaymentList().indexOf(getPaymentById(id));
                Data.payments.set(index, payment);
                FormatMessageUtil.succesToEditMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToEditMessage();
            System.out.println(e);
        }
    }
}
