package com.uade.tpo.TurnosYa.service.interfaces;

import java.time.LocalTime;
import java.util.List;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.AvailabilityRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;

public interface AvailabilityServiceInterface {
    public void createAvailability(Doctor doctor, Weekdays weekday, int shift);
    public List<AvailabilityRequest> getAvailabilityByDoctor(Long doctor_id);
    public List<AvailabilityRequest> getAvailabilityByWeekdayAndTime(Weekdays weekday, LocalTime time);
    public void deleteAvailability(AvailabilityRequest availabilityRequest);
    public void editAvailability(AvailabilityRequest availabilityRequest);
}
