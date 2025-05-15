package com.uade.tpo.TurnosYa.service.implementation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Specialties;
import com.uade.tpo.TurnosYa.repository.DoctorRepository;
import com.uade.tpo.TurnosYa.service.interfaces.DoctorServiceInterface;

public class DoctorService implements DoctorServiceInterface{

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void createDoctors() {
        SecureRandom random = new SecureRandom();
        List<String> names = List.of("Dr. Emilio", "Dr. Carlos", "Dra. Sofía", "Dra. Lucía", "Dr. Pedro");
        List<String> surnames = List.of("Pérez", "García", "López", "Martínez", "Fernández");
        List<String> specialties = List.of("Pediatria", "Cardiologia", "Traumatologia");
        for (int i = 0; i < 10; i++) {
            String name = names.get(random.nextInt(names.size())) + surnames.get(random.nextInt(surnames.size()));
            String specialty = specialties.get(random.nextInt(specialties.size()));
            Doctor doctor = Doctor.builder().name(name).specialties(Specialties.valueOf(specialty)).build();
            doctorRepository.save(doctor);
        }
    }

    @Override
    public List<DoctorRequest> searchDoctor(String name) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorRequest> results = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getName().contains(name)){
                DoctorRequest d = DoctorRequest.builder().name(doctor.getName()).specialties(doctor.getSpecialties().toString()).build();
                results.add(d);
            }
        }
        return results;
    }
    
}
