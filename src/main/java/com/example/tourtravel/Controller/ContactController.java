package com.example.tourtravel.Controller;

import com.example.tourtravel.Entity.Contact;
import com.example.tourtravel.Entity.Customer;
import com.example.tourtravel.Pojo.ContactPojo;
import com.example.tourtravel.Service.ContactService;
import com.example.tourtravel.Service.CustomerService;
import com.example.tourtravel.shared.GlobalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<GlobalApiResponse<String>> sendMessage(@RequestBody ContactPojo contactPojo) {
        Customer customer = customerService.getCustomerById(contactPojo.getCustomerId());

        if (customer == null) {
            return new ResponseEntity<>(GlobalApiResponse.<String>builder()
                    .message("Customer not found")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }

        Contact contact = new Contact();
        contact.setName(contactPojo.getName());
        contact.setEmail(contactPojo.getEmail());
        contact.setMessage(contactPojo.getMessage());
        contact.setCustomer(customer);

        contactService.sendMessage(contact);

        return new ResponseEntity<>(GlobalApiResponse.<String>builder()
                .message("Message sent successfully")
                .statusCode(HttpStatus.OK.value())
                .data("Message sent successfully")
                .build(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalApiResponse<List<Contact>>> getAllMessages() {
        List<Contact> contacts = contactService.getAllMessages();
        return new ResponseEntity<>(GlobalApiResponse.<List<Contact>>builder()
                .message("Messages retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(contacts)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse<Contact>> getMessageById(@PathVariable Long id) {
        Contact contact = contactService.getMessageById(id);
        if (contact == null) {
            return new ResponseEntity<>(GlobalApiResponse.<Contact>builder()
                    .message("Message not found")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(GlobalApiResponse.<Contact>builder()
                .message("Message retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(contact)
                .build(), HttpStatus.OK);
    }
}
