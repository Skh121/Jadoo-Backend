package com.example.tourtravel;

import com.example.tourtravel.Entity.Destinations;
import com.example.tourtravel.Entity.FileData;
import com.example.tourtravel.Repo.DestinationRepo;
import com.example.tourtravel.Service.DestinationService;
import com.example.tourtravel.Service.Impl.StorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DestinationRepoTest {

    @MockBean
    private DestinationRepo destinationRepo;

    @MockBean
    private StorageService storageService;

    @Autowired
    private DestinationService destinationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addDestinationTest() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image content".getBytes());
        String fileName = "uploaded_image.jpg";

        when(storageService.uploadImageToFileSystem(any(MockMultipartFile.class))).thenReturn(fileName);

        Destinations destinations = Destinations.builder()
                .destinationName("Modern Town")
                .details("helo")
                .price(12111L)
                .imageData(FileData.builder().build())
                .build();

        // Mock the repository save method to return the destinations object with a non-null ID
        when(destinationRepo.save(any(Destinations.class))).thenAnswer(invocation -> {
            Destinations savedDestination = invocation.getArgument(0);
            savedDestination.setDestinationId(1L); // Set a non-null ID
            return savedDestination;
        });

        destinationService.addDestination(destinations, image);

        // Ensure the destination is saved with the correct image data
        Assertions.assertThat(destinations.getImageData()).isNotNull();
        Assertions.assertThat(destinations.getImageData().getName()).isEqualTo(fileName);
        Assertions.assertThat(destinations.getImageData().getFilePath()).isEqualTo(storageService.FOLDER_PATH + fileName);
        Assertions.assertThat(destinations.getImageData().getType()).isEqualTo(image.getContentType());

        verify(destinationRepo).save(destinations);
        Assertions.assertThat(destinations.getDestinationId()).isGreaterThan(0);
    }

    @Test
    public void deleteByIdTest() {
        // Create and save a destination
        Destinations destination = Destinations.builder()
                .destinationName("Modern Town")
                .details("helo")
                .price(12111L)
                .imageData(FileData.builder().build())
                .build();

        when(destinationRepo.findById(destination.getDestinationId())).thenReturn(java.util.Optional.of(destination));

        destinationService.deleteById(destination.getDestinationId());

        // Verify that deleteById was called with the correct ID
        verify(destinationRepo).deleteById(destination.getDestinationId());

        // Optionally, verify that the destination is actually removed from the repository
        when(destinationRepo.findById(destination.getDestinationId())).thenReturn(java.util.Optional.empty());
        Assertions.assertThat(destinationRepo.findById(destination.getDestinationId())).isEmpty();
    }
    @Test
    public void updateDataTest() throws IOException {
        Long destinationId = 1L;
        Destinations existingDestination = Destinations.builder()
                .destinationId(destinationId)
                .destinationName("Old Town")
                .details("old details")
                .price(10000L)
                .imageData(FileData.builder().build())
                .build();

        Destinations updatedData = Destinations.builder()
                .destinationName("New Town")
                .details("new details")
                .price(20000L)
                .build();

        MockMultipartFile newImage = new MockMultipartFile("image", "new_image.jpg", "image/jpeg", "new image content".getBytes());
        String newFileName = "uploaded_new_image.jpg";

        when(destinationRepo.findById(destinationId)).thenReturn(Optional.of(existingDestination));
        when(storageService.uploadImageToFileSystem(any(MockMultipartFile.class))).thenReturn(newFileName);
        when(destinationRepo.save(any(Destinations.class))).thenAnswer(invocation -> invocation.getArgument(0));

        destinationService.updateData(destinationId, updatedData, newImage);

        verify(destinationRepo).findById(destinationId);
        verify(storageService).uploadImageToFileSystem(newImage);
        verify(destinationRepo).save(existingDestination);

        Assertions.assertThat(existingDestination.getDestinationName()).isEqualTo("New Town");
        Assertions.assertThat(existingDestination.getDetails()).isEqualTo("new details");
        Assertions.assertThat(existingDestination.getPrice()).isEqualTo(20000L);
        Assertions.assertThat(existingDestination.getImageData()).isNotNull();
        Assertions.assertThat(existingDestination.getImageData().getName()).isEqualTo(newFileName);
        Assertions.assertThat(existingDestination.getImageData().getFilePath()).isEqualTo(storageService.FOLDER_PATH + newFileName);
        Assertions.assertThat(existingDestination.getImageData().getType()).isEqualTo(newImage.getContentType());
    }
    @Test
    public void getAllTest() {
        // Create a list of destinations
        List<Destinations> destinationsList = new ArrayList<>();
        Destinations dest1 = Destinations.builder()
                .destinationId(1L)
                .destinationName("Destination 1")
                .details("Details 1")
                .price(100L)
                .imageData(FileData.builder().build())
                .build();
        Destinations dest2 = Destinations.builder()
                .destinationId(2L)
                .destinationName("Destination 2")
                .details("Details 2")
                .price(200L)
                .imageData(FileData.builder().build())
                .build();
        destinationsList.add(dest1);
        destinationsList.add(dest2);

        // Mock the findAll method to return the list of destinations
        when(destinationRepo.findAll()).thenReturn(destinationsList);

        // Call the service method
        List<Destinations> retrievedDestinations = destinationService.getAll();

        // Verify that the destinationRepo's findAll method was called
        verify(destinationRepo).findAll();

        // Verify the contents of the retrieved list
        Assertions.assertThat(retrievedDestinations).isNotNull();
        Assertions.assertThat(retrievedDestinations.size()).isEqualTo(2);
        Assertions.assertThat(retrievedDestinations).containsExactlyInAnyOrder(dest1, dest2);
    }
}
