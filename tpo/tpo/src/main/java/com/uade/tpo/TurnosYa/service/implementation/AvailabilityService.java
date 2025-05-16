package com.uade.tpo.TurnosYa.service.implementation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.TurnosYa.entity.Availability;
import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.AvailabilityRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;
import com.uade.tpo.TurnosYa.repository.AvailabilityRepository;
import com.uade.tpo.TurnosYa.service.interfaces.AvailabilityServiceInterface;

@Service
public class AvailabilityService implements AvailabilityServiceInterface{

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Override
    public void createAvailability(Doctor doctor, Weekdays weekday, int shift) {
        availabilityRepository.findByDoctorAndWeekday(doctor.getId(), weekday)
        .orElse(create(doctor,weekday,shift));
    }

    @Override
    public List<AvailabilityRequest> getAvailabilityByDoctor(Long doctor_id) {
        List<Availability> a = availabilityRepository.findAvailabilityByDoctor(doctor_id);
        List<AvailabilityRequest> ars = new ArrayList<>();
        for (Availability availability : a){
            AvailabilityRequest ar = AvailabilityRequest.builder()
                                                        .doctorId(doctor_id)
                                                        .weekday(availability.getWeekday())
                                                        .starTime(availability.getStartTime())
                                                        .endTime(availability.getEndTime())
                                                        .build();
            ars.add(ar);
        }
        return ars;
    }

    @Override
    public List<AvailabilityRequest> getAvailabilityByWeekdayAndTime(Weekdays weekday, LocalTime time) {
        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailabilityByWeekdayAndTime'");
    }

    @Override
    public void deleteAvailability(AvailabilityRequest availabilityRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAvailability'");
    }

    @Override
    public void editAvailability(AvailabilityRequest availabilityRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editAvailability'");
    }

    private Availability create(Doctor doctor, Weekdays weekday, int shift){
        LocalTime startTime;
        LocalTime endTime;
        if (shift == 1){
            startTime = LocalTime.of(8, 0);
            endTime = LocalTime.of(12, 0);
        }else{
            startTime = LocalTime.of(14, 0);
            endTime = LocalTime.of(20, 0);
        }
        Availability a = Availability.builder().doctor(doctor).weekday(weekday).startTime(startTime).endTime(endTime).build();
        availabilityRepository.save(a);
        return a;
    }
    
}
