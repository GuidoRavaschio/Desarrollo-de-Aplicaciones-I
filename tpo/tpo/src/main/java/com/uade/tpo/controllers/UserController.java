package com.uade.tpo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.entity.dto.TdRequest;
import com.uade.tpo.entity.dto.UserRequest;
import com.uade.tpo.service.implementation.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/insurance/upload")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> uploadInsurance(@RequestBody UserRequest userRequest, @AuthenticationPrincipal UserDetails userDetails) {
        userService.uploadInsurance(userDetails, userRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-code/{email}")
    public ResponseEntity<Void> requestCode(@PathVariable String email) {
        userService.requestChangePassword(email);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/validate-code")
    public ResponseEntity<Void> validateCode(@RequestBody TdRequest tdRequest) {
        userService.validateCode(tdRequest);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody UserRequest userRequest) {
        userService.changePassword(userRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/insurance/get")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserRequest> getInsurance(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getInsurance(userDetails));
    }
    
    
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteUser(userDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> editUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequest userRequest) {
        userService.editUser(userDetails, userRequest);
        return ResponseEntity.ok().build();
    }
}
