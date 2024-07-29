package com.example.tourtravel.Controller;

import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Pojo.CustomerPojo;
import com.example.tourtravel.Service.CustomerService;
import com.example.tourtravel.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")

public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/get")
    public GlobalApiResponse<List<Customer>> getData() {
//        System.out.println(customerService.getAll());

        return GlobalApiResponse.
                <List<Customer>>builder()
                .data(customerService.getAll())
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();

    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Customer> getData(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            return GlobalApiResponse.<Customer>builder()
                    .data(customer.get())
                    .statusCode(200)
                    .message("customer retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Customer>builder()
                    .statusCode(404)
                    .message("customer not found!")
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Integer id) {
        if(!customerService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        customerService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Ground with ID " + id + " deleted successfully")
                .build();
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getCustomerCount() {
        return GlobalApiResponse.<Long>builder()
                .data(customerService.customerCount())
                .statusCode(200)
                .message("Total home count retrieved successfully!")
                .build();
    }

}
