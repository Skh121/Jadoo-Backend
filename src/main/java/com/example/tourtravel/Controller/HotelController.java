package com.example.tourtravel.Controller;


import com.example.tourtravel.Entity.Destinations;
import com.example.tourtravel.Entity.Hotels;
import com.example.tourtravel.Service.HotelService;
import com.example.tourtravel.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor

public class HotelController {
    @Autowired
    private final HotelService hotelService;

    @GetMapping("/get")
    public GlobalApiResponse<List<Hotels>> getData() {
        List<Hotels> hotels = hotelService.getAll();
        return GlobalApiResponse.<List<Hotels>>builder()
                .data(hotels)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Hotels> createHotel(@RequestPart("hotels") Hotels hotels,
                                                 @RequestPart("image") MultipartFile image) {
        try {
            hotelService.addHotel(hotels, image);
            return GlobalApiResponse.<Hotels>builder()
                    .data(hotels)
                    .statusCode(201)
                    .message("Hotel created successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Hotels>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }

//    @GetMapping("/get/{id}")
//    public GlobalApiResponse<Hotels> getData(@PathVariable Long id) {
//        Optional<Hotels> hotels = hotelService.findById(id);
//        if (hotels.isPresent()) {
//            return GlobalApiResponse.<Hotels>builder()
//                    .data(hotels.get())
//                    .statusCode(200)
//                    .message("Building retrieved successfully!")
//                    .build();
//        } else {
//            return GlobalApiResponse.<Hotels>builder()
//                    .statusCode(404)
//                    .message("Building not found!")
//                    .build();
//        }
//    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getData(@PathVariable Long id) {
        Optional<Hotels> hotels = hotelService.findById(id);
        if (hotels.isPresent()) {
            return ResponseEntity.ok()
                    .body(hotels.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hotel not found!");
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Hotels> updateData(@PathVariable Integer id,
                                                      @RequestPart("hotels") Hotels hotelDetails,
                                                      @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            hotelService.updateData(Long.valueOf(id), hotelDetails, image);
            Hotels updatedHome = hotelService.findById(Long.valueOf(id)).orElse(null);
            return GlobalApiResponse.<Hotels>builder()
                    .data(updatedHome)
                    .statusCode(200)
                    .message("Hotel updated successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Hotels>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Long id) {
        if (!hotelService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Hotel with ID " + id + " not found")
                    .build();
        }

        hotelService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Hotel with ID " + id + " deleted successfully")
                .build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getHomeImage(@PathVariable Integer id) {
        try {
            byte[] imageData = hotelService.getHotelImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getHotelsCount() throws IOException {
        return GlobalApiResponse.<Long>builder()
                .data(hotelService.hotelsCount())
                .statusCode(200)
                .message("Total home count retrieved successfully!")
                .build();
    }

}
