package com.example.tourtravel.Service;

import com.example.tourtravel.Entity.Hotels;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    void addHotel(Hotels hotels, MultipartFile image) throws IOException;

    void deleteById(Long id);

    List<Hotels> getAll();

    Optional<Hotels> findById(Long id);
    void updateData(Long id, Hotels hotels, MultipartFile image) throws IOException;
    boolean existsById(Long id);
    byte [] getHotelImage(Integer hotelId) throws IOException;
    Long hotelsCount() throws IOException;
}
