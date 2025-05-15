package com.uade.tpo.TurnosYa.entity.dto;

import lombok.Data;

@Data
public class DoctorRequest {
    private Long id;
    private String name;
    private String specialties;
    private String image;
}
