package com.projects.irrigationcontrollerbatch.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Ryan G. Marcoux
 */
@Data
@Entity
public class Zone {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String id;
    private int zoneNumber;
    private boolean activeIndicator;
    private String location;
    private int runTime;
}
