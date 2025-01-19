package com.cect.backend.Services;

import com.cect.backend.Models.Courses;
import com.cect.backend.Models.Credentials;
import com.cect.backend.Repositories.CoursesRepo;
import com.cect.backend.Repositories.CredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoursesServices {

    @Autowired
    CoursesRepo courserepo;
    @Autowired
    CredentialsRepo credrepo;

    public List<Courses> fetchCourses() {
        return courserepo.findAll();
    }

    public void addCourse(Courses req) {
        courserepo.save(req);
    }

    public void deleteCoursewithId(String id) {
        courserepo.deleteById(id);
    }

    public void deleteAllCourses() {
        courserepo.deleteAll();
        Credentials cred =  credrepo.findById("6742b24baea106176ea29af7").orElse(null);
        cred.setCount("0");
    }

    public void saveCourses(List<Courses> courses) {
        courserepo.saveAll(courses);
        Credentials cred =  credrepo.findById("6742b24baea106176ea29af7").orElse(null);
        cred.setCount("0");
    }
}
