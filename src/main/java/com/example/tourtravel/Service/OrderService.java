package com.example.tourtravel.Service;

import com.example.tourtravel.Entity.Orders;
import com.example.tourtravel.Pojo.BookingItem;
import com.example.tourtravel.Pojo.PaymentRequest;
import com.example.tourtravel.Repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    public Orders processPayment(PaymentRequest paymentRequest) {
        Orders order = new Orders();
        order.setUsername(paymentRequest.getUsername());
        order.setEmail(paymentRequest.getEmail());
        order.setCardNumber(paymentRequest.getCardNumber());
        order.setCvvNumber(paymentRequest.getCvvNumber());
        order.setExpiryDate(paymentRequest.getExpiryDate());
        order.setTotalAmount(paymentRequest.getTotalAmount());
        order.setPaymentDateTime(paymentRequest.getPaymentDateTime());
        order.setCustomerId(paymentRequest.getCustomerId());

        List<Long> hotelIds = paymentRequest.getBookingItems().stream()
                .filter(item -> "hotel".equals(item.getType()))
                .map(BookingItem::getId)
                .collect(Collectors.toList());

        List<Long> destinationIds = paymentRequest.getBookingItems().stream()
                .filter(item -> "destination".equals(item.getType()))
                .map(BookingItem::getId)
                .collect(Collectors.toList());

        order.setHotelIds(hotelIds);
        order.setDestinationIds(destinationIds);

        return orderRepository.save(order);
    }
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    public Long ordersCount() {
        return orderRepository.count();
    }
}