package com.uade.tpo.TurnosYa.entity.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class AvailabilityRequest {
    private Long id;
    private Long doctorId;
    private String weekday;
    private LocalTime starTime;
    private LocalTime endTime;
}
