package com.projects.irrigationcontrollerbatch.controller;

import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import com.projects.irrigationcontrollerbatch.model.Zone;
import com.projects.irrigationcontrollerbatch.service.IrrigationSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
@RestController
public class IrrigationSystemController {

    @Autowired
    private IrrigationSystemService service;


    @PostMapping("/irrigationSystem")
    public IrrigationSystem saveIrrigationSystem(@RequestBody IrrigationSystem irrigationSystem) {
        IrrigationSystem savedIrrigationSystem = service.createIrrigationSystem(irrigationSystem);
        return savedIrrigationSystem;
    }

    @GetMapping("/irrigationSystems")
    public List getAllSystems() {
        return service.findAll();
    }

    @GetMapping("/irrigationSystem/{irrigationSystemId}")
    public IrrigationSystem getIrrigationSystemById(@PathVariable String irrigationSystemId) {
        return service.findIrrigationSystemById(irrigationSystemId);
    }

    @PutMapping("/irrigationSystem/zone/{irrigationSystemId}")
    public ResponseEntity<IrrigationSystem> updateZone(@PathVariable String irrigationSystemId, @RequestBody Zone zone) {
        return service.updateZone(irrigationSystemId, zone);
    }

    @PutMapping("/irrigationSystem/{irrigationSystemId}")
    public ResponseEntity<IrrigationSystem> updateIrrigationSystem(@PathVariable String irrigationSystemId,
                                                                   @RequestBody IrrigationSystem irrigationSystem) {
        return service.updateIrrigationSystem(irrigationSystemId, irrigationSystem);
    }

    @PutMapping("/irrigationSystem/{irrigationSystemId}/zone")
    public ResponseEntity<IrrigationSystem> deleteZone(@PathVariable String irrigationSystemId,
                                                                   @RequestBody Zone zone) {
        return service.deleteZone(irrigationSystemId, zone);
    }

    @DeleteMapping("/irrigationSystem/{irrigationSystemId}")
    public String deleteIrrigationSystem(@PathVariable String irrigationSystemId) {
        service.deleteById(irrigationSystemId);
        return String.format("irrigationSystemId successfully deleted", irrigationSystemId);
    }

}
