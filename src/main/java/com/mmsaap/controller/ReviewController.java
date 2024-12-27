package com.mmsaap.controller;


import com.mmsaap.entity.Property;
import com.mmsaap.entity.Reviews;
import com.mmsaap.entity.User;
import com.mmsaap.repository.PropertyRepository;
import com.mmsaap.repository.ReviewsRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private PropertyRepository propertyRepository;

    private ReviewsRepository reviewsRepository;
    public ReviewController(PropertyRepository propertyRepository, ReviewsRepository reviewsRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @PostMapping
    public  String addReview(
            @RequestBody Reviews  review,
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user
    ){
       //System.out.println(user.getName());
       //System.out.println(user.getEmail());
        Property property = propertyRepository.findById(propertyId).get();
        review.setProperty(property);
        review.setUser(user);
        reviewsRepository.save(review);
        return  "added..";
    }
}
