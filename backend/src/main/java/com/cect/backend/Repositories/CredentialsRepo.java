package com.cect.backend.Repositories;

import com.cect.backend.Models.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepo extends MongoRepository<Credentials, String> {
}
