package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.Models.Payment;
import org.example.Services.PaymentService;
import org.example.Services.PaymentServiceImpl;
import org.example.Utils.FormatMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PaymentServiceImplTest {
    PaymentService payment;

    @BeforeEach
    void instantiatePayment(){
        Data.initiatePayments();
    }

    @Test
    @DisplayName("Positive Case - Add Payment")
    void successToAdd() {
        int prev_size = Data.payments.size();
        payment = new PaymentServiceImpl();
        payment.create(new Payment(4, "BinarLater"));
        int new_size = Data.payments.size();
        assertEquals(prev_size+1, new_size);
    }

    @Test
    @DisplayName("Negative Case - Add Payment - Duplicate Issue")
    void duplicateIssue(){
        payment = new PaymentServiceImpl();
        String result = payment.create(new Payment(1, "BinarCash"));
        assertEquals(FormatMessageUtil.duplicateMessage(), result);
    }

    @Test
    @DisplayName("Negative Case - Add Payment - Null Issue")
    void nullIssue(){
        payment = new PaymentServiceImpl();
        String result = payment.create(null);
        assertEquals(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Payment"), result);
    }
    
    @Test
    @DisplayName("Positive Case - Delete Payment ")
    void successDelete(){
        int size = Data.payments.size();
        payment = new PaymentServiceImpl();
        payment.delete(1L);
        int new_size = Data.payments.size();
        assertEquals(size-1, new_size);
    }

    @Test
    @DisplayName("Negative Case - Delete Payment - Not Found Issue")
    void notFoundDelete(){
        payment = new PaymentServiceImpl();
        String result = payment.delete(10L);
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }

    @Test
    @DisplayName("Positive Case - Get Payment By ID")
    void testGetPaymentById() {
        payment = new PaymentServiceImpl();
        Payment paymentExist = payment.getPaymentById(1L);
        assertEquals("BinarCash", paymentExist.getName());
    }

    @Test
    @DisplayName("Positive Case - Update Item ")
    void successUpdate(){
        payment = new PaymentServiceImpl();
        payment.update(1L, new Payment(1L, "BinarLater"));
        Payment newPayment = payment.getPaymentById(1L);
        assertEquals("BinarLater", newPayment.getName());
    }

    @Test
    @DisplayName("Negative Case - Update Item - Not Found Issue")
    void notFoundUpdate(){
        payment = new PaymentServiceImpl();
        String result = payment.update(10L, new Payment(1L, "BinarLater"));
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }
}
