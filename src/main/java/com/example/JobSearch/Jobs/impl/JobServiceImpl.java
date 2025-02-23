package com.example.JobSearch.Jobs.impl;

import com.example.JobSearch.Jobs.Job;
import com.example.JobSearch.Jobs.JobRepository;
import com.example.JobSearch.Jobs.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobrepository;

    public JobServiceImpl(JobRepository jobrepository) { // JobRepository is managed by Spring (IOC)
        this.jobrepository = jobrepository;
    }

    // GET ALL JOBS
    @Override
    public List<Job> findAll() {
        try {
            return jobrepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Failed to fetch job listings. Please try again later.");
        }
    }

    // POST JOB
    @Override
    public String createJob(Job job) {
        try {
            Job savedJob = jobrepository.save(job);
            return String.valueOf(savedJob.getId());
        } catch (DataAccessException e) {
            logger.error("Error saving job: {}", job, e);
            throw new RuntimeException("Failed to create job. Please try again later.");
        }
    }

    // GET JOB BY ID
    @Override
    public Job getJobByID(Integer id) {
        try {
            return jobrepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            logger.error("Error fetching job with ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve job details. Please try again later.");
        }
    }

    // UPDATE JOB BY ID
    @Override
    public boolean updateJobById(Integer id, Job updated_job) {
        try {
            Optional<Job> jobOptional = jobrepository.findById(id);
            if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updated_job.getTitle());
                job.setDescription(updated_job.getDescription());
                job.setLocation(updated_job.getLocation());
                jobrepository.save(job);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            logger.error("Error updating job with ID: {}", id, e);
            throw new RuntimeException("Failed to update job. Please try again later.");
        }
    }

    // DELETE JOB BY ID
    @Override
    public boolean deleteJobById(Integer id) {
        try {
            if (jobrepository.existsById(id)) {
                jobrepository.deleteById(id);
                logger.info("Job with ID {} successfully deleted.", id);
                return true;
            }
            logger.warn("Attempted to delete non-existent job with ID: {}", id);
            return false;
        } catch (DataAccessException e) {
            logger.error("Error deleting job with ID: {}", id, e);
            throw new RuntimeException("Failed to delete job. Please try again later.");
        }
    }
}