package com.example.JobSearch.Jobs;

import com.example.JobSearch.Company.Company;
import jakarta.persistence.*;

@Entity
//@Table(name= "job_table")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String location;

    @ManyToOne
    private Company company;

    public Job(){} //JPA needs to create object for entity class

    public Job(Integer id, String title, String description, String location, Company company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.company = company;
    }

    public Integer getId() {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
