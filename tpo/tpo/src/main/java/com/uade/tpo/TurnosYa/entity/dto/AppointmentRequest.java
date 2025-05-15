package com.uade.tpo.TurnosYa.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long id;
    private Long doctorId; 
    private Long userId;
    private LocalDate date;
    private LocalTime time;
    private String image;
}
