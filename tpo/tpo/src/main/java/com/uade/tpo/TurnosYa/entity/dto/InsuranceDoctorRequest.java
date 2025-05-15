package com.uade.tpo.TurnosYa.entity.dto;

import lombok.Data;

@Data
public class InsuranceDoctorRequest {
    private Long id;
    private Long doctorId;
    private String company;
}
