package com.example.tourtravel;

import com.example.tourtravel.Entity.FileData;
import com.example.tourtravel.Entity.Hotels;
import com.example.tourtravel.Repo.HotelRepo;
import com.example.tourtravel.Service.HotelService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HotelRepoTest {

    @MockBean
    private HotelRepo hotelRepo;

    @MockBean
    private StorageService storageService;

    @Autowired
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addHotelTest() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image content".getBytes());
        String fileName = "uploaded_image.jpg";

        when(storageService.uploadImageToFileSystem(any(MockMultipartFile.class))).thenReturn(fileName);

        Hotels hotel = Hotels.builder()
                .hotelName("Luxury Inn")
                .details("A luxurious stay")
                .price(200L)
                .imageData(FileData.builder().build())
                .build();

        // Mock the repository save method to return the hotel object with a non-null ID
        when(hotelRepo.save(any(Hotels.class))).thenAnswer(invocation -> {
            Hotels savedHotel = invocation.getArgument(0);
            savedHotel.setHotelId(1L); // Set a non-null ID
            return savedHotel;
        });

        hotelService.addHotel(hotel, image);

        // Ensure the hotel is saved with the correct image data
        Assertions.assertThat(hotel.getImageData()).isNotNull();
        Assertions.assertThat(hotel.getImageData().getName()).isEqualTo(fileName);
        Assertions.assertThat(hotel.getImageData().getFilePath()).isEqualTo(storageService.FOLDER_PATH + fileName);
        Assertions.assertThat(hotel.getImageData().getType()).isEqualTo(image.getContentType());

        verify(hotelRepo).save(hotel);
        Assertions.assertThat(hotel.getHotelId()).isGreaterThan(0);
    }
    @Test
    public void deleteByIdTest() {
        Long hotelId = 1L;

        doNothing().when(hotelRepo).deleteById(hotelId);

        hotelService.deleteById(hotelId);

        verify(hotelRepo, times(1)).deleteById(hotelId);
    }

    @Test
    public void updateDataTest() throws IOException {
        Long hotelId = 1L;
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image content".getBytes());
        String fileName = "updated_image.jpg";

        Hotels existingHotel = Hotels.builder()
                .hotelId(hotelId)
                .hotelName("Old Hotel")
                .details("Old details")
                .price(100L)
                .imageData(FileData.builder().build())
                .build();

        Hotels updatedHotel = Hotels.builder()
                .hotelName("New Hotel")
                .details("New details")
                .price(200L)
                .build();

        when(hotelRepo.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        when(storageService.uploadImageToFileSystem(any(MockMultipartFile.class))).thenReturn(fileName);

        hotelService.updateData(hotelId, updatedHotel, image);

        Assertions.assertThat(existingHotel.getHotelName()).isEqualTo(updatedHotel.getHotelName());
        Assertions.assertThat(existingHotel.getDetails()).isEqualTo(updatedHotel.getDetails());
        Assertions.assertThat(existingHotel.getPrice()).isEqualTo(updatedHotel.getPrice());
        Assertions.assertThat(existingHotel.getImageData()).isNotNull();
        Assertions.assertThat(existingHotel.getImageData().getName()).isEqualTo(fileName);
        Assertions.assertThat(existingHotel.getImageData().getFilePath()).isEqualTo(storageService.FOLDER_PATH + fileName);
        Assertions.assertThat(existingHotel.getImageData().getType()).isEqualTo(image.getContentType());

        verify(hotelRepo).save(existingHotel);
    }

    @Test
    public void getAllTest() {
        Hotels hotel1 = Hotels.builder()
                .hotelId(1L)
                .hotelName("Hotel One")
                .details("Details One")
                .price(100L)
                .imageData(FileData.builder().build())
                .build();

        Hotels hotel2 = Hotels.builder()
                .hotelId(2L)
                .hotelName("Hotel Two")
                .details("Details Two")
                .price(200L)
                .imageData(FileData.builder().build())
                .build();

        List<Hotels> hotelsList = Arrays.asList(hotel1, hotel2);

        when(hotelRepo.findAll()).thenReturn(hotelsList);

        List<Hotels> result = hotelService.getAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).containsExactlyInAnyOrder(hotel1, hotel2);

        verify(hotelRepo, times(1)).findAll();
    }
}
