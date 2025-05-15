package com.uade.tpo.TurnosYa.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import com.uade.tpo.TurnosYa.entity.User;
import com.uade.tpo.TurnosYa.entity.dto.UserRequest;

public interface UserServiceInterface {
    public User getUser(UserDetails userDetails);
    public void createUser(UserRequest userRequest);
    public void deleteUser(UserDetails userDetails);
    public void editUser(UserDetails userDetails, UserRequest userRequest);
    public void uploadInsurance(UserDetails userDetails, UserRequest userRequest);
    public UserRequest getInsurance(UserDetails userDetails);
}
