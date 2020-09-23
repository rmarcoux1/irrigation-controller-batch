package com.projects.irrigationcontrollerbatch.service;

import com.projects.irrigationcontrollerbatch.controller.ZoneNotFoundException;
import com.projects.irrigationcontrollerbatch.model.Zone;
import com.projects.irrigationcontrollerbatch.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
@Service
public class ZoneService {

    @Autowired
    private ZoneRepository repository;

    public Zone createZone(Zone zone) {
        return repository.save(zone);
    }

    public List findAll() {
        return repository.findAll();
    }

    public Zone findZoneById(String id) {
        return repository.findById(id).orElseThrow( ()-> new
                ZoneNotFoundException("Zone not found. Please try again - " + id));
    }

    public ResponseEntity<Zone> updateZone(String zoneId, Zone inputZone) {

        Zone zone = findZoneById(zoneId);

        zone.setLocation(inputZone.getLocation());
        zone.setRunTime(inputZone.getRunTime());

        final Zone updatedZone = repository.save(zone);
        return ResponseEntity.ok(updatedZone);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
