package com.uade.tpo.TurnosYa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>{
    
}
