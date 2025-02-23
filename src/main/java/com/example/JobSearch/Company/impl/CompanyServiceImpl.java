package com.example.JobSearch.Company.impl;

import com.example.JobSearch.Company.Company;
import com.example.JobSearch.Company.CompanyRepository;
import com.example.JobSearch.Company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyrepository;

    public CompanyServiceImpl(CompanyRepository companyrepository) { // CompanyRepository is managed by Spring (IOC)
        this.companyrepository = companyrepository;
    }

    // GET ALL CompanyS
    @Override
    public List<Company> findAll() {
        try {
            return companyrepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Error retrieving all companies", e);
            throw new RuntimeException("Failed to fetch companies listings. Please try again later.");
        }
    }

    // POST Company
    @Override
    public String createCompany(Company company) {
        try {
            Company savedCompany = companyrepository.save(company);
            return String.valueOf(savedCompany.getId());
        } catch (DataAccessException e) {
            logger.error("Error saving company: {}", company, e);
            throw new RuntimeException("Failed to create company. Please try again later.");
        }
    }

    // GET Company BY ID
    @Override
    public Company getCompanyByID(Integer id) {
        try {
            return companyrepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            logger.error("Error fetching company with ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve company details. Please try again later.");
        }
    }

    // UPDATE Company BY ID
    @Override
    public boolean updateCompanyById(Integer id, Company updated_company) {
        try {
            Optional<Company> companyOptional = companyrepository.findById(id);
            if (companyOptional.isPresent()) {
                Company company = companyOptional.get();
                company.setTitle(updated_company.getTitle());
                company.setDescription(updated_company.getDescription());
                company.setLocation(updated_company.getLocation());
                companyrepository.save(company);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            logger.error("Error updating company with ID: {}", id, e);
            throw new RuntimeException("Failed to update company. Please try again later.");
        }
    }

    // DELETE Company BY ID
    @Override
    public boolean deleteCompanyById(Integer id) {
        try {
            if (companyrepository.existsById(id)) {
                companyrepository.deleteById(id);
                logger.info("Company with ID {} successfully deleted.", id);
                return true;
            }
            logger.warn("Attempted to delete non-existent company with ID: {}", id);
            return false;
        } catch (DataAccessException e) {
            logger.error("Error deleting company with ID: {}", id, e);
            throw new RuntimeException("Failed to delete company. Please try again later.");
        }
    }
}