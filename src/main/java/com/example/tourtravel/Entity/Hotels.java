package com.example.tourtravel.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    @Column(name = "hotelName", nullable = false, length = 100)
    private String hotelName;

    @Column(name = "details", nullable = false, length = 10000)
    private String details;

    @Column(name = "price", nullable = false, length = 50)
    private Long price;
    private String type="hotel";
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_data_id", referencedColumnName = "id")
    private FileData imageData;
}
