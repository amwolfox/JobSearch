package com.example.JobSearch.Review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/review/{companyId}")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewservice;

    // Dependency injection - IOC of Service Interface
    public ReviewController(ReviewService reviewservice) {
        this.reviewservice = reviewservice;
    }

    // GET ALL Reviews for a company
    @GetMapping("/findAll")
    public ResponseEntity<List<Review>> findAllByCompanyId(@PathVariable Integer companyId) {
        try {
            List<Review> reviews = reviewservice.findAllByCompanyId(companyId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving reviews listings", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE Review for a company
    @PostMapping("/createReview")
    public ResponseEntity<String> createReview(@PathVariable Integer companyId, @RequestBody Review review) {
        try {
            boolean review_created = reviewservice.createReview(companyId, review);
            if(review_created)
                return new ResponseEntity<>("Review Added Successfully ", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Failed adding Review ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error creating review: {}", review, e);
            return new ResponseEntity<>("Failed to create review. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // GET Review BY review-ID for a company
    @GetMapping("/get/{reviewId}")
    public ResponseEntity<?> getReviewByID(@PathVariable Integer companyId, @PathVariable Integer reviewId) {
        try {
            Review review = reviewservice.getReviewByID(companyId, reviewId);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                logger.warn("Review with ID {} not found", reviewId);
                return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching review with ID: {}", reviewId, e);
            return new ResponseEntity<>("Failed to retrieve Review details. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // UPDATE REVIEW BY ID
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Integer companyId, @PathVariable Integer reviewId, @RequestBody Review updatedReview) {
        try {
            boolean updated = reviewservice.updateReviewById(companyId, reviewId, updatedReview);
            if (updated) {
                return new ResponseEntity<>("Review UPDATED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to update non-existent review with ID: {}", reviewId);
                return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating review with ID: {}", reviewId, e);
            return new ResponseEntity<>("Failed to update review. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // DELETE JOB BY ID
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Integer companyId, @PathVariable Integer reviewId) {
        try {
            boolean deleted = reviewservice.deleteReviewById(companyId, reviewId);
            if (deleted) {
                return new ResponseEntity<>("REVIEW DELETED SUCCESSFULLY", HttpStatus.OK);
            } else {
                logger.warn("Attempted to delete non-existent review with ID: {}", reviewId);
                return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting job with ID: {}", reviewId, e);
            return new ResponseEntity<>("Failed to delete job. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}