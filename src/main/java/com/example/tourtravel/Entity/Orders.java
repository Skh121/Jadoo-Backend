package com.example.tourtravel.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String cardNumber;
    private String cvvNumber;
    private String expiryDate;
    private Double totalAmount;
    private String paymentDateTime;
    @ManyToOne
    private Customer customerId;

    @ElementCollection
    private List<Long> hotelIds;

    @ElementCollection
    private List<Long> destinationIds;

}
