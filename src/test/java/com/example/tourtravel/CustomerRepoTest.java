package com.example.tourtravel;

import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Pojo.CustomerPojo;
import com.example.tourtravel.Repo.CustomerRepo;
import com.example.tourtravel.Service.Impl.CustomerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerRepoTest {

    @MockBean
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCustomerTest() {
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.setId(1);
        customerPojo.setUsername("testuser");
        customerPojo.setPassword("testpassword");

        Customer customer = new Customer();
        customer.setId(customerPojo.getId());
        customer.setUsername(customerPojo.getUsername());
        customer.setPassword(customerPojo.getPassword());

        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            savedCustomer.setId(1); // Set a non-null ID
            return savedCustomer;
        });

        customerService.addCustomer(customerPojo);

        verify(customerRepo, times(1)).save(any(Customer.class));
        Assertions.assertThat(customer.getId()).isEqualTo(1);
        Assertions.assertThat(customer.getUsername()).isEqualTo("testuser");
        Assertions.assertThat(customer.getPassword()).isEqualTo("testpassword");
    }

    @Test
    public void getAllTest() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setUsername("user1");
        customer1.setPassword("pass1");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setUsername("user2");
        customer2.setPassword("pass2");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepo.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).containsExactlyInAnyOrder(customer1, customer2);

        verify(customerRepo, times(1)).findAll();
    }
}
