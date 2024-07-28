package com.example.tourtravel.Service.Impl;

import com.example.tourtravel.Entity.Contact;
import com.example.tourtravel.Repo.ContactRepo;
import com.example.tourtravel.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact sendMessage(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public List<Contact> getAllMessages() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getMessageById(Long id) {
        return contactRepo.findById(id).orElse(null);
    }
}
