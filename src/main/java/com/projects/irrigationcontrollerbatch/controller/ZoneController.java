package com.projects.irrigationcontrollerbatch.controller;

import com.projects.irrigationcontrollerbatch.model.Zone;
import com.projects.irrigationcontrollerbatch.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
@RestController
public class ZoneController {

    @Autowired
    private ZoneService service;


    @PostMapping("/zone")
    public Zone saveNewZone(@RequestBody Zone zone) {
        Zone savedZone = service.createZone(zone);
        return savedZone;
    }

    @GetMapping("/zones")
    public List getAllZones() {
        return service.findAll();
    }

    @GetMapping("/zone/{zoneId}")
    public Zone getZoneById(@PathVariable String zoneId) {
        return service.findZoneById(zoneId);
    }

    @PutMapping("/zone/{zoneId}")
    public ResponseEntity<Zone> updateZone(@PathVariable String zoneId, @RequestBody Zone zone) {
        return service.updateZone(zoneId, zone);
    }

    @DeleteMapping("/zone/{zoneId}")
    public String deleteZone(@PathVariable String zoneId) {
        service.deleteById(zoneId);
        return String.format("ZoneId #%d successfully deleted", zoneId);
    }

}
