package com.uade.tpo.TurnosYa.service.implementation;

import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    public User getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(""));
        return u;
    }

    @Override
    public void createUser(UserRequest userRequest) {
        User u = new User();
        int dni = userRequest.getDNI();
        if (userRepository.existsByDNI(dni)){
            u.setDNI(dni);
        }else{
            throw new RuntimeException("El usuario ya existe");
        }
        u.setName(userRequest.getName());
        String email = userRequest.getEmail();
        if (EmailValidator.getInstance().isValid(email)){
            u.setEmail(email);
        }else{
            throw new RuntimeException("Email invalido");
        }
        String password = userRequest.getPassword();
        if (password == null ? userRequest.getConfirm_password() == null : password.equals(userRequest.getConfirm_password())){
            u.setPassword(password);
        }else{
            throw new RuntimeException("La contraseña no coincide");
        }
        userRepository.save(u);
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
        if (password == null){
            u.setPassword(password);
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
    
}
