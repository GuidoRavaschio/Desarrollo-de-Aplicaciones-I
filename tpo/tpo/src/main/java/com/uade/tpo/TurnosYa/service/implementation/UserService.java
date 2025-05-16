package com.uade.tpo.TurnosYa.service.implementation;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.TurnosYa.entity.User;
import com.uade.tpo.TurnosYa.entity.dto.UserRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Company;
import com.uade.tpo.TurnosYa.repository.UserRepository;
import com.uade.tpo.TurnosYa.service.interfaces.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(""));
        return u;
    }

    @Override
    public void deleteUser(UserDetails userDetails) {
        User u = getUser(userDetails);
        userRepository.delete(u);
    }

    @Override
    public void editUser(UserDetails userDetails, UserRequest userRequest) {
        User u = getUser(userDetails);
        u.setName(userRequest.getName());
        String password = userRequest.getPassword();
        if (password != null){
            u.setPassword(passwordEncoder.encode(password));
        }else{
            throw new RuntimeException("La contraseña no puede ser vacia");
        }
        userRepository.save(u);
    }

    @Override
    public void uploadInsurance(UserDetails userDetails, UserRequest userRequest) {
        User u = getUser(userDetails);
        u.setCompany(Company.valueOf(userRequest.getCompany()));
        u.setAffiliateNumber(userRequest.getAffiliateNumber());
        userRepository.save(u);
    }

    @Override
    public UserRequest getInsurance(UserDetails userDetails) {
        User u = getUser(userDetails);
        UserRequest userRequest = new UserRequest();
        userRequest.setCompany(u.getCompany().toString());
        userRequest.setAffiliateNumber(u.getAffiliateNumber());
        return userRequest;
    }

    @Override
    public int changePassword(String email){
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); 
        if (!userRepository.existsByEmail(email)){
            throw new RuntimeException("EL email no esta registrado en el sistema");
        }
        return code;
    }
    
}
