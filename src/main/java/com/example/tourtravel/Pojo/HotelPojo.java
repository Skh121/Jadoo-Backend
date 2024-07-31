package com.example.tourtravel.Pojo;

import com.example.tourtravel.Entity.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HotelPojo {
    private Long id;
    private String hotelName;
    private String details;
    private Long price;
    private FileData imageData;
}
