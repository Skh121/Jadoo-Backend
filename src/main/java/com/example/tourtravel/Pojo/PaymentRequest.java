package com.example.tourtravel.Pojo;

import com.example.tourtravel.Entity.Customer;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private String username;
    private String email;
    private String cardNumber;
    private String cvvNumber;
    private String expiryDate;
    private Double totalAmount;
    private String paymentDateTime;
    private Customer customerId;
    private List<BookingItem> bookingItems;

}
