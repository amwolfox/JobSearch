package com.example.JobSearch.Jobs.impl;

import com.example.JobSearch.Jobs.Job;
import com.example.JobSearch.Jobs.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private List<Job> jobs = new ArrayList<>();
    private int nextid=1;


    @Override
    public List<Job> findAll() {
        return jobs;
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextid++);
        jobs.add(job);
    }

    @Override
    public Job getJobByID(int id) {
        for (Job job : jobs) {
            if (id == job.getId()) {
                return job;
            }
        }
        return null;
    }

    @Override
    public boolean updateJobById(int id, Job job) {
        for (Job value : jobs) {
            if (id == value.getId()) {
                value.setTitle(job.getTitle());
                value.setDescription(job.getDescription());
                value.setLocation(job.getLocation());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteJobById(int id) {
        return jobs.removeIf(value -> id == value.getId()); //removeIf return true/false
    }
}