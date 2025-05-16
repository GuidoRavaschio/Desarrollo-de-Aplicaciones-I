package com.uade.tpo.TurnosYa.service.interfaces;

import java.util.List;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.dto.FilterDoctorRequest;

public interface DoctorServiceInterface {
    public void createDoctors();
    public List<DoctorRequest> searchDoctor(String name);
    public Doctor getDoctor(Long doctor_id);
    public List<DoctorRequest> filterDoctors(FilterDoctorRequest filterDoctorRequest);
}
