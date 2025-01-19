package com.cect.backend.Controllers;

import com.cect.backend.Models.Courses;
import com.cect.backend.Services.CoursesServices;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CoursesController {

    @Autowired
    CoursesServices service;

    @RequestMapping("/courses")
    public List<Courses> fetchCourses()
    {
        return service.fetchCourses();
    }

    @RequestMapping("/savecourse")
    public Courses saveCourse(@RequestBody Courses newCourse) {
        System.out.println(newCourse);
        service.addCourse(newCourse);
        return newCourse;
    }

    @RequestMapping("/deletecourse/{id}")
    public void deleteCourse(@PathVariable String id)
    {
        System.out.println(id);
        service.deleteCoursewithId(id);
    }

    @RequestMapping("/deletecourses")
    public void deleteAllCourse()
    {
        service.deleteAllCourses();
    }



        @PostMapping("/upload")
        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
            Map<String, String> headerMapping = Map.of(
                    "Course ID", "code",
                    "Course Name", "name",
                    "Duration", "week"
            );

            try {
                // Read the Excel file
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);

                // Use the 11th row (index 10) as headers
                Row headerRow = sheet.getRow(10);
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                // Process rows starting from the 12th row (index 11)
                List<Courses> courses = new ArrayList<>();
                for (int i = 11; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Courses course = new Courses();
                    for (int j = 0; j < headers.size(); j++) {
                        String header = headers.get(j);
                        String schemaField = headerMapping.get(header);

                        if (schemaField != null) {
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                switch (schemaField) {
                                    case "code" -> course.setCode(cell.getStringCellValue());
                                    case "name" -> course.setName(cell.getStringCellValue());
                                    case "week" -> course.setWeek(cell.getStringCellValue());
                                }
                            }
                        }
                    }
                    courses.add(course);
                }

                workbook.close();

                // Save to database
                service.saveCourses(courses);

                return ResponseEntity.ok("Data successfully stored to the database.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to process file: " + e.getMessage());
            }
        }


}
