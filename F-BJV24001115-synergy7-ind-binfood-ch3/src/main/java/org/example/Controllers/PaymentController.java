package org.example.Controllers;

import java.util.List;

import org.example.Models.Payment;
import org.example.Services.PaymentService;
import org.example.Services.PaymentServiceImpl;
import org.example.Views.PaymentView;

public class PaymentController {

    public static void displayPayment(){
        PaymentService p_serv = new PaymentServiceImpl();
        List<Payment> p_list = p_serv.getPaymentList();

        PaymentView p_view = new PaymentView();
        p_view.displayPaymentMenu(p_list);

    }

}
