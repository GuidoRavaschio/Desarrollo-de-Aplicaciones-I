package com.uade.tpo.TurnosYa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
}
