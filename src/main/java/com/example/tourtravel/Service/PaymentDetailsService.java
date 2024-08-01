package com.example.tourtravel.Service;

import com.example.tourtravel.Pojo.PaymentDetailsPojo;

import java.util.List;

public interface PaymentDetailsService {
    void processPayment(PaymentDetailsPojo paymentDetails);
    List<PaymentDetailsPojo> getAllPayments();
}
