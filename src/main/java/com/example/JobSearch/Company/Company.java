package com.example.JobSearch.Company;

import com.example.JobSearch.Jobs.Job;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "company") //mapped by company defined in Job class;
    private List<Job> jobs;

    //private List<Review> reviews;


    public Company(){} //JPA needs to create object for entity class

    public Company(Integer id, String title, String description, String location, List<Job> jobs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobs = jobs;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
