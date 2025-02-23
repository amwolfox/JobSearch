package com.example.JobSearch.Jobs;

import java.util.List;

public interface JobService{

    //GET ALL
    List<Job> findAll();

    //POST JOBS -return job id in string format
    String createJob(Job job);

    //GET JOB BY ID
    Job getJobByID(Integer id);

    //UPDATE JOB BY ID
    boolean updateJobById(Integer id, Job updated_job);

    //DELETE JOB BY ID
    boolean deleteJobById(Integer id);
}
