package com.uade.tpo.TurnosYa.entity.dto;

import java.time.LocalTime;
import java.util.List;

import com.uade.tpo.TurnosYa.entity.enumerations.Specialties;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;

import lombok.Data;

@Data
public class FilterDoctorRequest {
    private Specialties specialty;
    private List<Weekdays> weekdays;
    private LocalTime time;
}
