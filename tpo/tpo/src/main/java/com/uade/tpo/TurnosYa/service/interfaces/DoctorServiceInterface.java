package com.uade.tpo.TurnosYa.service.interfaces;

import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;

public interface DoctorServiceInterface {
    public void createDoctors();
    public DoctorRequest getDoctor();
    public void deleteDoctor(DoctorRequest doctorRequest);
    public void editDoctor(DoctorRequest doctorRequest);
}
