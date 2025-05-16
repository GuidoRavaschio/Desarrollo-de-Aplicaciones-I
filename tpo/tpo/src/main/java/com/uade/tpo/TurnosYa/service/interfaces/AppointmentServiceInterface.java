package com.uade.tpo.TurnosYa.service.interfaces;

import com.uade.tpo.TurnosYa.entity.dto.AppointmentRequest;

public interface AppointmentServiceInterface {
    public void createAppointment();
    public AppointmentRequest getAppointment();
    public void deleteAppointment(AppointmentRequest appointmentRequest);
    public void editAppointment(AppointmentRequest appointmentRequest);
}
