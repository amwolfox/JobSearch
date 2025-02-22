package com.example.JobSearch.Jobs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    //need a create a job service object;
    private JobService jobservice;

    //dependency injection - IOC
    public JobController(JobService jobservice) {
        this.jobservice = jobservice;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Job>> findAll(){
        return new ResponseEntity<>(jobservice.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobservice.createJob(job);
        return new ResponseEntity<>("Job Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Job> getJobByID(@PathVariable int id){
        Job job = jobservice.getJobByID(id);
        if(job!=null){
            return new ResponseEntity<>(job, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable int id, @RequestBody Job job){
        boolean updated = jobservice.updateJobById(id, job);
        if(updated){
            return new ResponseEntity<>("Job UPDATED SUCCESSFULLY", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable int id){
        boolean deleted = jobservice.deleteJobById(id);
        if(deleted) {
            return new ResponseEntity<>("JOB DELETED SUCCESSFULLY", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
