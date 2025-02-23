package com.example.JobSearch.Jobs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {//Jpa repository for entity class, and primary key type
}
