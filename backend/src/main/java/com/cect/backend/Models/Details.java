package com.cect.backend.Models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "details")
public class Details {
    @Id
    private String  _id;
    private String name;
    private String roll;
    private String sec;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(String offeredBy) {
        this.offeredBy = offeredBy;
    }

    public String getModeOfStudy() {
        return modeOfStudy;
    }

    public void setModeOfStudy(String modeOfStudy) {
        this.modeOfStudy = modeOfStudy;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "Details{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", roll='" + roll + '\'' +
                ", sec='" + sec + '\'' +
                ", year='" + year + '\'' +
                ", dept='" + dept + '\'' +
                ", Email='" + Email + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", offeredBy='" + offeredBy + '\'' +
                ", modeOfStudy='" + modeOfStudy + '\'' +
                ", duration='" + duration + '\'' +
                ", credits='" + credits + '\'' +
                ", assessmentMethod='" + assessmentMethod + '\'' +
                ", mark='" + mark + '\'' +
                ", grade='" + grade + '\'' +
                ", courseLink='" + courseLink + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", ca='" + ca + '\'' +
                ", hod='" + hod + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getAssessmentMethod() {
        return assessmentMethod;
    }

    public void setAssessmentMethod(String assessmentMethod) {
        this.assessmentMethod = assessmentMethod;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    private String year;
    private String dept;
    private String Email;
    private String courseTitle;
    private String courseCode;
    private String offeredBy;
    private String modeOfStudy;
    private String duration;
    private String credits;
    private String assessmentMethod;
    private String mark;
    private String grade;
    private String courseLink;
    private String status;
    private String type;
    private String ca;
    private String hod;
    private String dir;
}
