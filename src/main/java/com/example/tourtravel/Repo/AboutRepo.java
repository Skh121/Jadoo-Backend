package com.example.tourtravel.Repo;

import com.example.tourtravel.Entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepo  extends JpaRepository<About, Integer> {
}
