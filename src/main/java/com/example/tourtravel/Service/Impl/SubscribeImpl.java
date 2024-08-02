package com.example.tourtravel.Service.Impl;

import com.example.tourtravel.Entity.About;
import com.example.tourtravel.Entity.Subscribe;
import com.example.tourtravel.Pojo.SubscribePojo;
import com.example.tourtravel.Repo.SubscribeRepo;
import com.example.tourtravel.Service.HotelService;
import com.example.tourtravel.Service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeImpl implements SubscribeService {

    private final SubscribeRepo subscribeRepo;

    @Override
    public void addSubscribe(SubscribePojo subscribePojo) {
        Subscribe subscribe = new Subscribe();
        subscribe.setEmail(subscribePojo.getEmail());
        subscribeRepo.save(subscribe);
    }
}
