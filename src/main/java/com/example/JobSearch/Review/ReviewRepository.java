package com.example.JobSearch.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByCompanyId(Integer companyId);//Jpa repository for entity class, and primary key type
}
