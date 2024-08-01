package com.example.tourtravel.Controller;

import com.example.tourtravel.Entity.Orders;
import com.example.tourtravel.Pojo.PaymentRequest;
import com.example.tourtravel.Service.OrderService;
import com.example.tourtravel.shared.GlobalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment2")
public class PaymentsController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/process")
    public Orders processPayment(@RequestBody PaymentRequest paymentRequest) {
        return orderService.processPayment(paymentRequest);
    }
    @GetMapping("/get")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getOrdersCount() {
        return GlobalApiResponse.<Long>builder()
                .data(orderService.ordersCount())
                .statusCode(200)
                .message("Total home count retrieved successfully!")
                .build();
    }
}
