package com.uade.tpo.TurnosYa.service.implementation;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.TurnosYa.entity.Appointment;
import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.User;
import com.uade.tpo.TurnosYa.entity.dto.AppointmentRequest;
import com.uade.tpo.TurnosYa.entity.enumerations.Weekdays;
import com.uade.tpo.TurnosYa.repository.AppointmentRepository;
import com.uade.tpo.TurnosYa.service.interfaces.AppointmentServiceInterface;

@Service
public class AppointmentService implements AppointmentServiceInterface{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public void createAppointment(AppointmentRequest appointmentRequest, User user, Doctor doctor) {
        LocalDate date = appointmentRequest.getDate();
        LocalTime time = appointmentRequest.getTime();
        Long doctor_id = doctor.getId();
        validateAppointment(date, time, doctor_id, user.getId());
        Appointment a = new Appointment();
        a.setUser(user);
        a.setDate(date);
        a.setTime(time);
        a.setDoctor(doctor);
        appointmentRepository.save(a);
    }

    @Override
    public List<AppointmentRequest> getAppointments(User user) {
        return appointmentRepository
            .findByUserId(user.getId())
            .stream()
            .map(this::mapToRequest)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteAppointment(AppointmentRequest appointmentRequest, User user) {
        Appointment a = appointmentRepository.findById(appointmentRequest.getId()).orElseThrow(() -> new RuntimeException("No existe ese turno"));
        if (Objects.equals(a.getUser().getId(), user.getId())){
            appointmentRepository.delete(a);
        }else{
            throw new RuntimeException("No tienes acceso a este turno ya que el turno no te pertenece");
        }
    }

    @Override
    public void editAppointment(AppointmentRequest appointmentRequest, User user, Doctor doctor) {
        LocalDate date = appointmentRequest.getDate();
        LocalTime time = appointmentRequest.getTime();
        Long doctor_id = doctor.getId();
        validateAppointment(date, time, doctor_id, user.getId());
        Appointment a = appointmentRepository.findById(appointmentRequest.getId()).orElseThrow(() -> new RuntimeException("No existe ese turno"));
        if (Objects.equals(a.getUser().getId(), user.getId())){
            a.setUser(user);
            a.setDate(date);
            a.setTime(time);
            a.setDoctor(doctor);
            appointmentRepository.save(a);
        }else{
            throw new RuntimeException("No tienes acceso a este turno ya que el turno no te pertenece");
        }
    }

    private void validateAppointment(LocalDate date, LocalTime time, Long doctor_id, Long user_id){
        boolean userAlreadyHasAppointment = appointmentRepository.findAppointment(user_id, time, doctor_id, date);
        boolean appointmentIsNotAvailable = appointmentRepository.checkAppointment(time, doctor_id, date);
        if (!userAlreadyHasAppointment && !appointmentIsNotAvailable){
            boolean dateIsValid = LocalDate.now().isBefore(date);
            Weekdays weekday = getWeekdayFromLocalDate(date);
            boolean doctorIsAvailable = availabilityService.getAvailability(List.of(doctor_id), List.of(weekday), time).size() == 1;
            if (dateIsValid && doctorIsAvailable){

            }else{
                throw new RuntimeException("El doctor no esta disponible esa fecha");
            }
        }else if(userAlreadyHasAppointment){
            throw new RuntimeException("El usuario ya registro este turno");
        }else{
            throw new RuntimeException("El turno no esta disponible");
        }
    }

    private Weekdays getWeekdayFromLocalDate(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek(); // Ej: MONDAY, TUESDAY, etc.
    
    return switch (day) {
        case MONDAY -> Weekdays.Lunes;
        case TUESDAY -> Weekdays.Martes;
        case WEDNESDAY -> Weekdays.Miercoles;
        case THURSDAY -> Weekdays.Jueves;
        case FRIDAY -> Weekdays.Viernes;
        default -> throw new IllegalArgumentException("Día no disponible: " + day);
    };
    }
    private AppointmentRequest mapToRequest(Appointment appointment){
        return AppointmentRequest.builder()
                                .date(appointment.getDate())
                                .doctorId(appointment.getDoctor().getId())
                                .time(appointment.getTime())
                                .userId(appointment.getUser().getId())
                                .id(appointment.getId())
                                .build();
    }

    @Override
    public AppointmentRequest getImage(Long appointment_id, User user) throws SQLException {
        Appointment appointment = appointmentRepository.findById(appointment_id)
            .orElseThrow(() -> new RuntimeException("No se encontro un turno"));

        if (!appointment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acceso no autorizado");
        }

        Blob imageBlob = appointment.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        if (imageBytes == null) {
            throw new RuntimeException("No hay imagen en este turno");
        }

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        AppointmentRequest dto = AppointmentRequest.builder()
            .id(appointment.getId())
            .image(base64Image) 
            .build();

        return dto;
    }

    
}
