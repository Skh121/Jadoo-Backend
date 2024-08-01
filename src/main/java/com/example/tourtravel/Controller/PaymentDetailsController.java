package com.example.tourtravel.Controller;

import com.example.tourtravel.Pojo.PaymentDetailsPojo;
import com.example.tourtravel.Service.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentDetailsPojo paymentDetails) {
        paymentService.processPayment(paymentDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentDetailsPojo>> getAllPayments() {
        List<PaymentDetailsPojo> paymentDetailsList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentDetailsList);
    }
}
