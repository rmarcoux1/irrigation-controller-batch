package com.projects.irrigationcontrollerbatch.repository;

import com.projects.irrigationcontrollerbatch.model.Zone;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ryan G. Marcoux
 */
public interface ZoneRepository extends MongoRepository<Zone, String> {

}
