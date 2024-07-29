package com.example.tourtravel.Service.Impl;


import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Pojo.CustomerPojo;
import com.example.tourtravel.Repo.CustomerRepo;
import com.example.tourtravel.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    @Override
    public void addCustomer(CustomerPojo customerPojo) {
        Customer customer = new Customer();
        customer.setId(customerPojo.getId());
        customer.setPassword(customerPojo.getPassword());
        customer.setUsername(customerPojo.getUsername());
        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Integer id) {
        this.customerRepo.deleteById(id);
    }

    @Override
    public List<Customer> getAll() {
        return this.customerRepo.findAll();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return this.customerRepo.findById(id);
    }

    public void updateData(Integer id, CustomerPojo customerPojo) {
        Optional<Customer> custoomerOptional = customerRepo.findById(id);
        if (custoomerOptional.isPresent()) {
            Customer existingCustomer = custoomerOptional.get();

            updateCustomerProperties(existingCustomer, customerPojo);
            customerRepo.save(existingCustomer); // Save the updated student
        } else {
            // Handle the case where the student with the given ID does not exist
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
    }

    private void updateCustomerProperties(Customer customer, CustomerPojo customerPojo) {
        customer.setId(customerPojo.getId());
//        customer.setTelNo(customerPojo.getTelNo());
        customer.setPassword(customerPojo.getPassword());
        customer.setUsername(customerPojo.getUsername());
         customerRepo.save(customer);

    }

    @Override
    public boolean existsById(Integer id) {
        return this.customerRepo.existsById(id);
    }
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepo.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public Long customerCount() {
        return customerRepo.count();
    }
}
