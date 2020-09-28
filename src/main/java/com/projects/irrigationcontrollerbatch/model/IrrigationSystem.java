package com.projects.irrigationcontrollerbatch.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
@Data
@Entity
public class IrrigationSystem {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String id;
    private boolean active;
    private String frequency;
    private String startTime;
    @OneToMany
    private List<Zone> zones = new ArrayList<>();

}
