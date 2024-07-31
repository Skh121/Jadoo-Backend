package com.example.tourtravel.Repo;

import com.example.tourtravel.Entity.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotels,Long> {
}
