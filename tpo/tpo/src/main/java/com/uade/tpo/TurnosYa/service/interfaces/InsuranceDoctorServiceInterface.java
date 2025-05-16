package com.uade.tpo.TurnosYa.service.interfaces;

import java.util.List;

import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.dto.InsuranceDoctorRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Company;

public interface InsuranceDoctorServiceInterface {
    public void setInsuranceDoctor(Long doctor_id, Company company);
    public List<InsuranceDoctorRequest> getInsuranceDoctor(DoctorRequest doctorRequest);
    public void editInsuranceDoctor(InsuranceDoctorRequest insuranceDoctorRequest);
    public void deleteInsuranceDoctor(InsuranceDoctorRequest insuranceDoctorRequest);
}
