package com.example.tourtravel.Service.Impl;

import com.example.tourtravel.Entity.FileData;
import com.example.tourtravel.Entity.Hotels;
import com.example.tourtravel.Repo.HotelRepo;
import com.example.tourtravel.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepo hotelRepo;
    private final StorageService storageService;

    @Override
    public void addHotel(Hotels hotels, MultipartFile image) throws IOException {
        String fileName = storageService.uploadImageToFileSystem(image);
        FileData imageData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();
        hotels.setImageData(imageData);

        hotelRepo.save(hotels);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepo.deleteById(id);

    }

    @Override
    public List<Hotels> getAll() {
        return hotelRepo.findAll();
    }

    @Override
    public Optional<Hotels> findById(Long id) {
        return hotelRepo.findById(id);
    }

    @Override
    public void updateData(Long id, Hotels hotelPojo, MultipartFile image) throws IOException {
        Optional<Hotels> hotelOptional = hotelRepo.findById(id);
        if (hotelOptional.isPresent()) {
            Hotels hotel = hotelOptional.get();
            hotel.setHotelName(hotelPojo.getHotelName());
            hotel.setPrice(hotelPojo.getPrice());
            hotel.setDetails(hotelPojo.getDetails());

            if (image != null && !image.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(image);
                FileData imageData = FileData.builder()
                        .name(fileName)
                        .type(image.getContentType())
                        .filePath(storageService.FOLDER_PATH + fileName)
                        .build();
                hotel.setImageData(imageData);
            }
            hotelRepo.save(hotel);
        }
    }

    @Override
    public byte[] getHotelImage(Integer hotelId) throws IOException {
        System.out.println("Fetching image for hotel ID: " + hotelId);
        Optional<Hotels> optionalHotel = hotelRepo.findById(hotelId.longValue());
        if (optionalHotel.isPresent() && optionalHotel.get().getImageData() != null) {
            String fileName = optionalHotel.get().getImageData().getName();
            if (fileName != null) {
                System.out.println("Found image file name: " + fileName);
                byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
                if (imageData == null) {
                    System.out.println("Failed to retrieve image data for file: " + fileName);
                }
                return imageData;
            } else {
                System.out.println("Image file name is null for hotel ID: " + hotelId);
            }
        } else {
            System.out.println("No image data found for hotel ID: " + hotelId);
        }
        return null;
    }

    @Override
    public Long hotelsCount() throws IOException {
        return hotelRepo.count();
    }

    @Override
    public boolean existsById(Long id) {
        return hotelRepo.existsById(id);
    }
}
