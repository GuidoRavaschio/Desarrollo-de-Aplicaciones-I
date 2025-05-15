package com.uade.tpo.TurnosYa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.InsuranceDoctor;

@Repository
public interface InsuranceDoctorRepository extends JpaRepository<InsuranceDoctor, Long>{
    
}
