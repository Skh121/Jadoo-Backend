package com.example.tourtravel.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Destinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    @Column(name = "destinationName", nullable = false, length = 100)
    private String destinationName;

    @Column(name = "details", nullable = false, length = 10000)
    private String details;

    @Column(name = "price", nullable = false, length = 50)
    private Long price;
    private String type="destination";
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_data_id", referencedColumnName = "id")
    private FileData imageData;

}
