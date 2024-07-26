package com.example.tourtravel.Pojo;

import com.example.tourtravel.Entity.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationPojo {
    private Long id;
    private String destinationName;
    private String details;
    private Long price;
    private FileData imageData;
}
