package com.uade.tpo.TurnosYa.service.interfaces;

import com.uade.tpo.TurnosYa.entity.dto.InsuranceDoctorRequest;

public interface InsuranceDoctorServiceInterface {
    public void createAppointment();
    public InsuranceDoctorRequest getAppointment();
    public void deleteAppointment(InsuranceDoctorRequest insuranceDoctorRequest);
    public void editAppointment(InsuranceDoctorRequest insuranceDoctorRequest);
}
