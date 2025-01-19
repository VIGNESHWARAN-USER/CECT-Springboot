package com.cect.backend.Models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credentials")
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    @Override
    public String toString() {
        return "Credentials{" +
                "_id='" + _id + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    @Id
    private String _id;
    private String count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
