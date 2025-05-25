package com.uade.tpo.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.entity.Doctor;
import com.uade.tpo.entity.User;
import com.uade.tpo.entity.dto.AppointmentRequest;
import com.uade.tpo.service.implementation.AppointmentService;
import com.uade.tpo.service.implementation.DoctorService;
import com.uade.tpo.service.implementation.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest appointmentRequest, 
            @AuthenticationPrincipal UserDetails userDetails) {
        User u = userService.getUser(userDetails);
        Doctor d = doctorService.getDoctor(appointmentRequest.getDoctorId());
        appointmentService.createAppointment(appointmentRequest, u, d);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/from-user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<AppointmentRequest>> getAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        User u = userService.getUser(userDetails);
        return ResponseEntity.ok(appointmentService.getAppointments(u));
    }
    
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteAppointment(@RequestBody AppointmentRequest appointmentRequest, 
            @AuthenticationPrincipal UserDetails userDetails){
        User u = userService.getUser(userDetails);
        appointmentService.deleteAppointment(appointmentRequest, u);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> editAppointment(@RequestBody AppointmentRequest appointmentRequest, 
            @AuthenticationPrincipal UserDetails userDetails) {
        User u = userService.getUser(userDetails);
        Doctor d = doctorService.getDoctor(appointmentRequest.getDoctorId());
        appointmentService.editAppointment(appointmentRequest, u, d);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/result/load/{appointmentId}", 
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> setImage(@PathVariable Long appointmentId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) throws SQLException, IOException {
        appointmentService.setImage(appointmentId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/result/{appointmentId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long appointmentId, 
            @AuthenticationPrincipal UserDetails userDetails) throws SQLException {
        User u = userService.getUser(userDetails);
        byte[] image = appointmentService.getImage(appointmentId, u);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
    
}
