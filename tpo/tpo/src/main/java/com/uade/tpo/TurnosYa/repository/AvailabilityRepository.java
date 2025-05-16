package com.uade.tpo.TurnosYa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.Availability;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>{
    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctor_id AND a.weekday = :weekday")
    Optional<Availability> findByDoctorAndWeekday(@Param("doctor_id") Long doctor_id, @Param("weekday") Weekdays weekday);

    @Query("SELECT a FROM Availability WHERE a.doctor.id = :doctor_id")
    List<Availability> findAvailabilityByDoctor(@Param("doctor_id") Long doctor_id);
}
