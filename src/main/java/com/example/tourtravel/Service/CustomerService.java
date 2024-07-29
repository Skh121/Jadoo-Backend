package com.example.tourtravel.Service;


import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Pojo.CustomerPojo;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void addCustomer(CustomerPojo customerPojo);

    void deleteById(Integer id);

    List<Customer> getAll();

    Optional<Customer> findById(Integer id);
    void updateData(Integer id, CustomerPojo customerPojo);
    boolean existsById(Integer id);
    Customer getCustomerById(Long id);
    Long customerCount();
}
