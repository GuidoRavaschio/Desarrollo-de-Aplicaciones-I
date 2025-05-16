package com.uade.tpo.TurnosYa.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private int DNI;
    private String name;
    private String email;
    private String password;
    private String confirm_password;
    private String role;
    private String company;
    private int affiliateNumber;
}
