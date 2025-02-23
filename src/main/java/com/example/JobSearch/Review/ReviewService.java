package com.example.JobSearch.Review;

import java.util.List;

public interface ReviewService{

    //GET ALL
    List<Review> findAllByCompanyId(Integer companyId);


    //POST Review -return job id in string format
    boolean createReview(Integer companyId, Review review);


    //GET review BY ID
    Review getReviewByID(Integer companyId, Integer reviewId);


    //UPDATE review BY ID
    boolean updateReviewById(Integer companyId, Integer reviewId, Review updatedReview);


    //DELETE review BY ID
    boolean deleteReviewById(Integer companyId, Integer reviewId);
}
