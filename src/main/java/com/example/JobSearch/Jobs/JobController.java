package com.example.JobSearch.Jobs;

import com.example.JobSearch.Company.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobservice;

    // Dependency injection - IOC of Service Interface
    public JobController(JobService jobservice) {
        this.jobservice = jobservice;
    }

    // GET ALL JOBS
    @GetMapping("/findAll")
    public ResponseEntity<List<Job>> findAll() {
        try {
            List<Job> jobs = jobservice.findAll();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving job listings", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE JOB
    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        try {
            String jobId = jobservice.createJob(job);
            return new ResponseEntity<>("Job Added Successfully with id: " + jobId, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating job: {}", job, e);
            return new ResponseEntity<>("Failed to create job. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET JOB BY ID
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getJobByID(@PathVariable Integer id) {
        try {
            Job job = jobservice.getJobByID(id);
            if (job != null) {
                return new ResponseEntity<>(job, HttpStatus.OK);
            } else {
                logger.warn("Job with ID {} not found", id);
                return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching job with ID: {}", id, e);
            return new ResponseEntity<>("Failed to retrieve job details. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE JOB BY ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Integer id, @RequestBody Job updatedJob) {
        try {
            boolean updated = jobservice.updateJobById(id, updatedJob);
            if (updated) {
                return new ResponseEntity<>("Job UPDATED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to update non-existent job with ID: {}", id);
                return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating job with ID: {}", id, e);
            return new ResponseEntity<>("Failed to update job. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE JOB BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Integer id) {
        try {
            boolean deleted = jobservice.deleteJobById(id);
            if (deleted) {
                return new ResponseEntity<>("JOB DELETED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to delete non-existent job with ID: {}", id);
                return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting job with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete job. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
