package org.example.Services;

import java.util.List;
import java.util.Optional;

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
    public String create(Payment payment) {
        try {
            Optional<Payment> opt_payment = Optional.ofNullable(payment);
            if (!opt_payment.isPresent()) {
                return FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Payment");
            }

            Payment getPayment = getPaymentById(payment.getId());
            if (getPayment != null) {
                return FormatMessageUtil.duplicateMessage();
            } else {
                Data.payments.add(payment);
                return FormatMessageUtil.succesToAddMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToAddMessage() + e;
        }
    }

    @Override
    public String delete(Long id) {
        try {
            if (getPaymentById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {
                int index = getPaymentList().indexOf(getPaymentById(id));
                Data.payments.remove(index);
                return FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToDeleteMessage() + e;
        }
    }

    @Override
    public String update(Long id, Payment payment) {
        try {
            if (getPaymentById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {

                int index = getPaymentList().indexOf(getPaymentById(id));
                Data.payments.set(index, payment);
                return FormatMessageUtil.succesToEditMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToEditMessage() + e;
        }
    }
}
