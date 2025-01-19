package com.cect.backend.Models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Override
    public String toString() {
        return "users{" +
                "_id='" + _id + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", year='" + year + '\'' +
                ", dept='" + dept + '\'' +
                ", sec='" + sec + '\'' +
                ", pass='" + pass + '\'' +
                ", Email='" + Email + '\'' +
                ", role='" + role + '\'' +
                ", roll='" + roll + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
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

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Id
    private String _id;
    private String first;
    private String last;
    private String year;
    private String dept;
    private String sec;
    private String pass;
    private String Email;
    private String role;
    private String roll;
}
