package com.example.tourtravel.Service;


import com.example.tourtravel.Entity.Contact;

import java.util.List;

public interface ContactService {
    Contact sendMessage(Contact contact);
    List<Contact> getAllMessages();
    Contact getMessageById(Long id);
}
