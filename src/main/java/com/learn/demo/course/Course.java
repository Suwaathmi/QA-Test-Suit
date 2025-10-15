package com.learn.demo.course;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course")
public class Course {

    @Id
    private String id; // MongoDB uses String IDs

    private String description;
    private String category;
    private String instructor_Id;
    private float price;
    private String image_url;

    public Course(String description, String category, String instructor_Id, String image_url, float price) {
        this.description = description;
        this.category = category;
        this.instructor_Id = instructor_Id;
        this.image_url = image_url;
        this.price = price;
    }

    public Course() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInstructor_Id() {
        return instructor_Id;
    }

    public void setInstructor_Id(String instructor_Id) {
        this.instructor_Id = instructor_Id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
