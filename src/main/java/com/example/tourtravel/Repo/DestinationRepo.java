package com.example.tourtravel.Repo;

import com.example.tourtravel.Entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DestinationRepo extends JpaRepository<Destinations,Long> {
}
