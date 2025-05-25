package com.uade.tpo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.entity.dto.DoctorRequest;
import com.uade.tpo.entity.dto.FilterDoctorRequest;
import com.uade.tpo.service.implementation.DoctorService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("api/doctor")
@RequiredArgsConstructor
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> createDoctors() {
        doctorService.createDoctors();
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDoctors(){
        doctorService.deleteDoctors();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<DoctorRequest>> searchDoctor(@PathVariable String name) {
        return ResponseEntity.ok(doctorService.searchDoctor(name));
    }
    
    @PostMapping("/filter")
    public ResponseEntity<List<DoctorRequest>> filterDoctors(
            @RequestBody FilterDoctorRequest filterDoctorRequest) {
        return ResponseEntity.ok(doctorService.filterDoctors(filterDoctorRequest));
    }

    
}
