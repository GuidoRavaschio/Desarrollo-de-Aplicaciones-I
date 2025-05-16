package com.uade.tpo.TurnosYa.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.uade.tpo.TurnosYa.entity.Doctor;
import com.uade.tpo.TurnosYa.entity.User;
import com.uade.tpo.TurnosYa.entity.dto.AppointmentRequest;

public interface AppointmentServiceInterface {
    public void createAppointment(AppointmentRequest appointmentRequest, User user, Doctor doctor);
    public List<AppointmentRequest> getAppointments(User user);
    public void deleteAppointment(AppointmentRequest appointmentRequest, User user);
    public void editAppointment(AppointmentRequest appointmentRequest, User user, Doctor doctor);
    public AppointmentRequest getImage(Long appointment_id, User user) throws SQLException;
}
