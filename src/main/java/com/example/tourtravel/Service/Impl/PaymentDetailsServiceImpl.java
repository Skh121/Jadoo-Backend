package com.example.tourtravel.Service.Impl;

import com.example.tourtravel.Entity.PaymentDetails;
import com.example.tourtravel.Pojo.PaymentDetailsPojo;
import com.example.tourtravel.Repo.PaymentDetailsRepo;
import com.example.tourtravel.Service.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    private final PaymentDetailsRepo paymentRepository;

    @Override
    public void processPayment(PaymentDetailsPojo paymentDetails) {
        PaymentDetails entity = new PaymentDetails();
        entity.setUsername(paymentDetails.getUsername());
        entity.setEmail(paymentDetails.getEmail());
        entity.setCardNumber(paymentDetails.getCardNumber());
        entity.setCvvNumber(paymentDetails.getCvvNumber());
        entity.setExpiryDate(paymentDetails.getExpiryDate());
        entity.setCustomer(paymentDetails.getCustomer());
        entity.setDestinations(paymentDetails.getDestinations());
        entity.setTotalPeople(paymentDetails.getTotalPeople());
        entity.setTotalPrice(paymentDetails.getTotalPrice());
        entity.setPaymentDateTime(LocalDate.now());

        paymentRepository.save(entity);
    }

    @Override
    public List<PaymentDetailsPojo> getAllPayments() {
        List<PaymentDetails> entityList = paymentRepository.findAll();

        return entityList.stream()
                .map(entity -> new PaymentDetailsPojo(
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getCardNumber(),
                        entity.getCvvNumber(),
                        entity.getExpiryDate(),
                        entity.getCustomer(),
                        entity.getDestinations(),
                        entity.getPaymentDateTime(),
                        entity.getTotalPeople(),
                        entity.getTotalPrice()
                ))
                .collect(Collectors.toList());
    }
}
