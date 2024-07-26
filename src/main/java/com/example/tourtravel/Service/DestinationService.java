package com.example.tourtravel.Service;

import com.example.tourtravel.Entity.Destinations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DestinationService {
    void addDestination(Destinations destinations, MultipartFile image) throws IOException;

    void deleteById(Long id);

    List<Destinations> getAll();

    Optional<Destinations> findById(Long id);
    void updateData(Long id, Destinations destinations, MultipartFile image) throws IOException;
    boolean existsById(Long id);
    byte [] getDestinationImage(Integer destinationId) throws IOException;
    Long destinationCount();
}
