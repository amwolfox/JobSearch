package com.example.JobSearch.Jobs;

import org.springframework.stereotype.Service;

import java.util.List;

public interface JobService{

    //GET ALL
    List<Job> findAll();

    //POST JOBS
    void createJob(Job job);

    //GET JOB BY ID
    Job getJobByID(int id);

    //UPDATE JOB BY ID
    boolean updateJobById(int id, Job job);

    //DELETE JOB BY ID
    boolean deleteJobById(int id);
}
