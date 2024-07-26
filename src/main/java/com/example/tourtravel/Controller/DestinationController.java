package com.example.tourtravel.Controller;

import com.example.tourtravel.Entity.Destinations;
import com.example.tourtravel.Service.DestinationService;
import com.example.tourtravel.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destination")
@RequiredArgsConstructor

public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping("/get")
    public GlobalApiResponse<List<Destinations>> getData() {
        List<Destinations> destinations = destinationService.getAll();
        return GlobalApiResponse.<List<Destinations>>builder()
                .data(destinations)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping(value="/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Destinations> createDestination(@RequestPart("destinations")Destinations destinations,
                                                             @RequestPart("image") MultipartFile image) {
        try{
            destinationService.addDestination(destinations,image);
            return GlobalApiResponse.<Destinations>builder()
                    .data(destinations)
                    .statusCode(201)
                    .message("Home created successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Destinations>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
        }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getData(@PathVariable Long id) {
        Optional<Destinations> destinations = destinationService.findById(id);
        if (destinations.isPresent()) {
            return ResponseEntity.ok()
                    .body(destinations.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Building not found!");
        }
    }
@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public GlobalApiResponse<Destinations> updateData(@PathVariable Integer id,
                                          @RequestPart("destinations") Destinations destinationDetails,
                                          @RequestPart(value = "image", required = false) MultipartFile image) {
    try {
        destinationService.updateData(Long.valueOf(id), destinationDetails, image);
        Destinations updatedHome = destinationService.findById(Long.valueOf(id)).orElse(null);
        return GlobalApiResponse.<Destinations>builder()
                .data(updatedHome)
                .statusCode(200)
                .message("Destination updated successfully!")
                .build();
    } catch (IOException e) {
        return GlobalApiResponse.<Destinations>builder()
                .statusCode(500)
                .message("Failed to process image!")
                .build();
    }
}


@DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Long id) {
        if (!destinationService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        destinationService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("destination with ID " + id + " deleted successfully")
                .build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getHomeImage(@PathVariable Integer id) {
        try {
            byte[] imageData = destinationService.getDestinationImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getDestinationCount() {
        return GlobalApiResponse.<Long>builder()
                .data(destinationService.destinationCount())
                .statusCode(200)
                .message("Total home count retrieved successfully!")
                .build();
    }

}
