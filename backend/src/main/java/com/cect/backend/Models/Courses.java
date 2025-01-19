package com.cect.backend.Models;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class Courses {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Id
    public String _id;

    @Override
    public String toString() {
        return "Courses{" +
                "_id='" + _id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", week='" + week + '\'' +
                ", orgcoursecode='" + orgcoursecode + '\'' +
                '}';
    }

    public String getOrgcoursecode() {
        return orgcoursecode;
    }

    public void setOrgcoursecode(String orgcoursecode) {
        this.orgcoursecode = orgcoursecode;
    }

    public String code;
    public String name;
    public String week;
    public String orgcoursecode;

}
