package com.uade.tpo.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import com.uade.tpo.entity.User;
import com.uade.tpo.entity.dto.TdRequest;
import com.uade.tpo.entity.dto.UserRequest;

public interface UserServiceInterface {
    public User getUser(UserDetails userDetails);
    public void deleteUser(UserDetails userDetails);
    public void editUser(UserDetails userDetails, UserRequest userRequest);
    public void uploadInsurance(UserDetails userDetails, UserRequest userRequest);
    public UserRequest getInsurance(UserDetails userDetails);
    public void requestChangePassword(String email);
    public void validateCode(TdRequest tdRequest);
    public void changePassword(UserRequest userRequest);
}
