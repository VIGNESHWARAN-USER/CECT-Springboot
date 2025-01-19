package com.cect.backend.Repositories;

import com.cect.backend.Models.Drops;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DropsRepo extends MongoRepository<Drops, String> {
}
