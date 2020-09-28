package com.projects.irrigationcontrollerbatch.service;

import com.projects.irrigationcontrollerbatch.controller.IrrigationSystemNotFoundException;
import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import com.projects.irrigationcontrollerbatch.model.Zone;
import com.projects.irrigationcontrollerbatch.repository.IrrigationSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Ryan G. Marcoux
 */
@Service
public class IrrigationSystemService {

    @Autowired
    private IrrigationSystemRepository repository;

    public IrrigationSystem createIrrigationSystem(IrrigationSystem irrigationSystem) {
        return repository.save(irrigationSystem);
    }

    public List findAll() {
        return repository.findAll();
    }

    public IrrigationSystem findIrrigationSystemById(String id) {
        return repository.findById(id).orElseThrow( ()-> new
                IrrigationSystemNotFoundException("Irrigation System not found. Please try again - " + id));
    }

    public ResponseEntity<IrrigationSystem> updateZone
            (String irrigationSystemId, Zone inputZone) {

        IrrigationSystem irrigationSystem = findIrrigationSystemById(irrigationSystemId);
        if (null != irrigationSystem) {
            Optional<Zone> zoneRecord = irrigationSystem.getZones().stream()
                    .filter(x -> x.getZoneNumber() == inputZone.getZoneNumber())
                    .findFirst();

            if (zoneRecord.isPresent()) {
                Zone updatedZone = zoneRecord.get();
                updatedZone.setRunTime(inputZone.getRunTime());
                updatedZone.setActiveIndicator(inputZone.isActiveIndicator());
                updatedZone.setLocation(inputZone.getLocation());
            }
            else {
                Zone newZone = new Zone();
                newZone.setZoneNumber(inputZone.getZoneNumber());
                newZone.setRunTime(inputZone.getRunTime());
                newZone.setActiveIndicator(inputZone.isActiveIndicator());
                newZone.setLocation(inputZone.getLocation());
                irrigationSystem.getZones().add(newZone);
            }
        }

        final IrrigationSystem updateIrrigationSystem = repository.save(irrigationSystem);
        return ResponseEntity.ok(updateIrrigationSystem);
    }

    public ResponseEntity<IrrigationSystem> deleteZone
            (String irrigationSystemId, Zone inputZone) {

        IrrigationSystem irrigationSystem = findIrrigationSystemById(irrigationSystemId);
        if (null != irrigationSystem) {
            Optional<Zone> zoneRecord = irrigationSystem.getZones().stream()
                    .filter(x -> x.getZoneNumber() == inputZone.getZoneNumber())
                    .findFirst();

            if (zoneRecord.isPresent()) {

                irrigationSystem.getZones().removeIf(e ->e.getZoneNumber() ==inputZone.getZoneNumber());
            }
        }

        final IrrigationSystem updateIrrigationSystem = repository.save(irrigationSystem);
        return ResponseEntity.ok(updateIrrigationSystem);
    }

    public ResponseEntity<IrrigationSystem> updateIrrigationSystem
            (String irrigationSystemId, IrrigationSystem inputIrrigationSystem) {

        IrrigationSystem irrigationSystem = findIrrigationSystemById(irrigationSystemId);
        if (null != irrigationSystem) {
            irrigationSystem.setFrequency(inputIrrigationSystem.getFrequency());
            irrigationSystem.setStartTime(inputIrrigationSystem.getStartTime());
            inputIrrigationSystem.setActive(inputIrrigationSystem.isActive());

        }

        final IrrigationSystem updateIrrigationSystem = repository.save(irrigationSystem);
        return ResponseEntity.ok(updateIrrigationSystem);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
