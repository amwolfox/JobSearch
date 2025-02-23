package com.example.JobSearch.Company;


import java.util.List;

public interface CompanyService {

    //GET ALL
    List<Company> findAll();

    //POST COMPANY -return job id in string format
    String createCompany(Company company);

    //GET COMPANY BY ID
    Company getCompanyByID(Integer id);

    //UPDATE COMPANY BY ID
    boolean updateCompanyById(Integer id, Company updated_company);

    //DELETE COMPANY BY ID
    boolean deleteCompanyById(Integer id);
}
