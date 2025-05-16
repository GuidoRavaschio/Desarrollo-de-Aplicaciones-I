package com.uade.tpo.TurnosYa.service.implementation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Company;
import com.uade.tpo.TurnosYa.entity.enumerations.Specialties;
import com.uade.tpo.TurnosYa.repository.DoctorRepository;
import com.uade.tpo.TurnosYa.service.interfaces.DoctorServiceInterface;

public class DoctorService implements DoctorServiceInterface{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private InsuranceDoctorService insuranceDoctorService;

    @Override
    public void createDoctors() {
        SecureRandom random = new SecureRandom();
        List<String> names = List.of("Dr. Emilio", "Dr. Carlos", "Dra. Sofía", "Dra. Lucía", "Dr. Pedro");
        List<String> surnames = List.of("Pérez", "García", "López", "Martínez", "Fernández");
        List<Specialties> specialties = List.of(Specialties.values());
        List<Company> companies = List.of(Company.values());
        for (int i = 0; i < 10; i++) {
            String name = names.get(random.nextInt(names.size())) + surnames.get(random.nextInt(surnames.size()));
            Specialties specialty = specialties.get(random.nextInt(specialties.size()));
            Doctor doctor = Doctor.builder().id(Integer.toUnsignedLong(i)).name(name).specialties(specialty).build();
            doctorRepository.save(doctor);
            int iterations = random.nextInt(companies.size());
            for (int j = 0; j < iterations; j++){
                insuranceDoctorService.setInsuranceDoctor(doctor.getId(), companies.get(random.nextInt(companies.size())));
            }
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
