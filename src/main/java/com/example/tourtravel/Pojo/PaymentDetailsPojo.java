package com.example.tourtravel.Pojo;

import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Entity.Destinations;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsPojo {
    private String username;
    private String email;
    private String cardNumber;
    private String cvvNumber;
    private String expiryDate;
    private Customer customer;
    private Destinations destinations;
    private LocalDate paymentDateTime;
    private Integer totalPeople;
    private Integer totalPrice;
}
