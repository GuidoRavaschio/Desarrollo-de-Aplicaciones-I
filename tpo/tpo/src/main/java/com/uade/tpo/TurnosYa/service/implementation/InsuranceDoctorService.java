package com.uade.tpo.TurnosYa.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.TurnosYa.entity.InsuranceDoctor;
import com.uade.tpo.TurnosYa.entity.dto.DoctorRequest;
import com.uade.tpo.TurnosYa.entity.dto.InsuranceDoctorRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Company;
import com.uade.tpo.TurnosYa.repository.DoctorRepository;
import com.uade.tpo.TurnosYa.repository.InsuranceDoctorRepository;
import com.uade.tpo.TurnosYa.service.interfaces.InsuranceDoctorServiceInterface;

@Service
public class InsuranceDoctorService implements InsuranceDoctorServiceInterface{

    @Autowired
    private InsuranceDoctorRepository insuranceDoctorRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void setInsuranceDoctor(Long doctor_id, Company company) {
        insuranceDoctorRepository.findByDoctorAndCompany(doctor_id, company)
        .orElse(create(doctor_id, company));
    }

    @Override
    public List<InsuranceDoctorRequest> getInsuranceDoctor(DoctorRequest doctorRequest) {
        List<InsuranceDoctor> ids = insuranceDoctorRepository.findByDoctorId(doctorRequest.getId());
        List<InsuranceDoctorRequest> idrs = new ArrayList<>();
        for (InsuranceDoctor id: ids){
            InsuranceDoctorRequest idr = InsuranceDoctorRequest.builder()
                                                                .company(id.getCompany().toString())
                                                                .doctorId(id.getDoctor().getId())
                                                                .build();
            idrs.add(idr);
        }
        return idrs;
    }

    @Override
    public void editInsuranceDoctor(InsuranceDoctorRequest insuranceDoctorRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editInsuranceDoctor'");
    }

    @Override
    public void deleteInsuranceDoctor(InsuranceDoctorRequest insuranceDoctorRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInsuranceDoctor'");
    }
    
    private InsuranceDoctor create(Long doctor_id, Company company){
        InsuranceDoctor insurance = new InsuranceDoctor();
        insurance.setCompany(company);
        insurance.setDoctor(doctorRepository.findById(doctor_id).orElseThrow());
        insuranceDoctorRepository.save(insurance);
        return insurance;
    }
}
