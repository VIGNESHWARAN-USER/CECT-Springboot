package com.cect.backend.Repositories;

import com.cect.backend.Models.Details;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepo extends MongoRepository<Details, String> {
}
