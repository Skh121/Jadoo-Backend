package com.example.tourtravel.Controller;

import com.example.tourtravel.Pojo.SubscribePojo;
import com.example.tourtravel.Service.SubscribeService;
import com.example.tourtravel.shared.GlobalApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;
    @PostMapping
    public ResponseEntity<GlobalApiResponse<Void>> createAbout(@RequestBody SubscribePojo subscribePojo) {
        subscribeService.addSubscribe(subscribePojo);
        GlobalApiResponse<Void> response = new GlobalApiResponse<>("Email Added successfully", null, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
