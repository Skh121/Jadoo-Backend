package com.example.tourtravel.Service.Impl;

import com.example.tourtravel.Entity.Destinations;
import com.example.tourtravel.Entity.FileData;
import com.example.tourtravel.Repo.DestinationRepo;
import com.example.tourtravel.Service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepo destinationRepo;
    private final StorageService storageService;

    @Override
    public void addDestination(Destinations destinations, MultipartFile image) throws IOException {
        String fileName = storageService.uploadImageToFileSystem(image);
        FileData imageData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();
        destinations.setImageData(imageData);

        destinationRepo.save(destinations);
    }

    @Override
    public void deleteById(Long id) {
        destinationRepo.deleteById(id);
    }

    @Override
    public List<Destinations> getAll() {
        return destinationRepo.findAll();
    }

    @Override
    public Optional<Destinations> findById(Long id) {
        return destinationRepo.findById(id);
    }

    @Override
    public void updateData(Long id, Destinations destinationPojo, MultipartFile image) throws IOException {
        Optional<Destinations> destinationOptional = destinationRepo.findById(id);
        if (destinationOptional.isPresent()) {
            Destinations destination = destinationOptional.get();
            destination.setDestinationName(destinationPojo.getDestinationName());
            destination.setPrice(destinationPojo.getPrice());
            destination.setDetails(destinationPojo.getDetails());

            if (image != null && !image.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(image);
                FileData imageData = FileData.builder()
                        .name(fileName)
                        .type(image.getContentType())
                        .filePath(storageService.FOLDER_PATH + fileName)
                        .build();
                destination.setImageData(imageData);
            }
            destinationRepo.save(destination);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return destinationRepo.existsById(id);
    }

    @Override
    public byte[] getDestinationImage(Integer destinationId) throws IOException {
        System.out.println("Fetching image for destination ID: " + destinationId);
        Optional<Destinations> optionalDestination = destinationRepo.findById(destinationId.longValue());
        if (optionalDestination.isPresent() && optionalDestination.get().getImageData() != null) {
            String fileName = optionalDestination.get().getImageData().getName();
            if (fileName != null) {
                System.out.println("Found image file name: " + fileName);
                byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
                if (imageData == null) {
                    System.out.println("Failed to retrieve image data for file: " + fileName);
                }
                return imageData;
            } else {
                System.out.println("Image file name is null for destination ID: " + destinationId);
            }
        } else {
            System.out.println("No image data found for destination ID: " + destinationId);
        }
        return null;
    }

    @Override
    public Long destinationCount() {
        return destinationRepo.count();
    }

//    public List<Destination> getDestinationsByIds(List<Long> ids) {
//        return destinationRepository.findAllById(ids);
//    }
}
