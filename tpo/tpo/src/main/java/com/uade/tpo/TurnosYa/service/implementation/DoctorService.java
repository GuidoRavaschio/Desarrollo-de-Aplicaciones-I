package com.uade.tpo.TurnosYa.service.implementation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.AvailabilityRequest;
import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.dto.FilterDoctorRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Company;
import com.uade.tpo.TurnosYa.entity.enumerations.Specialties;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;
import com.uade.tpo.TurnosYa.repository.DoctorRepository;
import com.uade.tpo.TurnosYa.service.interfaces.DoctorServiceInterface;

public class DoctorService implements DoctorServiceInterface{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private InsuranceDoctorService insuranceDoctorService;

    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public void createDoctors() {
        SecureRandom random = new SecureRandom();
        List<String> names = List.of("Dr. Emilio", "Dr. Carlos", "Dra. Sofía", "Dra. Lucía", "Dr. Pedro");
        List<String> surnames = List.of("Pérez", "García", "López", "Martínez", "Fernández");
        List<Specialties> specialties = List.of(Specialties.values());
        List<Company> companies = List.of(Company.values());
        List<Weekdays> weekdays = List.of(Weekdays.values());
        for (int i = 0; i < 10; i++) {
            String name = names.get(random.nextInt(names.size())) + surnames.get(random.nextInt(surnames.size()));
            Specialties specialty = specialties.get(random.nextInt(specialties.size()));
            Doctor doctor = Doctor.builder().id(Integer.toUnsignedLong(i)).name(name).specialties(specialty).build();
            doctorRepository.save(doctor);
            int iterations = random.nextInt(companies.size());
            for (int j = 0; j < iterations; j++){
                insuranceDoctorService.setInsuranceDoctor(doctor.getId(), companies.get(random.nextInt(companies.size())));
            }
            iterations = random.nextInt(weekdays.size());
            for (int j = 0; j < iterations; j++){
                int shift = random.nextInt(1); // 1:Morning, 2:Afternoon
                availabilityService.createAvailability(doctor, weekdays.get(random.nextInt(weekdays.size())), shift);
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

    @Override
    public Doctor getDoctor(Long doctor_id) {
        return doctorRepository.findById(doctor_id).orElseThrow(() -> new RuntimeException("El doctor no existe"));
    }

    @Override
    public List<DoctorRequest> filterDoctors(FilterDoctorRequest filterDoctorRequest) {
        List<Doctor> doctors = doctorRepository.findBySpecialties(filterDoctorRequest.getSpecialty());
        List<Long> doctor_ids = new ArrayList<>();
        for (Doctor doctor : doctors){
            doctor_ids.add(doctor.getId());
        }
        List<AvailabilityRequest> ars = availabilityService.getAvailability(doctor_ids, filterDoctorRequest.getWeekdays(), filterDoctorRequest.getTime());
        List<Long> ids_doctors = new ArrayList<>();
        for (AvailabilityRequest ar : ars){
            ids_doctors.add(ar.getDoctorId());
        }
        List<Doctor> doc = doctorRepository.findDoctors(ids_doctors);
        return mapToRequest(doc);
    }
    
    private List<DoctorRequest> mapToRequest(List<Doctor> doctors){
        List<DoctorRequest> results = new ArrayList<>();
        for (Doctor doctor : doctors) {
                DoctorRequest d = DoctorRequest.builder().name(doctor.getName()).specialties(doctor.getSpecialties().toString()).build();
                results.add(d);
            }
        return results;
    }
}
