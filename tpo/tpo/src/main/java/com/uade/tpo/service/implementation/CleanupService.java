package com.uade.tpo.service.implementation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.uade.tpo.repository.TemporaryDataRepository;

@Service
public class CleanupService {

    @Autowired
    private TemporaryDataRepository repository;

    @Scheduled(fixedRate = 60000) 
    public void removeExpiredData() {
        repository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}

