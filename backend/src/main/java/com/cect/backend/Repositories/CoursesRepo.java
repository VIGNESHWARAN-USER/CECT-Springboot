package com.cect.backend.Repositories;

import com.cect.backend.Models.Courses;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends MongoRepository<Courses, String> {
}
