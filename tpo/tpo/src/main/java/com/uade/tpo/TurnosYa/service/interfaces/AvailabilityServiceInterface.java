package com.uade.tpo.TurnosYa.service.interfaces;

import com.uade.tpo.TurnosYa.entity.dto.AvailabilityRequest;

public interface AvailabilityServiceInterface {
    public void createAvailability();
    public AvailabilityRequest getAvailability();
    public void deleteAvailability(AvailabilityRequest availabilityRequest);
    public void editAvailability(AvailabilityRequest availabilityRequest);
}
