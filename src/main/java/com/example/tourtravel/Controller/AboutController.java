package com.example.tourtravel.Controller;


import com.example.tourtravel.Pojo.AboutPojo;
import com.example.tourtravel.Service.AboutService;
import com.example.tourtravel.shared.GlobalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/about")
public class AboutController {

    private final AboutService aboutService;

    @Autowired
    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @PostMapping
    public ResponseEntity<GlobalApiResponse<Void>> createAbout(@RequestBody AboutPojo aboutPojo) {
        aboutService.createAbout(aboutPojo);
        GlobalApiResponse<Void> response = new GlobalApiResponse<>("About created successfully", null, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalApiResponse<Void>> updateAbout(@PathVariable Integer id, @RequestBody AboutPojo aboutPojo) {
        aboutService.updateAbout(id, aboutPojo);
        GlobalApiResponse<Void> response = new GlobalApiResponse<>("About updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse<Void>> deleteAbout(@PathVariable Integer id) {
        aboutService.deleteAbout(id);
        GlobalApiResponse<Void> response = new GlobalApiResponse<>("About deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse<AboutPojo>> getAbout(@PathVariable Integer id) {
        AboutPojo aboutPojo = aboutService.getAbout(id);
        GlobalApiResponse<AboutPojo> response = new GlobalApiResponse<>("About retrieved successfully", aboutPojo, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GlobalApiResponse<List<AboutPojo>>> getAllAbout() {
        List<AboutPojo> aboutList = aboutService.getAllAbout();
        GlobalApiResponse<List<AboutPojo>> response = new GlobalApiResponse<>("All about entries retrieved successfully", aboutList, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
