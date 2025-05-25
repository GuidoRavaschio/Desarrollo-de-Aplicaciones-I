package com.uade.tpo.service.implementation;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.controllers.auth.AuthenticationRequest;
import com.uade.tpo.controllers.auth.AuthenticationResponse;
import com.uade.tpo.controllers.auth.RegisterRequest;
import com.uade.tpo.controllers.config.JwtService;
import com.uade.tpo.entity.User;
import com.uade.tpo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                checkForErrors(request);
                var user = User.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .DNI(request.getDNI())
                                .build();
                if(request.getRole() != null){
                        user.setRole(request.getRole());
                }
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        private void checkForErrors(RegisterRequest userRequest){
        int dni = userRequest.getDNI();
        if (!repository.existsByDNI(dni)){
                throw new RuntimeException("El usuario ya existe");
        }
        String email = userRequest.getEmail();
        if (!EmailValidator.getInstance().isValid(email)){
                throw new RuntimeException("Email invalido");
        }
        String password = userRequest.getPassword();
        if (!(password == null ? userRequest.getConfirm_password() == null : password.equals(userRequest.getConfirm_password()))){
                throw new RuntimeException("La contraseña no coincide");
        }
        }
}
