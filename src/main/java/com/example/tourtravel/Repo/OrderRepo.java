package com.example.tourtravel.Repo;


import com.example.tourtravel.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
}
