package com.uade.tpo.TurnosYa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.enumerations.Specialties;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    @Query("SELECT d FROM Doctor d WHERE d.specialties = :specialties")
    List<Doctor> findBySpecialties(@Param("specialties") Specialties specialties);

    @Query("SELECT d FROM Doctor d WHERE d.id IN doctors")
    List<Doctor> findDoctors(@Param("doctors") List<Long> doctors);
}
