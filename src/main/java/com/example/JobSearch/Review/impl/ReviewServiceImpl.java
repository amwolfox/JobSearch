package com.example.JobSearch.Review.impl;

import com.example.JobSearch.Company.Company;
import com.example.JobSearch.Company.CompanyService;
import com.example.JobSearch.Review.Review;
import com.example.JobSearch.Review.ReviewRepository;
import com.example.JobSearch.Review.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewrepository;
    private final CompanyService companyService;


    public ReviewServiceImpl(ReviewRepository reviewrepository, CompanyService companyService) { // JobRepository is managed by Spring (IOC)
        this.reviewrepository = reviewrepository;
        this.companyService = companyService;
    }

    // GET ALL Reviews
    @Override
    public List<Review> findAllByCompanyId(Integer companyId) {
        try {
            return reviewrepository.findAllByCompanyId(companyId);
        } catch (DataAccessException e) {
            logger.error("Error retrieving all review", e);
            throw new RuntimeException("Failed to fetch review. Please try again later.");
        }
    }

    // POST Review
    @Override
    public boolean createReview(Integer companyId, Review review) {
        try {
            Company company = companyService.getCompanyByID(companyId);
            if(company!=null) {
                review.setCompany(company);
                reviewrepository.save(review);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            logger.error("Error saving review: {}", review, e);
            throw new RuntimeException("Failed to create review. Please try again later.");
        }
    }

    // GET JOB BY ID
    @Override
    public Review getReviewByID(Integer companyId, Integer reviewId) {
        try {
            List<Review> reviews = reviewrepository.findAllByCompanyId(companyId);
            //filter the review
            return reviews.stream()
                    .filter(review -> review.getId()
                            .equals(reviewId))
                    .findFirst()
                    .orElse(null);
        } catch (DataAccessException e) {
            logger.error("Error fetching job with ID: {}", reviewId, e);
            throw new RuntimeException("Failed to retrieve review details. Please try again later.");
        }
    }

    // UPDATE JOB BY ID
    @Override
    public boolean updateReviewById(Integer companyId,  Integer reviewId, Review updatedReview) {
        try {
            List<Review> reviews = reviewrepository.findAllByCompanyId(companyId);
            for (Review review: reviews){
                if(Objects.equals(review.getId(), reviewId)) {
                    review.setTitle(updatedReview.getTitle());
                    review.setDescription(updatedReview.getDescription());
                    review.setCompany(companyService.getCompanyByID(companyId));
                    reviewrepository.save(review);
                    return true;
                }
            }
            return false;
        } catch (DataAccessException e) {
            logger.error("Error updating review with ID: {}", reviewId, e);
            throw new RuntimeException("Failed to update review. Please try again later.");
        }
    }

    // DELETE Review BY ID  -> since it is bi-directional; we have to delete both
    // 1. review from company id
    // 2. review from review  repository
    @Override
    public boolean deleteReviewById(Integer companyId, Integer reviewId){
        try {
            if (companyService.getCompanyByID(companyId)!= null && reviewrepository.existsById(reviewId)) {
                Review review = reviewrepository.findById(reviewId).orElse(null); //get review
                Company company = companyService.getCompanyByID(companyId); // get company
                company.getReviews().remove(review); // 1. a. remove review from company
                companyService.updateCompanyById(companyId, company); //1. b. update company details
                reviewrepository.deleteById(reviewId); //2. delete review from review repository
                logger.info("Review with ID {} successfully deleted.", reviewId);
                return true;
            }
            logger.warn("Attempted to delete non-existent Review with ID: {}", reviewId);
            return false;
        } catch (DataAccessException e) {
            logger.error("Error deleting Review with ID: {}", reviewId, e);
            throw new RuntimeException("Failed to delete Review. Please try again later.");
        }
    }
}