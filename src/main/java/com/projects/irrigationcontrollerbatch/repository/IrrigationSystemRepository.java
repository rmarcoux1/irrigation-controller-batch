package com.projects.irrigationcontrollerbatch.repository;

import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ryan G. Marcoux
 */
public interface IrrigationSystemRepository extends MongoRepository<IrrigationSystem, String> {

}
