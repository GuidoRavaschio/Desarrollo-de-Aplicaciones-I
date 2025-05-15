package com.uade.tpo.TurnosYa.service.interfaces;

import java.util.List;

import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;

public interface DoctorServiceInterface {
    public void createDoctors();
    public List<DoctorRequest> searchDoctor(String name);
}
