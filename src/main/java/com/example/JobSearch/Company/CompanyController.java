package com.example.JobSearch.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    // Dependency injection - IOC of Service Interface
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET ALL JOBS
    @GetMapping("/findAll")
    public ResponseEntity<List<Company>> findAll() {
        try {
            List<Company> company = companyService.findAll();
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving Company listings", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE JOB
    @PostMapping("/createCompany")
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        try {
            String companyId = companyService.createCompany(company);
            return new ResponseEntity<>("Company Added Successfully with id: " + companyId, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating Company: {}", company, e);
            return new ResponseEntity<>("Failed to create Company. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET JOB BY ID
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getCompanyByID(@PathVariable Integer id) {
        try {
            Company company = companyService.getCompanyByID(id);
            if (company != null) {
                return new ResponseEntity<>(company, HttpStatus.OK);
            } else {
                logger.warn("Company with ID {} not found", id);
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching Company with ID: {}", id, e);
            return new ResponseEntity<>("Failed to retrieve Company details. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE JOB BY ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Integer id, @RequestBody Company updatedCompany) {
        try {
            boolean updated = companyService.updateCompanyById(id, updatedCompany);
            if (updated) {
                return new ResponseEntity<>("Company UPDATED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to update non-existent Company with ID: {}", id);
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating Company with ID: {}", id, e);
            return new ResponseEntity<>("Failed to update Company. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE COMPANY BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Integer id) {
        try {
            boolean deleted = companyService.deleteCompanyById(id);
            if (deleted) {
                return new ResponseEntity<>("Company DELETED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to delete non-existent Company with ID: {}", id);
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting Company with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete Company. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
