package com.uade.tpo.TurnosYa.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceDoctorRequest {
    private Long id;
    private Long doctorId;
    private String company;
}
