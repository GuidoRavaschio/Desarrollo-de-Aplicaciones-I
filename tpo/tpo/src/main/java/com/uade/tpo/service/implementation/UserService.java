package com.uade.tpo.service.implementation;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.entity.TemporaryData;
import com.uade.tpo.entity.User;
import com.uade.tpo.entity.dto.TdRequest;
import com.uade.tpo.entity.dto.UserRequest;
import com.uade.tpo.entity.enumerations.Company;
import com.uade.tpo.repository.TemporaryDataRepository;
import com.uade.tpo.repository.UserRepository;
import com.uade.tpo.service.interfaces.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemporaryDataRepository tdRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CryptoService cryptoService;

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
        List<String> emailContent = emailService.createEmailContentForUser(u.getName(), "eliminado");
        userRepository.delete(u);
        emailService.sendEmail(u.getEmail(), emailContent.get(0), emailContent.get(1));
    }

    @Override
    public void editUser(UserDetails userDetails, UserRequest userRequest) {
        User u = getUser(userDetails);
        String name = userRequest.getName();
        if (name != null){
            u.setName(name);
        }
        String password = userRequest.getPassword();
        if (password != null){
            u.setPassword(passwordEncoder.encode(password));
        }else{
            throw new RuntimeException("La contraseña no puede ser vacia");
        }
        userRepository.save(u);
        List<String> emailContent = emailService.createEmailContentForUser(u.getName(), "editado");
        emailService.sendEmail(u.getEmail(), emailContent.get(0), emailContent.get(1));
    }

    @Override
    public void uploadInsurance(UserDetails userDetails, UserRequest userRequest) {
        User u = getUser(userDetails);
        String action;
        if (u.getCompany() == Company.PARTICULAR){
            action = "registrada";
        }else{
            action = "actualizada";
        }
        Company company = userRequest.getCompany();
        u.setCompany(company);
        String affiliate = userRequest.getAffiliateNumber();
        String digits = affiliate.substring(affiliate.length()-4);
        u.setAffiliateNumber(cryptoService.encrypt(affiliate));
        userRepository.save(u);
        List<String> emailContent = emailService.createEmailContentForInsurance(digits, company.toString(), action);
        emailService.sendEmail(u.getEmail(), emailContent.get(0), emailContent.get(1));
    }

    @Override
    public UserRequest getInsurance(UserDetails userDetails) {
        User u = getUser(userDetails);
        UserRequest userRequest = new UserRequest();
        userRequest.setCompany(u.getCompany());
        userRequest.setAffiliateNumber(cryptoService.decrypt(u.getAffiliateNumber()));
        return userRequest;
    }

    @Override
    public void requestChangePassword(String email){
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); 
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("EL email no esta registrado en el sistema"));
        save(code, u);
        List<String> emailContent = emailService.createEmailContentForCode(code, email);
        emailService.sendEmail(email, emailContent.get(0), emailContent.get(1));
    }

    private TemporaryData save(int data, User user) {
        tdRepository.deleteByUser(user);
        TemporaryData temp = TemporaryData.builder()
            .data(data)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(5))
            .user(user)
            .build();

        return tdRepository.save(temp);
}

    @Override
    public void validateCode(TdRequest tdRequest) {
        tdRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        TemporaryData td = tdRepository.findByEmail(tdRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("El codigo ha expirado"));
        if (tdRequest.getCode() != td.getData()){
            throw new RuntimeException("El codigo no es correcto");
        }
        tdRepository.delete(td);
    }

    @Override
    public void changePassword(UserRequest userRequest) {
        User u = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new RuntimeException("El usuario no existe"));
        if (userRequest.getPassword() == null ? userRequest.getConfirm_password() == null : userRequest.getPassword().equals(userRequest.getConfirm_password())){
            u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(u);
        }else{
            throw new RuntimeException("La contraseña no  coincide o se encuentra vacia");
        }
    }
    
}
