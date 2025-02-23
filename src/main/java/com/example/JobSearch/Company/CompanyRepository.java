package com.example.JobSearch.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {//Jpa repository for entity class, and primary key type
}
