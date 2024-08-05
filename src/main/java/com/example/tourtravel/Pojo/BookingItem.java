package com.example.tourtravel.Pojo;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingItem {
    private Long id;
    private String type; // 'hotel' or 'destination'
    private Integer numberOfPeople;
    
}
